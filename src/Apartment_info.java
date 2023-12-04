public class Apartment_info extends UserInfo{
    private String unitNum;
    private int bedcount,price;
    private String status;
    private int apartment_id;

    public void setApartmentid(int apartment_id) {
        this.apartment_id = apartment_id;
    }

    public int getApartmentid() {
        return apartment_id;
    }

    public void setunitNum(String Unitnum) {
        unitNum=Unitnum;
    }
    public String getunitNum(){
        return unitNum;
    }

    public void setPrice(int Price){
        price=Price;
    }
    public int getPrice() {
        return price;
    }

    public void setBedcount(int Bedcount){
        bedcount=Bedcount;
    }
    public int getBedcount(){
        return bedcount;
    }
    public void setStatus(String Status){
        status=Status;
    }
    public String getStatus() {
        return status;
    }

    @Override
    void saveinstance() {
        UserInfo.set_Bedcount(getBedcount());
        UserInfo.set_Price(getPrice());
        UserInfo.set_Status(getStatus());
        UserInfo.setApart_id(getApartmentid());
    }
}
