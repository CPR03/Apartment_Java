import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class Selected_Apr_GUI extends JDialog {
    private  JPanel contentPane;
    private  JButton btnRent;
    private JButton buttonCancel;
    private JPanel panel;
    private JTextArea details;
    private JLabel poster;
    private JTabbedPane tabbedPane1;
    private JLabel poster2;
    private JLabel poster3;
    private JLabel poster4;
    private JLabel poster5;

    public Selected_Apr_GUI() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnRent);

        chosenUnit();//Display chosen unit

        btnRent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRent();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        /*poster.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDetails();
            }
        });*/
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


        btnRent.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_red.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        btnRent.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRent.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonCancel.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_cancel.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        buttonCancel.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    Apartment_info apartmentInfo=new Apartment_info();

    private void chosenUnit(){ //pass chosen unit
        Retrieve check = new Retrieve();
        ResultSet result=check.checkApartmentinfo();

        try {
            apartmentInfo.setApartmentid(result.getInt("apr_id"));
            apartmentInfo.setunitNum(result.getString("unit_number"));
            apartmentInfo.setBedcount(result.getInt("bedcount"));
            apartmentInfo.setPrice(result.getInt("unit_price"));
            apartmentInfo.setStatus(result.getString("status"));
            if(apartmentInfo.getStatus().equals("Unavailable")){
                btnRent.setEnabled(false);
            }
            BufferedImage[] image = new BufferedImage[5];
            for(int i=1;i<=5;i++){
                java.sql.Blob blob = result.getBlob("unit_photo"+i);
                InputStream in = blob.getBinaryStream();
                image[i-1]=ImageIO.read(in);
            }
            poster.setIcon(new ImageIcon(resize(image[0],550,300)));
            poster2.setIcon(new ImageIcon(resize(image[1],550,300)));
            poster3.setIcon(new ImageIcon(resize(image[2],550,300)));
            poster4.setIcon(new ImageIcon(resize(image[3],550,300)));
            poster5.setIcon(new ImageIcon(resize(image[4],550,300)));

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        details.setText("Unit Number: "+apartmentInfo.getunitNum()+"\nBedcount: "+apartmentInfo.getBedcount()
                +"\nUnit Price: "+apartmentInfo.getPrice()+"\nStatus: "+apartmentInfo.getStatus());

    }
    private void onRent() {
        apartmentInfo.saveinstance();
        dispose();
        Rent_Confirmation_GUI.Rent_Confirmation_GUI();
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
        Dashboard_GUI.Dashboard_GUI();

    }



    public static void main(String[] args) {
        Selected_Apr_GUI.Selected_Apr_GUI();
    }

    Image imageLogo = new ImageIcon("Images/Components/logo.png").getImage();

    static void Selected_Apr_GUI () {

        Selected_Apr_GUI selectedAprGui = new Selected_Apr_GUI();

        selectedAprGui.pack();
        selectedAprGui.setTitle("SoulSpace | Unit Details.");
        selectedAprGui.setIconImage(selectedAprGui.imageLogo);
        selectedAprGui.setResizable(false);
        selectedAprGui.setBounds(600,200,600,600);
        selectedAprGui.setLocationRelativeTo(null);
        selectedAprGui.setVisible(true);

    }





}
