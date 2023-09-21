package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testCases.TestLogger;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseTest {

    public static WebDriver driver;
    public static Properties c_prop = new Properties();
    public static Properties locators = new Properties();
    public static FileReader c_fr;
    public static FileReader l_fr;

    public static Logger log; // for logging

    public static ExtentReports extentReports; // For extent Reports to access in multiple methods

    public static ExtentTest extentTest; // For extent Reports to access info, logs etc...

    public static String repName;


    @Parameters("browser")
    @BeforeTest
    //   ITestContext context is dependency injection to get Test Name
    public void setup(ITestContext context, @Optional("chrome") String br) throws IOException {

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

        extentTest = extentReports.createTest(context.getName());

        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities(); // To add browser details

        String device = capabilities.getBrowserName() + capabilities.getBrowserVersion();
        extentTest.assignDevice(device); // To get browser details


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

    @BeforeSuite(alwaysRun = true)
    public void intitializeExtentReports() {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
        repName = "Test-Report-" + timeStamp + ".html";
        File file = new File(System.getProperty("user.dir") + "/reports/" + repName);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);

        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setReportName("Demo Report");

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
        extentReports.flush();

        Desktop.getDesktop().browse(new File(System.getProperty("user.dir") + "/reports/" + repName).toURI());


    }

    @AfterMethod
    public void checkMethodStatus(Method m, ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = null;
            screenshotPath = captureScreenshot(result.getTestContext().getName() + "_" + result.getMethod().getMethodName() + ".jpg");
            extentTest.addScreenCaptureFromPath(screenshotPath);
            extentTest.fail(result.getThrowable());

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass(m.getName() + " test is passed");  // Method m is java reflection
        }

        extentTest.assignCategory(m.getAnnotation(Test.class).groups());
    }

    public static String captureScreenshot(String fileName) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(". /Screenshots/" + fileName);
        try {
            FileUtils.copyFile(sourceFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return destFile.getAbsolutePath();
    }


}