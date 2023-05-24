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
        amazonPage.aramakutusu.sendKeys(ConfigReader.getProperty("amazonAranacakKelime") + Keys.ENTER);

        String expectedIcerik = ConfigReader.getProperty("amazonExpectedIcerik");
        String actualSonucYazisi = amazonPage.aramaSonucuElementi.getText();

        Assert.assertTrue(actualSonucYazisi.contains(expectedIcerik));

        Driver.closeDriver();

    }
}
