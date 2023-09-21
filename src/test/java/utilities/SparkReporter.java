package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SparkReporter {

    static WebDriver driver;
    static String repName;



    public static void main(String[] args) throws IOException {
        ExtentReports extentReports = new ExtentReports();

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
        repName = "Test-Report-" + timeStamp + ".html";

        File file = new File(System.getProperty("user.dir") +"/reports/" +repName);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
        extentReports.attachReporter(sparkReporter);

        sparkReporter.config().setDocumentTitle("Automation Report"); // Title of the report
        sparkReporter.config().setReportName("Functional Testing"); // Name of the report
//        sparkReporter.config().setTheme(Theme.DARK);

        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", "Assets");
        extentReports.setSystemInfo("Module", "User");
        extentReports.setSystemInfo("Sub Module", "TimeSheet");
        extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environemnt", "QA");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.google.com/");
        String base64 = captureScreenshot();
        String path = captureScreenshot("Google.jpg");


        extentReports.createTest("Screenshot Using Base 64", "This is for attaching screenshot")
                .info("This is info message")
                        .addScreenCaptureFromBase64String(base64, "Google homepage");

        extentReports.createTest("Screenshot Using Path", "This is for attaching screenshot")
                .info("This is info message")
                .addScreenCaptureFromPath(path, "Google homepage");


        extentReports.createTest("Screenshot at log level using base64", "This is for attaching screenshot")
                .info("This is info message")
                .fail(MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Failed screenshot").build());


        extentReports.createTest("Screenshot at log level using path", "This is for attaching screenshot")
                .info("This is info message")
                .fail(MediaEntityBuilder.createScreenCaptureFromPath(path, "Failed screenshot").build());

        Throwable tw = new Throwable("This is Throwable exception");
        extentReports.createTest("Screenshot at log level with Exception", "This is for attaching screenshot")
                .info("This is info message")
                .fail(tw,MediaEntityBuilder.createScreenCaptureFromPath(path, "Failed screenshot").build());

        extentReports.flush();
        driver.quit();
        Desktop.getDesktop().browse(new File(System.getProperty("user.dir") +"/reports/" +repName).toURI());


    }


    public static String captureScreenshot() {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        String base64Code = takesScreenshot.getScreenshotAs(OutputType.BASE64);
        System.out.println("Screenshot saved successfully");
        return base64Code;
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



