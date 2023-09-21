package testCases;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Rough_work {

    public static Logger log;

    public static WebDriver  driver = new ChromeDriver();

    public static void main(String[] args) throws InterruptedException, IOException {


        System.err.println("Start");

        System.setProperty("log4j2.configurationFile", "./log4j2.xml");
        log = LogManager.getLogger(TestLogger.class.getName());


        WebDriverManager.chromedriver().setup();

        log.info("Set up complete");

        driver.get("https://www.zoho.com/");
        driver.manage().window().maximize();




        Thread.sleep(5000);
        WebElement login_button = driver.findElement(By.xpath("//a[@class='login']"));
        login_button.click();
        log.info("Clicked on Login");

        WebElement email_field = driver.findElement(By.xpath("//input[@id='login_id']"));
        email_field.sendKeys("pawansm217@gmail.com");

        captureScreen("screenshotname");


//        WebElement next_button = driver.findElement(By.xpath("//button[@id='nextbtn']"));
//        next_button.click();
//
//        Thread.sleep(5000);
//
//
//        WebElement password_field = driver.findElement(By.xpath("//input[@id='password']"));
//        password_field.sendKeys("Zoho@zoho");
//
//
//        WebElement signIn_button = driver.findElement(By.xpath("//button[@id='nextbtn']"));
//        signIn_button.click();
//
//        Thread.sleep(5000);




        driver.quit();

    }

    public static String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";

        System.out.println("tname: "+ tname);

        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (Exception e) {
            e.getMessage();
        }
        return destination;

    }
}
