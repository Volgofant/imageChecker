import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainTest {
    private WebDriver driver;
    private ProductsPage productsPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().version("75.0.3770.140").setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Test
    public void allProductsShouldHaveImage() {
        driver.get("https://shop.westwing.ru/all-products/?q=");
        productsPage = new ProductsPage(driver);
        List<String> list = new ArrayList<String>();
        List<String> listNotHaveImage = new ArrayList<String>();
        int x = 1;
        while (driver.findElements(By.xpath("//div[@class=\"cl__list__next\"]/a[@title=\"Следующий\"]")).size() > 0) {
            driver.get("https://shop.westwing.ru/url_all-products/?page=" + x + "");
            productsPage.getImageUrl(list);
            x++;
        }
        System.out.println(list.size());

        for (String s : list) {
            driver.get(s);
            try {
                if (!(s.equals(productsPage.getProductImageURL()))) {
                    listNotHaveImage.add(s);
                }
            } catch (Exception e) {
                listNotHaveImage.add(s);
            }
        }
        System.out.println("КОЛИЧЕСТВО БИТЫХ КАРТИНОК : " + listNotHaveImage.size());
        for (String s : listNotHaveImage) {
            System.out.println(s);
        }
        Assert.assertTrue(listNotHaveImage.isEmpty());
    }

    @After
    public void setDown() {
        driver.quit();
    }
}
