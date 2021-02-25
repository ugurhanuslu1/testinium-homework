import Model.model;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import Model.degiskenler;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import static Model.degiskenler.log4jPath;
import static java.util.concurrent.TimeUnit.SECONDS;

public class girisYap {
    public static WebDriver driver;
    private static String baseUrl;

    private static Logger log = Logger.getLogger(girisYap.class);

    public static model elementPage; //Modelimizin bulunduğu paket dosyası


    @Before
    public void setUp() throws Exception {
        PropertyConfigurator.configure(log4jPath);

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Uğurhan Uslu\\IdeaProjects\\homework\\drivers\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        baseUrl = "https://www.gittigidiyor.com/";


//Driver elementlere erişim için 10 sn bekleme süresi tanınır
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
//Sayfaların beklenmesi için süre aşağıdaki gibi tanınır
        driver.manage().timeouts().pageLoadTimeout(200, SECONDS);

        elementPage = new model(driver);
    }

    @Test
    public void testUyeol() throws Exception {
        driver.get(baseUrl);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        //check if page is loaded
        if (!js.executeScript("return document.readyState").toString().equals("complete")) {
            log.error("page can not be loaded");
            return;
        } else {
            log.info("page loaded successfully");
        }
        //Yazı alanlarını önce  temizleyip sonra gönderiyoruz

        //break if return is not success
        if (!logIn())
            return;

        if (!findProduct(js))
            return;

        if (!addToBasket())
            return;
    }


    public static boolean logIn() {
        try {
            elementPage.girisYapUst().click();

            elementPage.girisYap().click();

            elementPage.kullaniciAdi().clear();
            elementPage.kullaniciAdi().sendKeys(degiskenler.email);

            elementPage.sifre().clear();
            elementPage.sifre().sendKeys(degiskenler.userPassword);

            elementPage.logIn().click();
            log.info("basarılı");
            return true;
        } catch (Exception e) {
            log.info(e);
            return false;
        }
    }

    public static boolean findProduct(JavascriptExecutor js) {

        try {
            elementPage.searchBar().click();
            elementPage.searchBar().sendKeys("bilgisayar");
            elementPage.searchButton().click();
        } catch (Exception e) {
            System.out.println("Arama sayfası yüklenemedi");
            return false;
        }


        js.executeScript("window.scrollBy(0,10000)");
        try {
            elementPage.pageSelector().click();
        } catch (Exception e) {
            System.out.println("Diger sayfalar yüklenmedi");
            return false;
        }

        try {
            elementPage.pageTwo().click();
        } catch (Exception e) {
            System.out.println("İkinci sayfaya gecilemedi");
            return false;
        }

        try {
            js.executeScript("window.scrollBy(0,10000)");
            Thread.sleep(2000);
            elementPage.product().click();

            elementPage.setListPrice(getDoublePrice(elementPage.listingPrice().getText()));
        } catch (Exception e) {
            System.out.println(e);
            //System.out.println(elementPage.product().isDisplayed());
            return false;
        }

        js.executeScript("window.scrollBy(0,300)");
        return true;
    }

    public static boolean addToBasket() throws InterruptedException, ParseException {
        elementPage.addToBasket().click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".gg-d-12 > .gg-ui-btn-default")).click();
        driver.findElement(By.cssSelector(".gg-input-select > .amount")).click();
        elementPage.setBasketPrice(getDoublePrice(elementPage.basketPrice().getText()));
        checkPriceDiff();
        {
            WebElement dropdown = driver.findElement(By.cssSelector(".gg-input-select > .amount"));
            Thread.sleep(1000);
            // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            //  dropdown.findElement(By.xpath("//option[. = '2']")).click();

            dropdown.findElements(By.xpath("//option[@value='2']"));
            Thread.sleep(1000);
            //    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".row > .btn-delete > .gg-icon")).click();
        Thread.sleep(1000);
        //  boolean isEmpty = driver.findElement(By.xpath("//*[@id=\"empty-cart-container\"]/div[1]/div[1]/div/div[2]/h2")).isDisplayed();
        boolean isEmpty = driver.findElement(By.cssSelector("#empty-cart-container > div.gg-d-24 > div:nth-child(1) > div > div.gg-w-22.gg-d-22.gg-t-21.gg-m-18 > h2")).isDisplayed();
        if (isEmpty) {
            log.info("sepet boş");
        } else {
            log.info("sepet dolu");
        }
        return true;
    }

    public static void checkPriceDiff() {
        double basketPrice, listPrice;
        listPrice = elementPage.getListPrice();
        basketPrice = elementPage.getBasketPrice();
        System.out.println(listPrice + " " + basketPrice);
        if (basketPrice != listPrice) {
            System.out.println("Price different with basket = " + (listPrice - basketPrice));
            log.info("Price different with basket = " + (listPrice - basketPrice));
        } else {
            log.info("There are no differences between prices");
        }
    }

    public static double getDoublePrice(String price) throws ParseException {
        price = (price.substring(0, price.length() - 3));

        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        Number number = format.parse(price);
        double d = number.doubleValue();

        return d;
    }

}

