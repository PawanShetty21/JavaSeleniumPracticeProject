package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import testCases.TestLogger;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseTestRegular {

    public WebDriver driver;
    public static Properties c_prop = new Properties();
    public static Properties locators = new Properties();
    public static FileReader c_fr;
    public static FileReader l_fr;

    public static Logger log; // for logging

    public static ExtentReports extentReports; // For extent Reports to access in multiple methods
    public static String repName;


    @BeforeTest
    @Parameters("browser")
    public void setup(String br) throws IOException {

        System.err.println("Start");
        System.setProperty("log4j2.configurationFile", "./log4j2.xml");
        log = LogManager.getLogger(TestLogger.class.getName());


        if (driver == null) {
            String root_path = System.getProperty("user.dir"); // To get root path

            // To read config properties
            c_fr = new FileReader(root_path + "/src/test/configFiles/config.properties");
            c_prop.load(c_fr);

            // To read locator properties
            l_fr = new FileReader(root_path + "/src/test/configFiles/locators.properties");
            locators.load(l_fr);
        }

        if (br.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            driver = new ChromeDriver();
        } else if (br.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(c_prop.getProperty("testUrl"));
        driver.manage().window().maximize();

        // Enable below code while using config property file to get browser name
//        if (c_prop.getProperty("browser").equalsIgnoreCase("chrome")){
//
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//
//
//        } else if(c_prop.getProperty("browser").equalsIgnoreCase("firefox")){
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//        }

    }

    @AfterTest(alwaysRun = true)
    public void teardown() {

        driver.quit();
    }

    @BeforeSuite
    public void intitializeExtentReports() {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
        repName = "Test-Report-" + timeStamp + ".html";
        File file = new File(System.getProperty("user.dir") + "/reports/" + repName);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);

        extentReports = new ExtentReports();

        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo("Application", "Assets");
        extentReports.setSystemInfo("Module", "User");
        extentReports.setSystemInfo("Sub Module", "TimeSheet");
        extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environemnt", "QA");


    }


    @AfterSuite
    public void generateExtentReports() throws IOException {

        Desktop.getDesktop().browse(new File(System.getProperty("user.dir") + "/reports/" + repName).toURI());


    }


    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";

        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (Exception e) {
            e.getMessage();
        }
        return destination;

    }
}
