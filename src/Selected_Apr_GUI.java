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

        //Button: Set Image Icon and Cursor
        btnRent.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_red.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        btnRent.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRent.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonCancel.setIcon(new ImageIcon(new ImageIcon("Images/Components/button_cancel.png").getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
        buttonCancel.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    //Resize image
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    Apartment_info apartmentInfo=new Apartment_info();

    //Send Chosen Unit to apartment Info
    private void chosenUnit(){

        Retrieve check = new Retrieve();
        ResultSet result=check.checkApartmentinfo();

        try {

            apartmentInfo.setApartmentid(result.getInt("apr_id")); //apartment id
            apartmentInfo.setunitNum(result.getString("unit_number")); //unit number
            apartmentInfo.setBedcount(result.getInt("bedcount")); //bedcount
            apartmentInfo.setPrice(result.getInt("unit_price")); //unit price
            apartmentInfo.setStatus(result.getString("status")); //apartment status

            if(apartmentInfo.getStatus().equals("Unavailable")){

                btnRent.setEnabled(false);

            }

            BufferedImage[] image = new BufferedImage[5];

            //Put Images to Array
            for(int i=1;i<=5;i++){

                java.sql.Blob blob = result.getBlob("unit_photo"+i); //Get Blob images from database
                InputStream in = blob.getBinaryStream(); //Convert to Readable Blob
                image[i-1]=ImageIO.read(in); //Read Image

            }

            //Set Tab Apartment Photos
            poster.setIcon(new ImageIcon(resize(image[0],550,300)));
            poster2.setIcon(new ImageIcon(resize(image[1],550,300)));
            poster3.setIcon(new ImageIcon(resize(image[2],550,300)));
            poster4.setIcon(new ImageIcon(resize(image[3],550,300)));
            poster5.setIcon(new ImageIcon(resize(image[4],550,300)));

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        //Set Details to be Display (Unit Number, Bedcount, Price, Status)
        details.setText("Unit Number: "+ apartmentInfo.getunitNum() + "\nBedcount: " + apartmentInfo.getBedcount()
                +"\nUnit Price: " + apartmentInfo.getPrice() + "\nStatus: "+apartmentInfo.getStatus());

    }

    //Show Rent Confirmation GUI
    private void onRent() {
        apartmentInfo.saveinstance(); //save
        dispose();
        Rent_Confirmation_GUI.Rent_Confirmation_GUI();
    }

    private void onCancel() {
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
