import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class InicioSelenium {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\Selenium\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://seubarriga.wcaquino.me/login");
        driver.findElement(By.id("email")).sendKeys("roger@roger.com");
        driver.findElement(By.id("senha")).sendKeys("12345");
        driver.findElement(By.tagName("button")).click();
        driver.quit();
    }
}
