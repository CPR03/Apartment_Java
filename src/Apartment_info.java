
//Class containing information about the apartment info
public class Apartment_info extends UserInfo{
    private String unitNum;
    private int bedcount,price;
    private String status;
    private int apartment_id;
    private String address;


    //Set Apartment ID
    public void setApartmentid(int apartment_id) {
        this.apartment_id = apartment_id;
    }

    //Get Apartment ID
    public int getApartmentid() {
        return apartment_id;
    }

    //Set Unit Number
    public void setunitNum(String Unitnum) {
        unitNum=Unitnum;
    }

    //Get Unit Number
    public String getunitNum(){
        return unitNum;
    }

    //Set Unit Price
    public void setPrice(int Price){
        price=Price;
    }

    //Get Unit Price
    public int getPrice() {
        return price;
    }

    //Set Bedcount
    public void setBedcount(int Bedcount){
        bedcount=Bedcount;
    }

    //Get Bedcount
    public int getBedcount(){
        return bedcount;
    }

    //Set Apartment Status
    public void setStatus(String Status){
        status=Status;
    }

    //Get Apartment Status
    public String getStatus() {
        return status;
    }

    public void setAddress(String Address){address=Address;}
    public String getAddress(){return address;}
    //Save Apartment Info to User Info
    @Override
    void saveinstance() {
        UserInfo.set_Bedcount(getBedcount());
        UserInfo.set_Price(getPrice());
        UserInfo.set_Status(getStatus());
        UserInfo.setApart_id(getApartmentid());
        UserInfo.set_address(getAddress());

    }
}
