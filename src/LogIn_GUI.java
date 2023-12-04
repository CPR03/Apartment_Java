import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class LogIn_GUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    protected JTextField txtUserLog;
    private JPasswordField txtPass;
    private JLabel logInPic;
    private JLabel labelLogo;
    private JButton btnSignUp;
    private JButton button1;

    public LogIn_GUI() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //Make username and password text placeholder disappear
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSignUp();
            }
        });

        txtUserLog.setText("Username");
        txtPass.setText("Password");

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

        txtPass.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txtPass.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txtPass.getText().equals("")){
                    txtPass.setText("Password");
                }
            }
        });

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

        btnSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    private void onSignUp() {
        //hide login
        setVisible(false);

        // Create and show the sign-up dialog
        SignUp_GUI.SignUp_GUI();
    }

    Error error = new Error();

    private void onOK() {
        if(txtUserLog.getText().equals("Username")||txtUserLog.getText().equals("")||txtPass.getText().equals("")||txtPass.getText().equals("Password")){
            error.nullInput();
        }else{
            Retrieve check = new Retrieve();
            //if succsessfull go to Dashboard
            if(check.checkAccount(txtUserLog.getText(), txtPass.getText())){
                dispose();
                Dashboard_GUI.Dashboard_GUI();
            }
        }

    }

    private void onCancel() {
        System.exit(0);
    }

    public static void main(String[] args) {
        LogIn_GUI.LogIn_GUI();
    }

    Image imagelogo = new ImageIcon("Images/Components/logo.png").getImage();
    //for calling LogIn_GUI
    static void LogIn_GUI() {

        LogIn_GUI log = new LogIn_GUI();
        log.pack();
        log.setTitle("SoulSpace | Login.");
        log.setIconImage(log.imagelogo);
        log.setResizable(false);
        log.setBounds(600,200,600,380);
        log.setLocationRelativeTo(null);

        log.setVisible(true);
    }

}
