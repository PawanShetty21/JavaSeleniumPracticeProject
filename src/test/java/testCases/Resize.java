package testCases;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;

import java.util.List;

public class Resize {

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

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

        Thread.sleep(2000);


        Actions action = new Actions(driver);

        WebElement  ele1 = driver.findElement(By.xpath("//div[@class='ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se']"));

//        action.dragAndDropBy(ele1, 1000, 2000).perform();

//        Point p = ele1.getLocation();
//        System.out.println(p.getX());
//        System.out.println(p.getY());


        WebElement  ele2 = driver.findElement(By.xpath("//div[@id='resizable']"));


//
//        Dimension d = ele2.getSize();
//        System.out.println(d.height);
//        System.out.println(d.width);

        Rectangle r = ele2.getRect();
        System.out.println(r.width);
        System.out.println(r.height);

        System.out.println(r.getX());
        System.out.println(r.getY());





        WebElement  ele3 = driver.findElement(By.xpath("//a[normalize-space()='1']"));

        String clr =  ele3.getCssValue("background-color");
        System.out.println(clr);

        Color color = Color.fromString(clr);
        System.out.println(color.asHex());





        Thread.sleep(5000);

        driver.quit();

    }
}
