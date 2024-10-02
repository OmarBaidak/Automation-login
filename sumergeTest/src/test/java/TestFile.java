import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestFile {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
    }

    @Test
    public void checkUserNamePasswordAndLoginBtnAreExisted() {
        WebElement userName = driver.findElement(By.id("user-name"));
        Assert.assertNotNull(userName, "userName Field is Existed");
        WebElement password = driver.findElement(By.id("password"));
        Assert.assertNotNull(password, "Password Field is Existed");
        WebElement loginBtn = driver.findElement(By.id("login-button"));
        Assert.assertNotNull(loginBtn, "Login Button is Existed");
    }

    @Test
    public void checkOnValidCredentials() throws InterruptedException {
        WebElement userName = driver.findElement(By.id("user-name"));
        userName.sendKeys("standard_user");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        WebElement loginBtn = driver.findElement(By.id("login-button"));
        loginBtn.click();
        Thread.sleep(500);
        WebElement logo = driver.findElement(By.xpath("//div[@class='app_logo']"));
        Assert.assertEquals(logo.getText(), "Swag Labs");
    }

    @Test
    public void checkOnInValidData() {
        WebElement userName = driver.findElement(By.id("user-name"));
        userName.sendKeys("InvalidUser");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("InvalidPass");
        WebElement loginBtn = driver.findElement(By.id("login-button"));
        loginBtn.click();
        WebElement error = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertEquals(error.getText(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void checkEmptyCredentials() {
        WebElement userName = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("login-button"));
        loginBtn.click();
        WebElement errorEmptyData = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertEquals(errorEmptyData.getText(), "Epic sadface: Username is required");
        userName.sendKeys("standard_user");
        password.sendKeys("");
        loginBtn.click();
        Assert.assertEquals(errorEmptyData.getText(), "Epic sadface: Password is required");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
