package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    private static final String BRAVE_PATH_WINDOWS = "C:/Program Files/BraveSoftware/Brave-Browser/Application/brave.exe";
    private static final String BRAVE_PATH_MAC     = "/Applications/Brave Browser.app/Contents/MacOS/Brave Browser";
    private static final String BRAVE_PATH_LINUX   = "/usr/bin/brave-browser";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setBinary(getBraveBinaryPath());

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String getBraveBinaryPath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return BRAVE_PATH_WINDOWS;
        } else if (os.contains("mac")) {
            return BRAVE_PATH_MAC;
        } else {
            return BRAVE_PATH_LINUX;
        }
    }
}
