import java.util.ArrayList;

//Class Containing Rent Info
public class Rent_info extends UserInfo{

    private String duration;
    ArrayList<String> utilities;

    //Get Duration of Stay
    public String getDuration() {
        return duration;
    }

    //Set Duration of Stay
    public void setDuration(String duration) {
        this.duration = duration;
    }

    //Set Utilities
    public void setUtilities(ArrayList Utilities) {
        this.utilities=Utilities;
    }

    //Get Utilities
    public ArrayList<String> getUtilities() {
        return utilities;
    }

    //Save to User Info
    @Override
    void saveinstance() {
        UserInfo.set_Duration(getDuration());
        UserInfo.set_Utilities(utilities);
    }
}
