import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class Pay_GUI extends JDialog {
    private JPanel contentPane;
    private JButton paybtn;
    private JButton buttonCancel;
    private JComboBox cmbPayMethod;
    private JTextField initialtxt;
    private JTextField disctxt;
    private JTextField totaltxt;
    private JTextPane receipttxt;
    private JTextField chargetxt;
    private JComboBox cmbDiscount;
    private JTextField txtDuration;
    private JTextField txtUtil;
    String DiscountCode;
    String mode;
    double discount;
    Transaction_info transaction = new Transaction_info();
    Compute compute = new Compute();
    public Pay_GUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(paybtn);

        cmbDiscount.setSelectedItem("Roland");      //Set default value to avoid empty error
        cmbPayMethod.setSelectedItem("SoulSpace");

        DiscountCode=cmbDiscount.getSelectedItem().toString();//Get discount % base on code

        transaction.setDiscountCode(discount);
        getDiscount(); //Display discount %

        mode = cmbPayMethod.getSelectedItem().toString();
        transaction.setPaymentMode(mode);

        getPaymentmode();  //Display Payment mode
        getTotal();
        cmbDiscount.addActionListener(new ActionListener() {//Check selected item from Discount code and set the Discount
            public void actionPerformed(ActionEvent e) {
                transaction.setPaymentMode(mode);
                DiscountCode=cmbDiscount.getSelectedItem().toString();
                transaction.setDiscountCode(discount);
                getDiscount();  //Display Discount
                getPaymentmode();
                getTotal();


            }
        });

        cmbPayMethod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = cmbPayMethod.getSelectedItem().toString();
                transaction.setPaymentMode(mode);
                getPaymentmode();  //Display Payment mode
                getTotal();
            }
        });

        paybtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                onPay();

            }
        });




        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        paybtn.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_red.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        paybtn.setHorizontalTextPosition(SwingConstants.CENTER);
        paybtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonCancel.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_cancel.png").getImage().getScaledInstance(80, 23, Image.SCALE_SMOOTH)));
        buttonCancel.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        initialtxt.setText(String.valueOf(UserInfo.get_Price()));
        txtDuration.setText(UserInfo.get_Duration());

        txtUtil.setText(String.valueOf(compute.getUtilfee()));//Compute Additional fee for Utilities

    }

    private void getTotal(){

        double total,price,chargefee,utilfee;
        chargefee=Double.parseDouble(chargetxt.getText());
        utilfee=compute.getUtilfee();
        discount=Double.parseDouble(disctxt.getText().substring(0,2))/100;
        price=UserInfo.get_Price()*discount;
        total=(UserInfo.get_Price()-price)+chargefee+utilfee;

        totaltxt.setText(String.valueOf(total));
        UserInfo.setMonthly_total(total-chargefee);

    }

    private void getDiscount(){
        switch (DiscountCode) {
            case "Roland" -> disctxt.setText("30%");
            case "Gail" -> disctxt.setText("25%");
            case "King" -> disctxt.setText("20%");
            case "Hakim" -> disctxt.setText("15%");
            default -> disctxt.setText("10%");
        }


    }

    private void getPaymentmode(){

        switch (mode) {
            case "SoulSpace" -> chargetxt.setText("0");
            case "GCash" -> chargetxt.setText("100");
            default -> chargetxt.setText("200");
        }


    }

    Payment confirm = new Payment();
    Create create = new Create();

    int status=1; //Payment status indicator

    private void onPay() {

        //Check if payment is successful
        status=confirm.confirmPayment(mode);
        //Status 0 = successful payment
        //Status 1 = unsuccessful payment
        //Status -1 = Null payment
        transaction.setDiscountCode(discount);

        if(status==0){// if Payment successful print receipt update database

            StringBuilder convert = new StringBuilder();

            for (int i = 0; i < UserInfo.get_Utilities().size(); i++) {

                convert.append(" "+UserInfo.get_Utilities().get(i)); //Get all utilities

                if (i < UserInfo.get_Utilities().size() - 1)
                    convert.append(" ");

            }

            Font font = new Font("Arial", Font.PLAIN, 16);
            receipttxt.setFont(font);


            receipttxt.setText(


                    "\t     ~User Info~" + "\n" +
                            "\tUser ID: " + UserInfo.get_User_id() + "\n" +
                            "\tUsername: "+ UserInfo.get_username() +"\n"+
                            "\tRemaining Balance: "+UserInfo.get_Balance()+"\n"+
                            "\tDate: "+ LocalDate.now() +"\n"+"\n"+

                            "\t     ~Apartment info~" + "\n"+
                            "\tUnit number: "+UserInfo.get_UnitNum()+"\n"+
                            "\tUnit Price: " +UserInfo.get_Price() + "\n" +
                            "\tDuration of Stay: " + UserInfo.get_Duration() + "\n"+
                            "\tUtilities: " + convert +"\n"+"\n"+


                            "\t     ~Total~" + "\n" +
                            "\tUtilities Fee: " + txtUtil.getText() + "\n"+
                            "\tCharge Fee: " + chargetxt.getText() +"\n"+
                            "\tDiscount: " + disctxt.getText()+"\n"+
                            "\tTotal: " + totaltxt.getText()


            );
            transaction.setRentTotal(UserInfo.getMonthly_total()*compute.getmonths());
            transaction.saveinstance();

            //Create transaction to database
            create.createTransaction();
            paybtn.setEnabled(false);


        }
        else if (status==1){

            JOptionPane.showMessageDialog(null,"Payment Unsuccessful\nPlease Try again!");
            onPay(); //Call itself

        }

    }


    private void onCancel() {

        //If payment successful back to Dashboard
        if(status==0){
            dispose();

            Dashboard_GUI.Dashboard_GUI();

        }
        else{

            dispose();
            Rent_Confirmation_GUI.Rent_Confirmation_GUI();

        }

    }

    public static void main(String[] args) {

        Pay_GUI.pay_GUI();
    }

    Image imageLogo = new ImageIcon("Images/Components/logo.png").getImage();
    static void pay_GUI(){
        Pay_GUI dialog = new Pay_GUI();
        dialog.pack();
        dialog.setTitle("SoulSpace | Payment");
        dialog.setBounds(300, 25, 700, 450);
        dialog.setResizable(false);
        dialog.setIconImage(dialog.imageLogo);
        dialog.setLocationRelativeTo(null);

        dialog.setVisible(true);

    }
}
