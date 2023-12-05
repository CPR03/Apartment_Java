
//Class containing transaction Info
public class Transaction_info extends UserInfo{

    private double DiscountCode;
    private String PaymentMode;
    private double rentTotal;

    //Set Payment Method
    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    //Get Payment Method
    public String getPaymentMode() {
        return PaymentMode;
    }

    //Set Discount Code
    public void setDiscountCode(double discountCode) {
        DiscountCode = discountCode;
    }

    //Get Discount Code
    public  double getDiscountCode() {
        return DiscountCode;
    }

    //Set Rent Total
    public void setRentTotal(double rentTotal) {
        this.rentTotal = rentTotal;
    }

    //Get Rent Total
    public double getRentTotal() {
        return rentTotal;
    }

    //Save to User Info
    void saveinstance() {
        UserInfo.set_PaymentMode(getPaymentMode());
        UserInfo.set_DiscountCode(getDiscountCode());
        UserInfo.setRent_total(getRentTotal());

    }
}
