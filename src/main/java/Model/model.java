package Model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Random;

public class model {
    public static WebDriver driver;

    public double listPrice, basketPrice;

    public model(WebDriver webDriver) {
        driver = webDriver;
    }

    public WebElement girisYapUst() {
        return driver.findElement(By.cssSelector(".gekhq4-5 > .gekhq4-6 path"));
    }

    //header[@id='main-header']/div[3]/div/div/div/div[3]/div/div/div/div[2]

    public WebElement girisYap() {
        return driver.findElement(By.xpath("//header[@id=\'main-header\']/div[3]/div/div/div/div[3]/div/div/div[2]/div/div/div/a"));
    }

    public WebElement kullaniciAdi() {
        return driver.findElement(By.id("L-UserNameField"));
    }

    public WebElement sifre() {
        return driver.findElement(By.id("L-PasswordField"));

    }

    public WebElement logIn() {
        return driver.findElement(By.xpath("//*[@id=\"gg-login-enter\"]"));
    }


    public WebElement btnUyeOl() {
        return driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/main/article/div/div/form/div[2]/button"));
    }

    public WebElement searchBar() {
        return driver.findElement(By.name("k"));
    }

    public WebElement searchButton() {
        return driver.findElement(By.cssSelector(".hKfdXF > span"));
    }

    public WebElement pageTwo() {
        return driver.findElement(By.xpath("//a[contains(text(),'2')]"));
    }

    public WebElement pageSelector() {
        return driver.findElement(By.cssSelector(".pt30"));
    }

    public WebElement product() {
        Random rand = new Random();
        int upperbound = 47;
        //generate random values from 0-48
        int random = rand.nextInt(upperbound) + 1;
        String key = String.valueOf(random);
        String randomXpath = "//li[" + key + "]/a/div/div/div/div/h3/span";

        return driver.findElement(By.xpath(randomXpath));
    }

    public WebElement listingPrice() {
        return driver.findElement(By.xpath("//*[@id=\"sp-price-highPrice\"]"));
    }

    public WebElement basketPrice() {
        return driver.findElement(By.xpath("//*[@id=\"submit-cart\"]/div/div[2]/div[3]/div/div[1]/div/div[5]/div[1]/div/ul/li[1]/div[2]"));
    }

    public WebElement addToBasket() {
        return driver.findElement(By.cssSelector("#add-to-basket"));
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getBasketPrice() {
        return basketPrice;
    }

    public void setBasketPrice(double basketPrice) {
        this.basketPrice = basketPrice;
    }
}