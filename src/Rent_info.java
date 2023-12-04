import java.util.ArrayList;

public class Rent_info extends UserInfo{

    private String duration;
    ArrayList<String> utilities;


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setUtilities(ArrayList Utilities) {
        this.utilities=Utilities;
    }

    public ArrayList<String> getUtilities() {
        return utilities;
    }

    @Override
    void saveinstance() {
        UserInfo.set_Duration(getDuration());
        UserInfo.set_Utilities(utilities);
    }
}
