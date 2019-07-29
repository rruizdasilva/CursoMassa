package estrategia2;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ContaTesteWeb {
    private WebDriver driver;
    private Faker faker = new Faker();

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
    public void inserir(){
        inserirConta();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta adicionada com sucesso!", msg);
    }

    @Test
    public void consultar(){
        String conta = inserirConta();
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'"+conta+"')]/..//a")).click();
        String nomeConta = driver.findElement(By.id("nome")).getAttribute("value");
        Assert.assertEquals(conta, nomeConta);
    }

    @Test
    public void alterar(){
        String conta = inserirConta();
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'"+conta+"')]/..//a")).click();
        driver.findElement(By.id("nome")).sendKeys(" Alterada");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta alterada com sucesso!", msg);
    }

    @Test
    public void excluir(){
        String conta = inserirConta();
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'"+conta+"')]/..//a[2]")).click();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta removida com sucesso!", msg);
    }

    private String inserirConta() {
        String registro = faker.harryPotter().character();
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Adicionar")).click();
        driver.findElement(By.id("nome")).sendKeys(registro);
        driver.findElement(By.tagName("button")).click();
        return registro;
    }

    @After
    public void fechar(){
        driver.quit();
    }
}
