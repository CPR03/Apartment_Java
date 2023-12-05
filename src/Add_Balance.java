import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Objects;

public class Add_Balance extends JDialog {
    private JPanel contentPane;
    private JButton btnCashin;
    private JComboBox cmbvia;
    private JTextField txtamount;
    private JTextField txttotal;
    private JTextField txtcharge;
    private JTextField txtinput;
    private JButton btnenter;
    private JButton btnCancel;
    private JButton buttonCancel;
    String mode;

    public Add_Balance() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnCashin);

        btnCashin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onPay();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        cmbvia.setSelectedItem("Gcash");
        mode=cmbvia.getSelectedItem().toString();
        UserInfo.set_PaymentMode(mode);

        getPaymentmode();
        cmbvia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = cmbvia.getSelectedItem().toString();
                UserInfo.set_PaymentMode(mode);
                getPaymentmode();  //Display Payment mode
            }
        });
        btnenter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEnter();
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

        btnCashin.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_red.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        btnCashin.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCashin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancel.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_cancel.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        btnCancel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }
    Error error = new Error();
    private void getPaymentmode(){

        switch (mode) {
            case "Debit" -> txtcharge.setText("200");
            default -> txtcharge.setText("100");
        }

    }
    int amount=0;
    double total = 0;
    private void onEnter() {
        UserInfo info = new Apartment_info();
        String iamount = txtamount.getText();
        if(isNumeric(iamount)){
//            error when charge fee is greater than inputted amount
            amount = Integer.parseInt(iamount);
            if (Integer.parseInt(iamount)<Integer.parseInt(txtcharge.getText())){
                error.inputInsufficient ();

//            error when charge fee is equal to inputted amount
            }else if(Integer.parseInt(iamount)==Integer.parseInt(txtcharge.getText())) {
                error.inputInsufficient();

            }

//        error when input is null
            else if(iamount.equals("")){
                error.nullInput();
            }
            else{
                txtinput.setText(String.valueOf(amount));
                int charge = Integer.parseInt(txtcharge.getText());
                total = amount - charge;
                txttotal.setText(amount-charge + " ( " + amount + " (Amount) " + "-" + charge + " (Charge)" + " )" );

            }
        }
        else{
            error.invalidInput();
        }

    }

    private void onPay() {
        Update add = new Update();

        if (txtamount.getText().equals("") || txtamount.getText().equals(null)){
            error.nullInput();
        }

        else {

            add.addbalance(total);

            JOptionPane.showMessageDialog(null,"Top up Successfully","Cash-in",JOptionPane.INFORMATION_MESSAGE);
            dispose();
            Dashboard_GUI.Dashboard_GUI();

        }


    }

    private void onCancel() {
        dispose();
        Dashboard_GUI.Dashboard_GUI();
    }

    public static void main(String[] args) {
        Add_Balance.Add_Balance();

    }
    Image imageLogo = new ImageIcon("Images/Components/logo.png").getImage();

    static void Add_Balance() {

        Add_Balance dialog = new Add_Balance();
        dialog.pack();
        dialog.setBounds(600,200,400,380);
        dialog.setIconImage(dialog.imageLogo);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setTitle("SoulSpace | Add SoulSpace Balance.");
        dialog.setIconImage(dialog.imageLogo);

        dialog.setVisible(true);
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
