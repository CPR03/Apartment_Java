import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class Retrieve extends Database{


    @Override
    public Statement connect() {
        Statement state = null;
        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment", "root", "root");
            state = con.createStatement();


        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return state;
    }
    public double checkBalance(String username){

        double bal=0;
        try {
            Statement state = connect();
            ResultSet result = state.executeQuery("SELECT Balance FROM apartment.users WHERE userName ='"+username+"'");

            while(result.next())
                bal = result.getDouble("Balance");

        } catch (Exception exc) {

            exc.printStackTrace();
        }

        return bal;
    }
    public boolean checkAccount(String username,String password){
        boolean exist=false;
        try {
            Statement state = connect();
            ResultSet result = state.executeQuery("SELECT * FROM apartment.users where userName='"+username+"'");

            int flag = 0; //tell if user OK

            while (result.next()) {
                int  user_id=result.getInt("user_id");
                String userNameFromDatabase = result.getString("userName");
                String userPasswordFromDatabase = result.getString("userPassword");

                if (userNameFromDatabase.equals(username)) {
                    flag = 1;

                    //if password OK Open Dashboard
                    if (userPasswordFromDatabase.equals(password)){
                        //save the login for every object to be accessed

                        Login_Details log = new Login_Details(user_id,username);
                        log.saveinstance();


                        //New


                        exist=true;

                    }


                    //if wrong password
                    else {
                        JOptionPane.showMessageDialog(null, "Wrong Password!", "Error", JOptionPane.WARNING_MESSAGE);
                    }

                    break;

                }
            }

            if (flag == 0) {
                JOptionPane.showMessageDialog(null, "User not found or exists.", "Error", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception exc) {

            exc.printStackTrace();
        }
        return exist;
    }

    public ResultSet checkApartmentinfo(){
        ResultSet result = null;
        try {

            Statement state = connect();
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


    public String checktype(){
        String type = "";
        try {
            Statement state = connect();

            //Get row count from User ID
            ResultSet result = state.executeQuery("SELECT COUNT(*) as Rowcount FROM apartment.transaction WHERE user_id ='"+UserInfo.get_User_id()+"'");
            result.next();

            int count = result.getInt("Rowcount");
            result.close();

            //Check Row Count
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

    public  ArrayList get_LastTransaction(){

        Image image; //unit photo
        double remaining; //remaining balance
        String duration; //duration of stay

        ArrayList<Object> dashboard = new ArrayList<>();

        try {

            Statement state = connect();

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

    public  ArrayList get_lastTrans(){
        ArrayList<Object> last_transaction = new ArrayList<>();

        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment", "root", "root");
            Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

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
            con.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return last_transaction;
    }




}
