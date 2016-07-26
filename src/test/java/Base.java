import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by tku on 7/25/2016.
 */
public class Base {

    private WebDriver driver;
    private BrowserMobProxy server;
    private DesiredCapabilities capabilities;

    @BeforeSuite
    public void start(){
        server = new BrowserMobProxyServer();

        server.addRequestFilter(new RequestFilter() {
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                if (request.headers().contains("User-Agent")) {
                    request.headers().set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");
                }
                // in the request filter, you can return an HttpResponse object to "short-circuit" the request
                return null;
            }
        });
        server.start(0);
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, ClientUtil.createSeleniumProxy(server));
        driver = new ChromeDriver(capabilities);
        driver.get("http://www.seosprint.net/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void first(){
        driver.findElement(By.id("mnu302")).click();
        driver.findElement(By.cssSelector("form.auth input[type='text']")).sendKeys("dfgd");
        driver.findElement(By.cssSelector("form.auth input[type='password']")).sendKeys("dfgd");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[src]")));
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
