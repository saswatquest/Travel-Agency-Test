import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class TravelAgencyPage extends TestDriver {

    public TravelAgencyPage() {}

    public boolean BookATrip(String TravelPage, Hashtable<String, String> CustomerDetails)
    {
        boolean _errorstatus = false;
        WebDriver driver = InvokeURL(TravelPage);
        Select fromPort = new Select(GetControlbyNAME(driver, "fromPort"));
        Select toPort = new Select(GetControlbyNAME(driver, "toPort"));


        for(int from = 0; from < fromPort.getOptions().size(); from++)
        {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            fromPort = new Select(GetControlbyNAME(driver, "fromPort"));
            fromPort.selectByIndex(from);

            for (int to = 0; to < toPort.getOptions().size(); to++)
            {
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                toPort = new Select(GetControlbyNAME(driver, "toPort"));
                toPort.selectByIndex(to);

                if (fromPort.getFirstSelectedOption().getText() == toPort.getFirstSelectedOption().getText())
                {
                    _errorstatus = true;
                    break;
                }
                else
                {
                    WebElement FindFlight = GetControlbyXpath(driver, "/html/body/div[3]/form/div/input");
                    FindFlight.click();

                    Hashtable<String, String> BookingDetails = null;
                    Hashtable<String, Hashtable<String, String>> FlightChart = FetchFlightChart(driver);


                    for (int flightoptions = 0; flightoptions < FlightChart.size(); flightoptions++)
                    {
                        //BookingDetails = BookFlight(driver, FlightChart.ElementAt(flightoptions).Key, FlightChart, CustomerDetails);
                        Object keys = FlightChart.keySet().toArray()[flightoptions];
                        String Flightkey = keys.toString();


                        BookingDetails = BookFlight(driver,Flightkey,FlightChart,CustomerDetails);
                        if (BookingDetails.get("Id") == null || BookingDetails.get("Id") == "")
                        {
                            _errorstatus = true;
                        }
                        else if (flightoptions < FlightChart.size() - 1)
                        {
                            WebElement TravelTheWorld = GetControlbyXpath(driver, "/html/body/div[1]/div/div/a[2]");
                            TravelTheWorld.click();

                            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

                            fromPort = new Select(GetControlbyNAME(driver, "fromPort"));
                            fromPort.selectByIndex(from);

                            toPort = new Select(GetControlbyNAME(driver, "toPort"));
                            toPort.selectByIndex(to);

                            WebElement FlightFind = GetControlbyXpath(driver, "/html/body/div[3]/form/div/input");
                            FlightFind.click();
                            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

                            FlightChart = null;
                            FlightChart = FetchFlightChart(driver);

                        }
                    }
                }

                WebElement TravelWorld = GetControlbyXpath(driver, "/html/body/div[1]/div/div/a[2]");
                TravelWorld.click();

                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

                fromPort = new Select(GetControlbyNAME(driver, "fromPort"));
                fromPort.selectByIndex(from);

                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                toPort = new Select(GetControlbyNAME(driver, "toPort"));
            }
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            fromPort = new Select(GetControlbyNAME(driver, "fromPort"));
        }


        driver.close();
        driver.quit();
        return _errorstatus;

    }

    private Hashtable<String, Hashtable<String, String>> FetchFlightChart(WebDriver driver)
    {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        int Row_count = GetControlCount(driver, "/html/body/div[2]/table/tbody/tr");
        int Col_count = GetControlbyXpath(driver, "/html/body/div[2]/table/tbody/tr").findElements(By.tagName("td")).size();

        Hashtable<String, Hashtable<String, String>> FlightChart = new Hashtable<String, Hashtable<String, String>>();
        Hashtable<String, String> FlightData = null;

        for (int row = 1; row <= Row_count; row++)
        {
            String Flightno = null;
            String keyvalue = null;
            FlightData = new Hashtable<String, String>();

            for (int col = 1; col <= Col_count; col++)
            {
                WebElement element = null;

                if (col == 1)
                {
                    element = GetControlbyXpath(driver, "/html/body/div[2]/table/tbody/tr[" + row + "]/td[" + col + "]/input");
                    keyvalue = "/html/body/div[2]/table/tbody/tr[" + row + "]/td[" + col + "]/input";
                }
                else
                {
                    element = GetControlbyXpath(driver, "/html/body/div[2]/table/tbody/tr[" + row + "]/td[" + col + "]");
                    keyvalue = element.getText();
                }

                String header = GetControlbyXpath(driver, "/html/body/div[2]/table/thead/tr/th[" + col + "]").getText();
                String flightheader = "Flight #";

                if (header.equals(flightheader))
                {
                    Flightno = keyvalue;
                }

                FlightData.put(header, keyvalue);
            }

            // Assuming a flight number is unique for a particular day
            FlightChart.put(Flightno, FlightData);
        }

        return FlightChart;
    }

    private Hashtable<String, String> BookFlight(WebDriver driver,String FlightNo, Hashtable<String, Hashtable<String, String>> FlightChart, Hashtable<String, String> CustomerDetails)
    {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Hashtable<String, String> BookingDetails = new Hashtable<String, String>();
        Hashtable<String, String> FlightDetails = new Hashtable<String, String>();

        FlightDetails = FlightChart.get(FlightNo);

        WebElement ChooseFlightButton = GetControlbyXpath(driver, FlightDetails.get("Choose"));
        ChooseFlightButton.click();

        WebElement TotalCost = GetControlbyXpath(driver, "/html/body/div[2]/p[5]");
        String FinalCost = TotalCost.getText();


        WebElement Name = GetControlbyID(driver, "inputName");
        Name.sendKeys(CustomerDetails.get("Name"));

        WebElement Address = GetControlbyID(driver, "address");
        Address.sendKeys(CustomerDetails.get("address"));

        WebElement city = GetControlbyID(driver, "city");
        city.sendKeys(CustomerDetails.get("city"));

        WebElement state = GetControlbyID(driver, "state");
        state.sendKeys(CustomerDetails.get("state"));

        WebElement zipCode = GetControlbyID(driver, "zipCode");
        zipCode.sendKeys(CustomerDetails.get("zipCode"));

        //Went with default value.
        //WebElement cardType = GetControlbyID(driver, "cardType");
        //cardType.sendKeys("TestCustomer");

        WebElement creditCardNumber = GetControlbyID(driver, "creditCardNumber");
        creditCardNumber.sendKeys(CustomerDetails.get("creditCardNumber"));

        WebElement creditCardMonth = GetControlbyID(driver, "creditCardMonth");
        creditCardMonth.clear();
        creditCardMonth.sendKeys(CustomerDetails.get("creditCardMonth"));

        WebElement creditCardYear = GetControlbyID(driver, "creditCardYear");
        creditCardYear.clear();
        creditCardYear.sendKeys(CustomerDetails.get("creditCardYear"));

        WebElement nameOnCard = GetControlbyID(driver, "nameOnCard");
        nameOnCard.sendKeys(CustomerDetails.get("nameOnCard"));

        WebElement PurchaseFlight = GetControlbyXpath(driver, "/html/body/div[2]/form/div[11]/div/input");
        PurchaseFlight.click();

        BookingDetails.put("Id", GetControlbyXpath(driver, "/html/body/div[2]/div/table/tbody/tr[1]/td[2]").getText());
        BookingDetails.put("Status", GetControlbyXpath(driver, "/html/body/div[2]/div/table/tbody/tr[2]/td[2]").getText());
        BookingDetails.put("Amount", GetControlbyXpath(driver, "/html/body/div[2]/div/table/tbody/tr[3]/td[2]").getText());
        BookingDetails.put("Card Number", GetControlbyXpath(driver, "/html/body/div[2]/div/table/tbody/tr[4]/td[2]").getText());
        BookingDetails.put("Expiration", GetControlbyXpath(driver, "/html/body/div[2]/div/table/tbody/tr[5]/td[2]").getText());
        BookingDetails.put("Auth Code", GetControlbyXpath(driver, "/html/body/div[2]/div/table/tbody/tr[6]/td[2]").getText());
        BookingDetails.put("Date", GetControlbyXpath(driver, "/html/body/div[2]/div/table/tbody/tr[7]/td[2]").getText());
        BookingDetails.put("FinalCost", FinalCost);

        return BookingDetails;
    }

}
