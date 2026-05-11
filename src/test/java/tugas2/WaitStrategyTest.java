package tugas2;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class WaitStrategyTest extends BaseTest {

    private static final String URL = "https://practicetestautomation.com/practice-test-exceptions/";
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private static final By ADD_BUTTON    = By.id("add_btn");
    private static final By SAVE_BUTTON   = By.id("save_btn");
    private static final By EDIT_BUTTON   = By.id("edit_btn");
    private static final By HIDE_BUTTON   = By.id("hide_btn");
    private static final By ROW_1         = By.id("row1");
    private static final By ROW_2         = By.id("row2");
    private static final By ROW_1_INPUT   = By.xpath("//div[@id='row1']//input");
    private static final By ROW_2_INPUT   = By.xpath("//div[@id='row2']//input");
    private static final By CONFIRM_BTN   = By.id("confirm_btn");

    @BeforeMethod
    public void navigateTo() {
        driver.get(URL);
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void tc1_tanpaWait_harus_throwNoSuchElementException() {
        driver.findElement(By.id("add_btn")).click();

        driver.findElement(ROW_2_INPUT).sendKeys("Sushi");
    }

    @Test
    public void tc1_denganThreadSleep() throws InterruptedException {
        driver.findElement(ADD_BUTTON).click();

        Thread.sleep(3000);

        WebElement row2Input = driver.findElement(ROW_2_INPUT);
        row2Input.clear();
        row2Input.sendKeys("Sushi");
        driver.findElement(SAVE_BUTTON).click();

        Assert.assertEquals(row2Input.getAttribute("value"), "Sushi");
    }

    @Test
    public void tc1_denganImplicitWait() {
        driver.manage().timeouts().implicitlyWait(TIMEOUT);

        driver.findElement(ADD_BUTTON).click();

        WebElement row2Input = driver.findElement(ROW_2_INPUT);
        row2Input.clear();
        row2Input.sendKeys("Sushi");
        driver.findElement(SAVE_BUTTON).click();

        Assert.assertEquals(row2Input.getAttribute("value"), "Sushi");
    }

    @Test
    public void tc1_denganExplicitWait() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        driver.findElement(ADD_BUTTON).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(ROW_2));

        WebElement row2Input = driver.findElement(ROW_2_INPUT);
        row2Input.clear();
        row2Input.sendKeys("Sushi");
        driver.findElement(SAVE_BUTTON).click();

        wait.until(ExpectedConditions.attributeContains(ROW_2_INPUT, "value", "Sushi"));
        Assert.assertEquals(row2Input.getAttribute("value"), "Sushi");
    }

    @Test(expectedExceptions = TimeoutException.class)
    public void tc5_timeoutTerlaluPendek_harus_throwTimeoutException() {
        driver.findElement(HIDE_BUTTON).click();

        WebDriverWait instantTimeout = new WebDriverWait(driver, Duration.ofMillis(0));
        instantTimeout.until(ExpectedConditions.invisibilityOfElementLocated(ROW_1));
    }

    @Test
    public void tc5_denganThreadSleep() throws InterruptedException {
        driver.findElement(HIDE_BUTTON).click();

        Thread.sleep(3000);

        WebElement row1 = driver.findElement(ROW_1);
        Assert.assertFalse(row1.isDisplayed(), "Row 1 seharusnya tersembunyi");
    }

    @Test
    public void tc5_denganExplicitWait() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        driver.findElement(HIDE_BUTTON).click();

        boolean row1Invisible = wait.until(
            ExpectedConditions.invisibilityOfElementLocated(ROW_1)
        );

        Assert.assertTrue(row1Invisible, "Row 1 seharusnya sudah tidak visible");
    }

    @Test
    public void tc2_denganExplicitWait_inputRow2HarusInteractable() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        driver.findElement(ADD_BUTTON).click();

        WebElement row2Input = wait.until(
            ExpectedConditions.elementToBeClickable(ROW_2_INPUT)
        );

        row2Input.sendKeys("Pasta");
        driver.findElement(SAVE_BUTTON).click();

        Assert.assertEquals(row2Input.getAttribute("value"), "Pasta");
    }

    @Test
    public void tc3_denganExplicitWait_editRow1() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        driver.findElement(EDIT_BUTTON).click();

        WebElement row1Input = wait.until(
            ExpectedConditions.elementToBeClickable(ROW_1_INPUT)
        );

        row1Input.clear();
        row1Input.sendKeys("Ramen");

        driver.findElement(SAVE_BUTTON).click();

        wait.until(ExpectedConditions.attributeContains(ROW_1_INPUT, "value", "Ramen"));
        Assert.assertEquals(row1Input.getAttribute("value"), "Ramen");
    }

    @Test
    public void tc4_denganExplicitWait_hindariStaleReference() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        driver.findElement(EDIT_BUTTON).click();

        WebElement freshRow1Input = wait.until(
            ExpectedConditions.elementToBeClickable(ROW_1_INPUT)
        );

        freshRow1Input.clear();
        freshRow1Input.sendKeys("Nasi Goreng");

        driver.findElement(SAVE_BUTTON).click();

        WebElement savedInput = wait.until(
            ExpectedConditions.visibilityOfElementLocated(ROW_1_INPUT)
        );

        Assert.assertEquals(savedInput.getAttribute("value"), "Nasi Goreng",
            "Nilai input seharusnya 'Nasi Goreng' setelah disimpan");
    }
}
