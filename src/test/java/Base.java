import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by tku on 7/25/2016.
 */
public class Base {

    private WebDriver driver;

    @BeforeSuite
    public void start(){
        driver = new ChromeDriver();
        driver.get("http://www.seosprint.net/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void first(){
        driver.findElement(By.id("mnu302")).click();
        driver.findElement(By.cssSelector("form.auth input[type='text']")).sendKeys("dfgd");
        driver.findElement(By.cssSelector("form.auth input[type='password']")).sendKeys("dfgd");
        driver.findElement(By.cssSelector("iframe[title='recaptcha widget']"));
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='recaptcha widget']")));
        driver.findElement(By.id("recaptcha-anchor-label")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().defaultContent();
        driver.findElement(By.cssSelector("form.auth>input.auth-enter")).click();
    }

    @AfterSuite
    public void end(){
        driver.quit();
    }
}
