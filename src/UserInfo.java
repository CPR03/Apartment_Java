import java.util.ArrayList;

abstract class UserInfo extends Create{
    //Userinfo
    private static String username;
    private static double balance;
    private static int user_id;
    //Apartment info
    private static int apart_id;
    private static String unitNum;
    private static int price;
    private static int bedcount;
    private static String status;
    private static String address;
    //Rent info
    private static String duration;
    static ArrayList<String> utilities;
    private static double monthly_total;
    private static double rent_total;
    private static double DiscountCode;
    private static String PaymentMode;
    private static double amount_paid;




    //Userinfo
    public static void setUser_id(int UserId){
        user_id=UserId;
    }
    public static int get_User_id(){
        return user_id;
    }

    public static void setUsername(String Username){
        username=Username;
    }
    public static String get_username(){
        return username;
    }

    public static void setBalance(){
        Retrieve retrieve = new Retrieve();
        balance=retrieve.checkBalance(get_username());

    }
    public static double get_Balance(){
        setBalance();
        return balance;
    }


    //Apartment info

    public static void setApart_id(int apart_id) {
        UserInfo.apart_id = apart_id;
    }

    public static int getApart_id() {
        return apart_id;
    }

    public static void setUnitNum(String unit){
        unitNum=unit;
    }
    public static String get_UnitNum(){
        return  unitNum;
    }
    public static void set_Price(int Price){
        price=Price;
    }
    public static int get_Price() {
        return price;
    }

    public static void set_Bedcount(int Bedcount){
        bedcount=Bedcount;
    }

    public static void set_Status(String Status){
        status=Status;
    }

    public static void set_address(String Address){address=Address;}
    public static String get_address(){return address;}




    //Rent info
    public static void set_Duration(String Duration){
        duration=Duration;
    }
    public static String get_Duration() {
        return duration;
    }

    public static void set_Utilities(ArrayList utilList){

        utilities=utilList;
    }

    public static ArrayList<String> get_Utilities() {
        return utilities;
    }


    //Transaction


    public static void setMonthly_total(double monthlytotal) {
        monthly_total = monthlytotal;
    }

    public static double getMonthly_total() {
        return monthly_total;
    }

    public static void setRent_total(double Renttotal) {
        rent_total = Renttotal;
    }

    public static double getRent_total() {
        return rent_total;
    }

    //Transaction
    public static void set_PaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public static String get_PaymentMode() {
        return PaymentMode;
    }

    public static void set_DiscountCode(double discountCode) {
        DiscountCode = discountCode;
    }

    public static double get_DiscountCode() {
        return DiscountCode;
    }

    public static void setAmount_paid(double amount_paid) {
        UserInfo.amount_paid = amount_paid;
    }

    public static double getAmount_paid() {
        return amount_paid;
    }

    //need to be overidden of every child class
    void saveinstance() {

    }
    static void clearinstance(){
        username=null;
        balance=0;
        user_id=0;

        apart_id=0;
        unitNum=null;;
        price=0;
        bedcount=0;
        status=null;;

        duration=null;;
        utilities=null;
        monthly_total=0;
        rent_total=0;
        DiscountCode=0;
        PaymentMode=null;
        amount_paid=0;
    }




}
