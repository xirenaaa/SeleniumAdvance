package tugas1;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class KeyPressesTest extends BaseTest {

    private static final String URL = "https://the-internet.herokuapp.com/key_presses";

    @BeforeMethod
    public void navigateTo() {
        driver.get(URL);
    }

    @Test
    public void pressShiftInInput_shouldDisplayCorrectMessage() {
        WebElement inputField = driver.findElement(By.id("target"));

        new Actions(driver)
            .click(inputField)
            .keyDown(Keys.SHIFT)
            .keyUp(Keys.SHIFT)
            .perform();

        WebElement resultText = driver.findElement(By.id("result"));

        Assert.assertEquals(resultText.getText(), "You entered: SHIFT",
            "Hasil seharusnya menampilkan 'You entered: SHIFT'");
    }
}
