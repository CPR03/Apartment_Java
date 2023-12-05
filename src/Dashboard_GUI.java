import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Dashboard_GUI extends JDialog {
    private JPanel contentPane;

    private JButton buttonCancel;
    private JLabel profilePic;
    private JPanel p1;
    private JLabel homeLogo;
    private JLabel dashMan;
    private JButton btnrequest;
    private JPanel apartment_panel;
    private JScrollPane pane;
    private JLabel unit1;
    private JLabel unit2;
    private JLabel unit3;
    private JLabel unit4;
    private JLabel unit5;
    private JLabel unit6;
    private JLabel unit7;
    private JLabel unit8;
    private JLabel unit9;
    private JPanel scrollPanel;
    private JPanel other_info_panel;
    private JPanel dashPanel;
    private JButton rentUnit1;
    private JButton rentUnit2;
    private JButton rentUnit3;
    private JButton rentUnit4;
    private JButton rentUnit5;
    private JButton rentUnit6;
    private JButton rentUnit7;
    private JButton rentUnit8;
    private JButton rentUnit9;
    private JLabel unit10;
    private JLabel unit11;
    private JLabel unit12;
    private JButton rentUnit10;
    private JButton rentUnit11;
    private JButton rentUnit12;
    private JTable table1;
    private JPanel myInfo_panel;
    private JButton btnPayRent;
    private JLabel txtWelcome;
    private JTextField txtBalance;
    private  JButton btnUpdate;
    private JLabel dash_pic;
    private JLabel rentotal;
    private JLabel duration;
    private JPanel dashboardPanel;
    private JTable table2;
    private JButton btnTopUp;

    Retrieve retrieve = new Retrieve();

    public Dashboard_GUI() {

        setContentPane(contentPane);

        pane.getVerticalScrollBar().setUnitIncrement(16); //set scroll speed

        //Know what button is clicked returns the label
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                UserInfo.setUnitNum(actionEvent.getActionCommand().substring(6));

                dispose();
                Selected_Apr_GUI.Selected_Apr_GUI();
                //make dashboard visible if button cancel is clicked in Selected GUI


            }
        };

        Display(actionListener);

        //Show Request GUI
        btnrequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                onRequest();
            }
        });

        //Show Pay Rent GUI
        btnPayRent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                PayRent_GUI.PayRent_GUI();

            }
        });

        //Show Top Up Balance GUI
        btnTopUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Add_Balance.Add_Balance();

            }
        });


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        buttonCancel.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_cancel.png").getImage().getScaledInstance(80, 23, Image.SCALE_SMOOTH)));
        buttonCancel.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    //Display Apartments
    private void Display(ActionListener actionListener){

        //Check account type
        checkType();

        //Store all Buttons to Array
        JButton [] Apart_buttons = {rentUnit1,rentUnit2,rentUnit3,rentUnit4,rentUnit5,
                rentUnit6,rentUnit7,rentUnit8,rentUnit9,rentUnit10,rentUnit11,rentUnit12};

        //Store all label to Array
        JLabel [] Unit_image={unit1,unit2,unit3,unit4,unit5,unit6,unit7,unit8,unit9,unit10,unit11,unit12};

        //Set Apartment Images and Buttons
        for(int i=0;i<12;i++){
            Apart_buttons[i].addActionListener(actionListener);//add actionlistener to every Rent button

            //Set image for buttons
            Apart_buttons[i].setIcon(new ImageIcon(new ImageIcon("Images/Components/button_red.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
            Apart_buttons[i].setHorizontalTextPosition(SwingConstants.CENTER);//Center text
            Apart_buttons[i].setText("Rent: Unit "+(i+1)); //Set label for buttons
            Apart_buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));//Cursor hover

            String imagePath = "Images/Apartments/Out1/pngs/unit" + (i + 1) + ".png";

            Unit_image[i].setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));

        }

        //Disable Pay Rent Button is Rent is Done
        if(rentotal.getText().equals("₱ 0.0")){
            btnPayRent.setEnabled(false);
        }

        //Set Button Icon Images and Cursor
        btnrequest.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPayRent.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profilePic.setIcon(new ImageIcon(new ImageIcon("Images/Components/profile.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        homeLogo.setIcon(new ImageIcon(new ImageIcon("Images/Components/home_logo.png").getImage().getScaledInstance(300, 70, Image.SCALE_SMOOTH)));
        dashMan.setIcon(new ImageIcon(new ImageIcon("Images/Components/dash_board_man.png").getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH)));

        //Table Models
        table1.setModel(new DefaultTableModel(
                null,
                new String[] {"Name","Current Apartment","Date created","Rent Per Month","Amount Paid","Payment Method","Duration of Stay","Amenities","Wi-Fi","Cable","Water"}));

        table2.setModel(new DefaultTableModel(
                null,
                new String[] {"Request ID","User ID","Request Type","Description","Date Created",}));

        //Add rows by calling history
        history();
        maintenance();

        txtBalance.setText(String.valueOf(UserInfo.get_Balance()));
        txtWelcome.setText("Welcome, "+UserInfo.get_username() + "!");

    }

    //Get Rent History. Contains all transaction of the User
    private void history(){
        Retrieve con = new Retrieve();

        try {

            Statement state = con.connect().createStatement();

            //Get: User ID, Username, Unit Number, Transaction Date, Monthly Rent, Payment Method, Duration of Stay, Utilities
            ResultSet result = state.executeQuery(" SELECT apartment.users.user_id,apartment.users.userName,apartment.apartment_unit.unit_number,apartment.transaction.Date, apartment.transaction.monthly_due_amount,apartment.transaction.payment_method, apartment.transaction.duration,apartment.transaction.amenities,apartment.transaction.wifi,apartment.transaction.cable,apartment.transaction.water,apartment.transaction.amount_pay\n" +
                    "            FROM apartment.users\n" +
                    "            RIGHT JOIN apartment.transaction ON users.user_id = transaction.user_id\n" +
                    "            LEFT JOIN apartment.apartment_unit ON transaction.apart_id = apartment_unit.apr_id" +
                    " Where users.user_id ='"+UserInfo.get_User_id()+"'");

            //Set Rows Data per Column
            while (result.next()){

                //Get Transaction Data and add it to row arrayList
                Object[] row = {

                        result.getString("userName"),
                        result.getString("unit_number"),
                        result.getDate("Date"),
                        result.getDouble("monthly_due_amount"),
                        result.getDouble("amount_pay"),
                        result.getString("payment_method"),
                        result.getString("duration"),
                        result.getInt("amenities"),
                        result.getInt("wifi"),result.getInt("cable"),
                        result.getInt("water")
                };

                //Get Table 1 Model
                DefaultTableModel model = (DefaultTableModel) table1.getModel();

                //Add row
                model.addRow(new Object[]{row[0], row[1], row[2],row[3],row[4],row[5],row[6],row[7],row[8],row[9],row[10]});

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

                // Center align the column
                for(int i=0;i<11;i++){
                    table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

            }

            state.close();

        } catch (Exception exc) {

            exc.printStackTrace();
        }
    }

    //Get Maintenance History
    private void maintenance(){ //Get all transaction of the User

        try {
            Statement state = retrieve.connect().createStatement();



            //Get: User ID, Username, Unit Number, Transaction Date, Monthly Rent, Payment Method, Duration of Stay, Utilities
            ResultSet result = state.executeQuery(" SELECT *" +
                    "    FROM apartment.maintenance_req Where maintenance_req.user_id ='"+UserInfo.get_User_id()+"'");

            //Set Rows Data per Column
            while (result.next()){

                //Get Transaction Data and add it to data arrayList
                Object[] data = {

                        result.getInt("req_id"),
                        result.getInt("user_id"),
                        result.getString("type"),
                        result.getString("description"),
                        result.getDate("date_created")
                };

                //Get Table 2 Model
                DefaultTableModel model = (DefaultTableModel) table2.getModel();

                //Add Data to the table using the data arrayList
                model.addRow(new Object[]{data[0], data[1], data[2],data[3],data[4]});

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

                // Center align column
                for(int i=0;i<5;i++){
                    table2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

            }

            state.close();

        } catch (Exception exc) {

            exc.printStackTrace();
        }
    }

    ArrayList objects;
    //Check user type
    private void checkType(){

        //Hide Request Maintenance and Pay Rent Button if New
        if(retrieve.checktype().equals("new")){

            btnrequest.setVisible(false);
            btnPayRent.setVisible(false);

        }

        else{

            objects = retrieve.get_LastTransaction("check");
            Object dashpic = objects.get(0);
            ImageIcon icon = new ImageIcon((Image) dashpic);

            //Enable Old User Interface
            btnrequest.setVisible(true);
            dashboardPanel.setVisible(true);
            btnPayRent.setVisible(true);
            scrollPanel.setVisible(false);
            myInfo_panel.setVisible(true);

            dash_pic.setIcon(icon);
            rentotal.setText("₱ "+ objects.get(2));
            duration.setText(String.valueOf(objects.get(1)));

        }

    }

    //Show Request Maintenance GUI
    private void onRequest(){
        Request_Maintenance_GUI.Request_Maintenance_GUI();
    }

    public void onCancel() {
        dispose();
        LogIn_GUI.LogIn_GUI();
    }

    public static void main(String[] args) {
        Dashboard_GUI.Dashboard_GUI();
    }

    Image imageLogo = new ImageIcon("Images/Components/logo.png").getImage();
    static void Dashboard_GUI() {

        Dashboard_GUI dialog = new Dashboard_GUI();
        dialog.pack();
        dialog.setBounds(300, 25, 1300, 950);
        dialog.setResizable(false);
        dialog.setTitle("SoulSpace | Dashboard.");
        dialog.setIconImage(dialog.imageLogo);

        dialog.setVisible(true);
    }

}


