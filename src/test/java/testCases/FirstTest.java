package testCases;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import utilities.ReadXlsData;

public class FirstTest extends BaseTest {


    // To point specific class which as data provider methods

    @Test(testName = "loginTest1", groups = {"sanity"}, dataProviderClass = ReadXlsData.class, dataProvider = "testData")
    public void login(String username, String password) throws InterruptedException {

        log.debug("application logs......");
        log.info("***  Starting Login Test ***");
        extentTest.info("Starting Login Test "); // To add info on ExtentReporter


        WebElement login_button = driver.findElement(By.xpath(locators.getProperty("login_button_xpath")));
        login_button.click();
        log.info("*** Clicked on login button ***");
        extentTest.info("Clicked on login button"); // To add info on ExtentReporter



        WebElement email_field = driver.findElement(By.xpath(locators.getProperty("email_field_xpath")));
//        email_field.sendKeys("pawansm217@gmail.com");
        email_field.sendKeys(username);
        log.info("*** Entered username ***");



        Thread.sleep(5000);


        WebElement next_button = driver.findElement(By.xpath(locators.getProperty("next_button_xpath")));
        next_button.click();
        log.info("*** Clicked on next button ***");


        Thread.sleep(5000);


        WebElement password_field = driver.findElement(By.xpath(locators.getProperty("password_field_xpath")));
//        password_field.sendKeys("Zoho@zoho");
        password_field.sendKeys(password);
        log.info("*** Entered password ***");


        WebElement signIn_button = driver.findElement(By.xpath(locators.getProperty("signIn_button_xpath")));
        signIn_button.click();
        log.info("*** Clicked on signIn button ***");

        Thread.sleep(5000);


    }
}