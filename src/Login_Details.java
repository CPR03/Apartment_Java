import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//Class Containing Login Details
public class Login_Details extends UserInfo{

    //Encapsulation
    private final String username;
    private final int userid;

    //Constructor
    public Login_Details(int Userid,String user){

        userid=Userid;
        username = user;

    }

    //Get Username
    public String getusername() {
        return username;
    }

    //Get UserID
    public int getUserid(){
        return userid;
    }

    //Save info to User Info
    public void saveinstance(){

        UserInfo.setUser_id(getUserid());
        UserInfo.setUsername(getusername());

    }
}
