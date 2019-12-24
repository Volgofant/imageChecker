import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

class ProductsPage {

    private WebDriver driver;

    ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    void getImageUrl(List<String> list) {
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class=\"blockProductGrid__item qaBlockProductGrid__item \"]//img"));
        for (WebElement s : elements) {
            list.add(s.getAttribute("src"));
        }
    }

    private By productImageURL = By.xpath("//body/img");

    String getProductImageURL() {
        return driver.findElement(productImageURL).getAttribute("src");
    }
}

