package testCases;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestLogger {


    public static WebDriver driver;
    public static Logger log;

    public static void main(String[] args) {

            System.err.println("Start");

            System.setProperty("log4j2.configurationFile", "./log4j2.xml");
            log = LogManager.getLogger(TestLogger.class.getName());

            System.err.println("Initialized Logger");
            log.trace("TRACE");
            log.debug("DEBUG");
            log.info("INFO");
            log.warn("WARN");
            log.error("ERROR");
            log.fatal("FATAL");

            System.err.println("END");
    }
}