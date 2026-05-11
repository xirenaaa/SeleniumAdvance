package tugas1;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HoverTest extends BaseTest {

    private static final String URL = "https://the-internet.herokuapp.com/hovers";

    @BeforeMethod
    public void navigateTo() {
        driver.get(URL);
    }

    @Test
    public void hoverFirstImage_shouldShowUser1() {
        WebElement firstImage = driver.findElement(
            By.cssSelector(".figure:first-of-type img")
        );

        new Actions(driver)
            .moveToElement(firstImage)
            .perform();

        WebElement captionName = driver.findElement(
            By.cssSelector(".figure:first-of-type .figcaption h5")
        );

        Assert.assertEquals(captionName.getText(), "name: user1",
            "Caption seharusnya menampilkan 'name: user1' setelah hover");
    }
}
