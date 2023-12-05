public class Compute extends UserInfo{


    public int getUtilfee(){
        int utilfee=0;

        for (int i = 0; i < get_Utilities().size(); i++){
            switch (get_Utilities().get(i)) {
                case "Amenities" -> utilfee += 200;
                case "Wi-Fi" -> utilfee += 300;
                case "Cable" -> utilfee += 400;
                default -> utilfee += 100; //Water
            }
        }
        return utilfee;

    }
    public int getmonths(){

        return switch (get_Duration()) {
            case "3 months" -> 2;
            case "6 months" -> 5;
            case "1 year" -> 11;
            default -> 0;
        };
    }

}
