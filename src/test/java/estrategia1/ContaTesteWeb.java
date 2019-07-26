package estrategia1;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContaTesteWeb {
    WebDriver driver;

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
    public void teste1_inserir(){
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Adicionar")).click();
        driver.findElement(By.id("nome")).sendKeys("Conta estrategia #1");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta adicionada com sucesso!", msg);
    }

    @Test
    public void teste2_consultar(){
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'Conta estrategia #1')]/..//a")).click();
        String nomeConta = driver.findElement(By.id("nome")).getAttribute("value");
        Assert.assertEquals("Conta estrategia #1", nomeConta);
    }

    @Test
    public void teste3_alterar(){
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'Conta estrategia #1')]/..//a")).click();
        driver.findElement(By.id("nome")).clear();
        driver.findElement(By.id("nome")).sendKeys("Conta estrategia #1 alterada");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta alterada com sucesso!", msg);
    }

    @Test
    public void teste4_excluir(){
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Listar")).click();
        driver.findElement(By.xpath("//td[contains(text(),'Conta estrategia #1')]/..//a[2]")).click();
        String msg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta removida com sucesso!", msg);
    }

    @After
    public void fechar(){
        driver.quit();
    }
}
