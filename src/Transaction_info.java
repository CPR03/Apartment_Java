public class Transaction_info extends UserInfo{
    private double DiscountCode;
    private String PaymentMode;

    private double rentTotal;


    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setDiscountCode(double discountCode) {
        DiscountCode = discountCode;
    }

    public  double getDiscountCode() {
        return DiscountCode;
    }




    public void setRentTotal(double rentTotal) {
        this.rentTotal = rentTotal;
    }

    public double getRentTotal() {
        return rentTotal;
    }


    void saveinstance() {
        UserInfo.set_PaymentMode(getPaymentMode());
        UserInfo.set_DiscountCode(getDiscountCode());
        UserInfo.setRent_total(getRentTotal());




    }
}
