import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//Contains Create Account and Create Transaction
public class Create extends Database{


    //Will Create Account for the user
    Error err = new Error();
    public int createAccount(String username, String password){

        int flag=0;

        //Set SoulSpace Wallet Initial Balance
        int initial=10000;

        try {

            Statement state = connect().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet getRowCount = state.executeQuery("SELECT COUNT(*) as rowCount FROM apartment.users");

            // Move to the first (and only) row of the result set
            if (getRowCount.next()) {

                // Get the count of rows
                int rowCount = getRowCount.getInt("rowCount");

                //Get and Set Username and password
                String usernameInput = username;
                String passText=password;

                boolean usernameExists = false;

                //Create new query statement
                ResultSet result;
                result = state.executeQuery("SELECT * FROM apartment.users");

                //Create Account if database is empty
                if (rowCount == 0) {

                    // Insert the new user with user_id 1 if database is empty
                        result.moveToInsertRow();

                        result.updateInt("user_id", 1); // add the newly created ID
                        result.updateString("userName", usernameInput); // add username
                        result.updateString("userPassword", passText); // add password
                        result.updateDouble("Balance",initial);
                        result.insertRow();
                        result.beforeFirst();

                        flag=1;

                } else {

                    // The database is not empty, check for duplicates

                    //Loop through the database to check if user exists
                    while (result.next()) {

                        String userNameFromDatabase = result.getString("userName").toLowerCase();

                        if (userNameFromDatabase.equals(usernameInput.toLowerCase())) {

                            JOptionPane.showMessageDialog(null, "Username already exists","Details not valid.",JOptionPane.ERROR_MESSAGE);
                            usernameExists = true;
                            break;  // exit the loop as soon as a matching username is found

                        }
                    }

                    //if user not exist insert new user
                    if (!usernameExists) {

                        result.last();
                        int id = result.getInt("user_id") + 1; // get current id and ADD 1

                        result.moveToInsertRow();

                        result.updateInt("user_id", id); // add the newly created ID
                        result.updateString("userName", usernameInput); // add username
                        result.updateString("userPassword", passText);
                        result.updateDouble("Balance",initial);// add password

                        result.insertRow();
                        result.beforeFirst();
                        flag=1;

                    }
                }
            }

            state.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return flag;
    }

    //Will create transcation
    Update updatestatus = new Update();
    public void createTransaction(){

        ArrayList<String> util = new ArrayList<>(); //utilities arrayList
        String unitNum = UserInfo.get_UnitNum(); //Will get only the number from the "Unit '1'"
        updatestatus.setStatus(unitNum);

        try {

            Statement state = connect().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);


            //Get the latest transaction ID
            ResultSet getMaxTranId = state.executeQuery("SELECT MAX(tran_id) as maxTranId FROM apartment.transaction");

            getMaxTranId.next();
            int maxTranId = getMaxTranId.getInt("maxTranId");

            //Select * Columns of Transaction Table
            ResultSet result = state.executeQuery("SELECT * FROM apartment.transaction");

            result.moveToInsertRow();

            //Update the newest Transaction ID in the Transaction Table
            int trans_id = maxTranId + 1; // Increment the max tran_id
            result.updateInt("tran_id", trans_id);

            //Insert the Discount Code
            result.updateDouble("Discount_code",UserInfo.get_DiscountCode()*100);

            //Insert the Date
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = formatter.parse(formatter.format(new java.util.Date()));
            result.updateDate("Date", new Date(date.getTime()));

            //Insert the Payment Method
            result.updateString("payment_method",UserInfo.get_PaymentMode());

            //Amount paid
            result.updateDouble("amount_pay",UserInfo.getAmount_paid());

            //Insert the  Rent_total
            result.updateDouble("rent_total",UserInfo.getRent_total());

            //Insert the  monthly due
            result.updateDouble("monthly_due_amount",UserInfo.getMonthly_total());

            //Insert the duration
            result.updateString("duration",UserInfo.get_Duration());

            //Insert Utilities (1 = YES / 0 = NONE)
            util=UserInfo.get_Utilities();

            for(int i=0;i<util.size();i++){

                switch (util.get(i)) {
                    case "Amenities" -> result.updateInt("amenities", 1);
                    case "Cable" -> result.updateInt("Cable", 1);
                    case "Wi-Fi" -> result.updateInt("wifi", 1);
                    default -> result.updateInt("water", 1);
                }

            }

            //Insert Foreign keys (User ID and Apartment ID)
            result.updateInt("user_id",UserInfo.get_User_id());
            result.updateInt("apart_id", UserInfo.getApart_id());

            result.insertRow();
            result.beforeFirst();

            //Set Status to 1 (Meaning done)
            JOptionPane.showMessageDialog(null, "Transaction Successful.");

            state.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }

}
