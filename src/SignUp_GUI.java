import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

public class SignUp_GUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtUserLog;
    private JPasswordField passfield;
    private JLabel logInPic;
    private JLabel labelLogo;
    private JButton btnLogIn;



    public SignUp_GUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        txtUserLog.setText("Username");
        passfield.setText("Password");
        txtUserLog.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtUserLog.getText().equals("")||txtUserLog.getText().equals("Username")){
                    txtUserLog.setText("");
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txtUserLog.getText().equals("")){
                    txtUserLog.setText("Username");
                }
            }
        });
        passfield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passfield.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(passfield.getText().equals("")){
                    passfield.setText("Password");
                }
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                onOK();

            }
        });

        btnLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                onBack();

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

        //Sign in Picture
        logInPic.setIcon(new ImageIcon(new ImageIcon("Images/Components/sign_in.png").getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH)));
        labelLogo.setIcon(new ImageIcon(new ImageIcon("Images/Components/logo.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));

        //Rounded Buttons
        buttonOK.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_red.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        buttonOK.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonOK.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonCancel.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_cancel.png").getImage().getScaledInstance(80, 23, Image.SCALE_SMOOTH)));
        buttonCancel.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    private void onOK() {
        Error err = new Error();
        int flag;
        if(txtUserLog.getText().equals("Username")||txtUserLog.getText().equals("")||passfield.getText().equals("")||passfield.getText().equals("Password")){
            err.nullInput();
        }else{
            Create create = new Create();
            flag=create.createAccount(txtUserLog.getText(), passfield.getText());
            if(flag==1){
                JOptionPane.showMessageDialog(null,"Account successfully created");
            }
            else return;
            dispose();
            LogIn_GUI.LogIn_GUI();

        }



    }


    //Go back to Login if back button is pressed
    private void onBack(){
        setVisible(false);

        LogIn_GUI.LogIn_GUI();
    }

    private void onCancel() {
        dispose();
    }

    public static void main (String[] args){
        SignUp_GUI.SignUp_GUI();
    }

    Image imagelogo = new ImageIcon("Images/Components/logo.png").getImage();
    static void SignUp_GUI(){
        SignUp_GUI sign = new SignUp_GUI();
        sign.pack();
        sign.setTitle("SoulSpace | Sign up.");
        sign.setIconImage(sign.imagelogo);
        sign.setResizable(false);
        sign.setBounds(600,200,600,380);

        sign.setLocationRelativeTo(null);
        sign.setVisible(true);
    }

}
