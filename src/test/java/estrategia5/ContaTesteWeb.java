package estrategia5;

import com.github.javafaker.Faker;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ContaTesteWeb {
    private WebDriver driver;

    @BeforeClass
    public static void reset(){
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\Selenium\\chromedriver.exe");
        WebDriver driverLogin = new ChromeDriver();
        driverLogin.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driverLogin.manage().window().maximize();
        driverLogin.get("http://seubarriga.wcaquino.me/login");
        driverLogin.findElement(By.id("email")).sendKeys("roger@roger.com");
        driverLogin.findElement(By.id("senha")).sendKeys("12345");
        driverLogin.findElement(By.tagName("button")).click();
        driverLogin.findElement(By.linkText("reset")).click();
        driverLogin.quit();
    }

    @Before
    public void login() {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://seubarriga.wcaquino.me/login");
        driver.findElement(By.id("email")).sendKeys("roger@roger.com");
        driver.findElement(By.id("senha")).sendKeys("12345");
        driver.findElement(By.tagName("button")).click();
    }

    @Test
    public void inserir() throws SQLException, ClassNotFoundException {
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Adicionar")).click();
        driver.findElement(By.id("nome")).sendKeys("Conta estrategia #5");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta adicionada com sucesso!", msg);
    }

    @Test
    public void consultar() throws SQLException, ClassNotFoundException {
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'Conta para saldo')]/..//a")).click();
        String nomeConta = driver.findElement(By.id("nome")).getAttribute("value");
        Assert.assertEquals("Conta para saldo", nomeConta);
    }

    @Test
    public void alterar() throws SQLException, ClassNotFoundException {
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'Conta para alterar')]/..//a")).click();
        driver.findElement(By.id("nome")).sendKeys(" Alterada");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta alterada com sucesso!", msg);
    }

    @Test
    public void excluir() throws SQLException, ClassNotFoundException {
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'Conta mesmo nome')]/..//a[2]")).click();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta removida com sucesso!", msg);
    }

    @After
    public void fechar(){
        driver.quit();
    }
}