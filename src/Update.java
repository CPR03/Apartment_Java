import java.sql.*;


public class Update extends Database{



    //Update Balance
    public void UpdateBalance(){

        int user = UserInfo.get_User_id(); //Get userID
        double currentbal = UserInfo.get_Balance(); //Check Current balance
        double newbal = currentbal-UserInfo.getMonthly_total(); //Get New Balance (Note: Total Price is the total expenses of renting the unit)

        try {

            connect().setAutoCommit(true);

            PreparedStatement stmt = connect().prepareStatement("UPDATE users SET Balance = ? WHERE user_id = ?"); //(Note: ? is a placeholder)
            stmt.setDouble(1,newbal); //.set utilizes the '?'
            stmt.setInt(2,user);

            stmt.executeUpdate();
            stmt.close();
            connect().close();

        } catch (Exception exc) {

            exc.printStackTrace();
        }
    }

    //Set New Status of the Apartment
    public void setStatus(String unitNum){

        try {

            connect().setAutoCommit(true);

            PreparedStatement stmt = connect().prepareStatement("UPDATE apartment_unit SET status = ? WHERE unit_number = ?"); //(Note: ? is a placeholder)
            stmt.setString(1, "Unavailable"); //.set utilizes the '?'
            stmt.setString(2,unitNum);

            stmt.executeUpdate();
            stmt.close();
            connect().close();

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    //Add Balance
    public void addbalance(double addition){

        try {

            connect().setAutoCommit(true);

            PreparedStatement stmt = connect().prepareStatement("UPDATE users SET Balance = ? WHERE user_id = ?"); //(Note: ? is a placeholder)
            stmt.setDouble(1, UserInfo.get_Balance()+addition); //.set utilizes the '?'
            stmt.setInt(2,UserInfo.get_User_id());

            stmt.executeUpdate();
            stmt.close();
            connect().close();

        } catch (Exception exc) {

            exc.printStackTrace();
        }
    }
}
