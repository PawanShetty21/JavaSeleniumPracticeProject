package testCases;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class Tables {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverManager.chromedriver().setup();
        driver.manage().window().maximize();
        driver.get("https://testautomationpractice.blogspot.com/");

        /*

table
//table[@name="BookTable"]

table header
//table[@name="BookTable"]//tr//th

table data
//table[@name="BookTable"]//td

table data of first column Bookname
//table[@name="BookTable"]//td[1]

Product 2 checkbox
//td[text()='Product 2']//following-sibling::td[input[@type='checkbox']]

 */

       List<WebElement> table_header = driver.findElements(By.xpath("//table[@name='BookTable']//tr//th"));

       for(WebElement header:table_header){
          String header_value = header.getText();
           System.out.println(header_value);

       }

        Actions action = new Actions(driver);

        WebElement  ele1 = driver.findElement(By.xpath("//td[text()='Product 2']//following-sibling::td//input"));

        action.scrollToElement(ele1).perform();

        ele1.click();




        Thread.sleep(5000);

        driver.quit();

    }
}
