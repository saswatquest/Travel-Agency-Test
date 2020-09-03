import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class TestDriver {

    public TestDriver() {}


    public WebDriver InvokeURL(String url)
    {
        System.setProperty("webdriver.gecko.driver","C:\\Program Files\\Java\\geckodriver-v0.27.0-win64\\geckodriver.exe" );
        FirefoxOptions browseroptions = new FirefoxOptions();
        browseroptions.setCapability("marionette", true);
        WebDriver driver= new FirefoxDriver(browseroptions);
        driver.get(url);
        return driver;
    }

    public WebElement GetControlbyID(WebDriver client,String ID)
    {
        WebElement control;
        control = client.findElement(By.id(ID));
        return control;
    }

    public WebElement GetControlbyNAME(WebDriver client,String NAME)
    {
        WebElement control;
        control = client.findElement(By.name(NAME));
        return control;
    }

    public WebElement GetControlbyCLASSNAME(WebDriver client,String CLASSNAME)
    {
        WebElement control;
        control = client.findElement(By.className(CLASSNAME));
        return control;
    }

    public WebElement GetControlbyXpath(WebDriver client,String xpath)
    {
        WebElement control;
        control = client.findElement(By.xpath(xpath));
        return control;
    }

    public WebElement GetControlbyTagName(WebDriver client,String TagName)
    {
        WebElement control;
        control = client.findElement(By.tagName(TagName));
        return control;
    }

    public WebElement GetControlbyLink(WebDriver client,String Link)
    {
        WebElement control;
        control = client.findElement(By.linkText(Link));
        return control;
    }

    public int GetControlCount(WebDriver client,String xpath)
    {
        int count = 0;
        count = client.findElements(By.xpath(xpath)).size();
        return count;
    }




}
