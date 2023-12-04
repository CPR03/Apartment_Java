import javax.swing.*;

public class Error {

    //Insufficient Funds Error
    public void inputInsufficient (){
        JOptionPane.showMessageDialog(null, "Input is insufficient!", "SoulSpace | Warning", JOptionPane.WARNING_MESSAGE);
    }

    //Gcash Insufficient Fund Error
    public void GcashInsufficient(){
        JOptionPane.showMessageDialog(null, "Input is insufficient!", "Gcash | Warning", JOptionPane.WARNING_MESSAGE);
    }

    //Debit Insufficient Fund Error
    public void DebitInsufficient(){
        JOptionPane.showMessageDialog(null, "Input is insufficient!", "Debit | Warning", JOptionPane.WARNING_MESSAGE);
    }

    //Invalid Input Error
    public void invalidInput(){
        JOptionPane.showMessageDialog(null, "Invalid Input", "Invalid Input", JOptionPane.WARNING_MESSAGE);

    }

    //Null Input Error
    public void nullInput(){
        JOptionPane.showMessageDialog(null, "No Input", "Null", JOptionPane.WARNING_MESSAGE);

    }

}
