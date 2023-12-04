import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login_Details extends UserInfo{
    //Encapsulation
    private final String username;
    private final int userid;


    //Constructor
    public Login_Details(int Userid,String user){


        userid=Userid;
        username = user;

    }

    public String getusername() {
        return username;
    }
    public int getUserid(){
        return userid;
    }

    public void saveinstance(){
        UserInfo.setUser_id(getUserid());
        UserInfo.setUsername(getusername());

    }
}
