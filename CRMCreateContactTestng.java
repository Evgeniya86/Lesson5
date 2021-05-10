import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class CRMCreateContactTestng {
    WebDriver driver;
    WebDriverWait webDriverWait;
    private static final String BASE_URL = "https://crm.geekbrains.space/";


    @BeforeSuite
    void setupDataBase() {
        System.out.println("Before suite setup database");
    }

    @BeforeTest
    void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    void setUpBrowser() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 5);
        driver.get(BASE_URL);
        login();
    }

    @Test(description = "Регистрация в crm", enabled = true)
    void CreateContact() {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//span[text()='Контрагенты']/ancestor::a")))
                .build()
                .perform();
        driver.findElement(By.xpath("//li[@data-route='crm_contact_index']/a")).click();
        WebElement until = webDriverWait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//a[text()='Создать контактное лицо']")));

        driver.findElement(By.xpath("//a[text()='Создать контактное лицо']")).click();

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//input[@name='crm_contact[lastName]']")));


        driver.findElement(By.xpath("//input[@name='crm_contact[lastName]']")).sendKeys("Норина");

        driver.findElement(By.xpath("//input[@name='crm_contact[firstName]']")).sendKeys("Александра");

        driver.findElement(By.xpath("//span[text()='Укажите организацию']/..")).click();
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//input[@class='select2-input select2-focused']")));
        driver.findElement(By.xpath("//input[@class='select2-input select2-focused']")).sendKeys("1234");
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//input[@class='select2-input select2-focused']")));
        driver.findElement(By.xpath("//input[@class='select2-input select2-focused']")).sendKeys(Keys.ENTER);

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='crm_contact[jobTitle]']")));
        driver.findElement(By.xpath("//input[@name='crm_contact[jobTitle]']")).sendKeys("менеджер");
        List<WebElement> headers = driver.findElements(
                By.xpath("//button[contains(text(),'Сохранить и закрыть')]"));
        Assert.assertEquals(headers.get(0).getText(), "Сохранить и закрыть");
        driver.findElement(By.xpath("//button[contains(text(),'Сохранить и закрыть')]")).click();

        webDriverWait.until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[text()='Контактное лицо сохранено']")));

        driver.findElement(By.xpath("//*[text()='Контактное лицо сохранено']"));

    }

    private void login() {
        driver.get(BASE_URL);
        driver.findElement(By.id("prependedInput")).sendKeys("Applanatest1");
        driver.findElement(By.id("prependedInput2")).sendKeys("Student2020!");
        driver.findElement(By.id("_submit")).click();
    }
    @AfterMethod
    void closeBrowser() {
    driver.quit();
    }
}
