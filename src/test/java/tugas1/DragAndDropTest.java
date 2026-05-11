package tugas1;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DragAndDropTest extends BaseTest {

    private static final String URL = "https://the-internet.herokuapp.com/drag_and_drop";

    @BeforeMethod
    public void navigateTo() {
        driver.get(URL);
    }

    @Test
    public void dragBoxAtoBoxB_shouldSwitchLabels() {
        WebElement boxA = driver.findElement(By.id("column-a"));
        WebElement boxB = driver.findElement(By.id("column-b"));

        Assert.assertEquals(boxA.findElement(By.tagName("header")).getText(), "A");
        Assert.assertEquals(boxB.findElement(By.tagName("header")).getText(), "B");

        simulateHtml5DragAndDrop(boxA, boxB);

        WebElement boxBHeader = driver.findElement(By.cssSelector("#column-b header"));
        Assert.assertEquals(boxBHeader.getText(), "A",
            "Setelah drag, header Box B seharusnya berubah menjadi 'A'");
    }

    private void simulateHtml5DragAndDrop(WebElement source, WebElement target) {
        String jsScript =
            "var src = arguments[0], tgt = arguments[1];" +
            "var dt = new DataTransfer();" +
            "['dragstart','drag'].forEach(function(t) {" +
            "  src.dispatchEvent(new DragEvent(t, { bubbles: true, cancelable: true, dataTransfer: dt }));" +
            "});" +
            "['dragenter','dragover','drop'].forEach(function(t) {" +
            "  tgt.dispatchEvent(new DragEvent(t, { bubbles: true, cancelable: true, dataTransfer: dt }));" +
            "});" +
            "src.dispatchEvent(new DragEvent('dragend', { bubbles: true, cancelable: true, dataTransfer: dt }));";

        ((JavascriptExecutor) driver).executeScript(jsScript, source, target);
    }
}
