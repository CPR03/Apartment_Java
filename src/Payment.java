import javax.swing.*;
import java.sql.*;

//Contains Calculation per Payment Method
public class Payment extends Error {

    static String text;

    //Get text (user payment amount)
    public static String getText(){
        return text;
    }

    //Get Payment Method and Calculate
    public int confirmPayment(String mode){

        int stat=10; //initialize not in choices(-1,0,1)

        double input = 0;

        //GCash
        if(mode.equals("GCash" )){

            text=JOptionPane.showInputDialog(null,"Input Amount: ","Gcash",JOptionPane.QUESTION_MESSAGE);

            try {

                if(isNumeric(text)){

                    input=Double.parseDouble(text);

                    //Display change (if OK)
                    if(input>=UserInfo.getMonthly_total()){

                        //Formula: User Input - Monthly Rent. Display Change
                        JOptionPane.showMessageDialog(null,"Change: "+ (input - UserInfo.getMonthly_total()),"Payment Successful",JOptionPane.INFORMATION_MESSAGE);
                        stat=0; //set flag to 0

                    }

                    //Display Error (if fund not enough)
                    else {

                        GcashInsufficient();
                        stat=1; //set flag to 1

                    }

                }

                //Display Error (If Input is Non-numeric)
                else{

                    invalidInput();
                    stat=1; //set flag to 1

                }

            }catch (NullPointerException e){
                nullInput();
                stat=-1; //set flag to -1
            }

        }

        //Debit
        else if (mode.equals("Debit")) {

            text=JOptionPane.showInputDialog(null,"Input Amount: ","DebitCard",JOptionPane.QUESTION_MESSAGE);

            try {

                //Check if input is numeric
                if(isNumeric(text)){

                    input=Double.parseDouble(text);

                    if(input>=UserInfo.getMonthly_total()){

                        ////Formula: User Input - Monthly Rent. Display Change
                        JOptionPane.showMessageDialog(null,"Change: "+ (input - UserInfo.getMonthly_total()),"Payment Successful",JOptionPane.INFORMATION_MESSAGE);
                        stat=0; //set flag to 0

                    }

                    //Display Error (If Debit Input is not enough)
                    else {

                        DebitInsufficient();
                        stat=1; //set flag to 1

                    }

                }

                //If input is not numeric
                else {

                    //Display Error (If input is Non-numeric)
                    invalidInput();
                    stat=1; //set flag to 1

                }

            }catch (NullPointerException e){
                nullInput();
                stat=-1; //set flag to -1
            }


        }

        //SoulSpace method
        else{

            text= String.valueOf(UserInfo.getMonthly_total());

            //Check if the user input is enough for payment.
            if(UserInfo.get_Balance()>=UserInfo.getMonthly_total()){

                input=Double.parseDouble(text);
                Update update = new Update();
                update.UpdateBalance();

                //Display Success Message
                JOptionPane.showMessageDialog(null,"Remaining Balance "+ UserInfo.get_Balance(),"Payment Successful",JOptionPane.INFORMATION_MESSAGE);
                stat=0; //set flag 0

            }

            //Display Error (If SoulSpace Balance is not enough)
            else{

                inputInsufficient();

            }

        }

        //Set Amount Payment of the user
        if(stat==0){
            UserInfo.setAmount_paid(input);
        }

        return stat;

    }


    //check if input is number
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


}
