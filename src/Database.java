
import java.sql.Connection;
import java.sql.DriverManager;


//Abstract Class containing the important connector for the SQL
abstract class Database {

      protected Connection connect() {
           Connection con=null;
            try {

                   con = DriverManager.getConnection("jdbc:mysql://localhost:3306/apartment", "root", "root");


            } catch (Exception exc) {
                  exc.printStackTrace();
            }
            return con;
      }

}
