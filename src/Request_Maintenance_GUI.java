import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Request_Maintenance_GUI extends JDialog{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox cmbReqType;
    private JTextArea areaConcern;

    public Request_Maintenance_GUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                onOK();
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

        onSelectedUtilities();
    }
    Create requestConnector = new Create();

    private void onOK() {

        try {

            Statement state = requestConnector.connect();

            ResultSet getMaxReqId = state.executeQuery("SELECT MAX(req_id) as maxReqId FROM apartment.maintenance_req");
            getMaxReqId.next();

            int maxReqId = getMaxReqId.getInt("maxReqId");

            ResultSet result = state.executeQuery("SELECT * FROM apartment.maintenance_req");

            result.moveToInsertRow();

            result.updateInt("req_id", maxReqId + 1); // Increment the max req_id


            //result.updateInt("user_id", log.getUserID());

            result.updateString("type", cmbReqType.getSelectedItem().toString());
            result.updateString("description", areaConcern.getText());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(formatter.format(new Date()));
            result.updateDate("date_created", new java.sql.Date(date.getTime()));
            result.updateInt("user_id",UserInfo.get_User_id());

            result.insertRow();
            result.beforeFirst();

            JOptionPane.showMessageDialog(null, "Request Successfully sent.");

            state.close();


        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }

    private void onSelectedUtilities(){

        int userID;
        int amenities = 0, water = 0, cable = 0, wifi = 0;

        try {

            Statement state = requestConnector.connect();

            ResultSet result = state.executeQuery("SELECT * FROM apartment.transaction WHERE user_id = '" + UserInfo.get_User_id()+ "'");

            while(result.next()){
                wifi = result.getInt("wifi");
                amenities = result.getInt("amenities");
                water = result.getInt("water");
                cable = result.getInt("cable");
            }


            state.close();


        } catch (Exception exc) {
            exc.printStackTrace();
        }

        if(amenities == 1){
            cmbReqType.addItem("Amenities");
        }

        if(wifi == 1){
            cmbReqType.addItem("Wi-Fi");
        }

        if(water == 1){
            cmbReqType.addItem("Water");
        }

        if(cable == 1){
            cmbReqType.addItem("Cable");
        }


    }

    private void onCancel() {
        // add your code here if necessary

        dispose();
        Dashboard_GUI.Dashboard_GUI();
    }


    public static void main(String[] args) {
        Request_Maintenance_GUI.Request_Maintenance_GUI();
    }

    Image imagelogo = new ImageIcon("Images/Components/logo.png").getImage();
    //for calling Request Maintenance
    static void Request_Maintenance_GUI() {

        Request_Maintenance_GUI req = new Request_Maintenance_GUI();
        req.pack();
        req.setIconImage(req.imagelogo);
        req.setBounds(600,200,400,350);
        req.setLocationRelativeTo(null);
        req.setTitle("SoulSpace | Request Maintenance");

        req.setVisible(true);
    }
}
