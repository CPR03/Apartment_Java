import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

//Class Containing Balance Checker, Account Checker, Account Type, Last Transaction
public class Retrieve extends Database{



    //Will check the user Balance
    public double checkBalance(String username){

        double bal=0;

        try {

            Statement state = connect().createStatement();
            ResultSet result = state.executeQuery("SELECT Balance FROM apartment.users WHERE userName ='"+username+"'");

            //Get balance
            while(result.next())
                bal = result.getDouble("Balance");

        } catch (Exception exc) {

            exc.printStackTrace();
        }

        return bal;
    }

    Error err = new Error();
    //Will check if account exists
    public boolean checkAccount(String username,String password){

        //flag
        boolean exist=false;

        try {

            Statement state = connect().createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM apartment.users where userName='"+username+"'");

            int flag = 0; //tell if user OK

            while (result.next()) {

                //Get the inputted username, password, and user_id
                int user_id = result.getInt("user_id");
                String userNameFromDatabase = result.getString("userName");
                String userPasswordFromDatabase = result.getString("userPassword");

                //Check if the inputted username is equals to username in the database
                if (userNameFromDatabase.equals(username)) {

                    flag = 1;


                    //if password OK Open Dashboard
                    if (userPasswordFromDatabase.equals(password)){

                        //save the login for every object to be accessed
                        Login_Details log = new Login_Details(user_id,username);
                        log.saveinstance();

                        exist=true;
                    }

                    //if wrong password, display error
                    else {

                        err.wrongPassword();
                    }

                    break;

                }
            }

            //if flag == 0 display password is wrong
            if (flag == 0) {
                err.userNotFound();
            }

        } catch (Exception exc) {

            exc.printStackTrace();
        }
        return exist;
    }

    //Will check the apartment Info
    public ResultSet checkApartmentinfo(){

        ResultSet result = null;
        try {

            Statement state = connect().createStatement();

            //Retrieve Apartment: Unit, Photo,
            result = state.executeQuery("SELECT  apartment_unit.*,apartment.unit_photo.*" +
                    "FROM apartment.apartment_unit JOIN apartment.unit_photo " +
                    "ON unit_photo.unit_number = apartment_unit.unit_number" +
                    " WHERE unit_photo.unit_number= '"+UserInfo.get_UnitNum()+"'");

            result.next();

        } catch (Exception exc) {

            exc.printStackTrace();
        }

        return result;
    }

    //Check Account Type
    public String checktype(){

        String type = "";

        try {

            Statement state = connect().createStatement();

            //Get user ID
            ResultSet result = state.executeQuery("SELECT COUNT(*) as Rowcount FROM apartment.transaction WHERE user_id ='"+UserInfo.get_User_id()+"'");
            result.next();


            int count = result.getInt("Rowcount");
            result.close();

            //Check if user ID is old or new
            if(count>0){
                type="old";
            }

            else{
                type="new";
            }


        } catch (Exception exc) {

            exc.printStackTrace();
        }

        return type;
    }

    //Get the last transaction (put to variable) (will be used by the dashboard)
    public  ArrayList get_LastTransaction(String useTransactionVariable){

        Image image; //unit photo
        double remaining; //remaining balance
        String duration; //duration of stay

        ArrayList<Object> dashboard = new ArrayList<>();

        try {

            Statement state = connect().createStatement();

            //Get Unit Photo, Rent Total, Duration of Stay, Transaction ID
            ResultSet result=state.executeQuery(" SELECT apartment_unit.unit_photo, transaction.rent_total, transaction.duration,transaction.user_id,transaction.tran_id\n" +
                    "            FROM apartment.transaction\n" +
                    "            INNER JOIN apartment.apartment_unit ON transaction.apart_id = apartment_unit.apr_id" +
                    " WHERE user_id='"+UserInfo.get_User_id()+"' order by tran_id DESC ");

            //Put Data a local variable
            while (result.next()){

                java.sql.Blob blob = result.getBlob("unit_photo");
                InputStream in = blob.getBinaryStream();
                image= ImageIO.read(in).getScaledInstance(320,200,Image.SCALE_SMOOTH);
                remaining=result.getDouble("rent_total");
                System.out.println(remaining);
                duration=result.getString("duration");

                //Add unit photo, duration, and remaining balance to arrayList
                dashboard.add(image);
                dashboard.add(duration);
                dashboard.add(remaining);

            }

            state.close();


        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return dashboard;
    }

    //Get last transaction (put to arrayList) (will be used by PayRent)

    public  ArrayList get_LastTransaction(int useTransactionArray){

        ArrayList<Object> last_transaction = new ArrayList<>();

        try {
            Statement state = connect().createStatement();


            //Get Apartment ID, Apartment Unit Number, Unit Price, and all Columns of Transaction Table
            ResultSet result=state.executeQuery(" SELECT apartment_unit.apr_id,apartment_unit.unit_number,apartment_unit.unit_price, transaction.*" +
                    "            FROM apartment.transaction\n" +
                    "            INNER JOIN apartment.apartment_unit ON transaction.apart_id = apartment_unit.apr_id" +
                    " WHERE user_id='"+UserInfo.get_User_id()+"' order by tran_id DESC");

            //Put Data to the arrayList (last_transaction)
            while (result.next()){

                last_transaction.add(result.getInt("apr_id")); //apartment ID
                last_transaction.add(result.getString("unit_number")); //unit number
                last_transaction.add(result.getInt("unit_price")); //unit price
                last_transaction.add(result.getInt("tran_id")); //transaction id
                last_transaction.add(result.getDate("Date")); //date created
                last_transaction.add(result.getDouble("Discount_code")); //discount code
                last_transaction.add(result.getDouble("monthly_due_amount")); //rent per month
                last_transaction.add(result.getString("duration")); //duration of stay
                last_transaction.add(result.getInt("amenities")); //utility
                last_transaction.add(result.getInt("wifi")); //utility
                last_transaction.add(result.getInt("cable")); //utility
                last_transaction.add(result.getInt("water")); //utility
                last_transaction.add(result.getString("payment_method")); //payment method
                last_transaction.add(result.getDouble("rent_total"));

            }

            state.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return last_transaction;
    }

}
