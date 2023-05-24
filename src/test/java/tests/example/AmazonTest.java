package tests.example;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AmazonPage;
import utilities.ConfigReader;
import utilities.Driver;

public class AmazonTest {
    @Test
    public void test(){

        Driver.getDriver().get(ConfigReader.getProperty("amazonUrl"));

        AmazonPage amazonPage = new AmazonPage();
        amazonPage.searchBox.sendKeys(ConfigReader.getProperty("amazonSearchWord") + Keys.ENTER);

        String expectedContent = ConfigReader.getProperty("amazonExpectedContent");
        String actualResultText = amazonPage.searchResultElement.getText();

        Assert.assertTrue(actualResultText.contains(expectedContent));

        Driver.closeDriver();

    }
}
