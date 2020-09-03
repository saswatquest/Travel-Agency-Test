import java.util.Hashtable;

public class TravelAgencyTest {

    public static void main(String[] args)
    {
        BookATrip_TEST();

    }

    public static void BookATrip_TEST()
    {
        String TravelSite = "http://blazedemo.com/";
        Hashtable <String,String> CustomerDetails = new Hashtable<String,String>();
        CustomerDetails.put("Name", "TestCustomer");
        CustomerDetails.put("address", "address");
        CustomerDetails.put("city", "city");
        CustomerDetails.put("state", "state");
        CustomerDetails.put("zipCode", "121212");
        CustomerDetails.put("creditCardNumber", "1234123412341234");
        CustomerDetails.put("creditCardMonth", "03");
        CustomerDetails.put("creditCardYear", "2021");
        CustomerDetails.put("nameOnCard", "TestCustomer");

        TravelAgencyPage Agency = new TravelAgencyPage();
        boolean IsAnyError = Agency.BookATrip(TravelSite, CustomerDetails);

        // IF IsAnyError is false then the fail condition would have satisfied, please check in BookATrip method for conditions.
//        Assert.IsFalse(IsAnyError);



    }



}
