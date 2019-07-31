package estrategia3;

import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class GeradorMassas {

    private WebDriver driver;
    public static final String CHAVE_CONTA_SB = "CONTA_SB";
    public static final String CHAVE_CONTA = "CONTA";

    public void gerarContaSeuBarriga() throws SQLException, ClassNotFoundException {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://seubarriga.wcaquino.me/login");

        driver.findElement(By.id("email")).sendKeys("roger@roger.com");
        driver.findElement(By.id("senha")).sendKeys("12345");
        driver.findElement(By.tagName("button")).click();

        Faker faker = new Faker();
        String registro = faker.gameOfThrones().character() + " " + faker.gameOfThrones().dragon();
        driver.findElement(By.linkText("Contas")).click();
        driver.findElement(By.linkText("Adicionar")).click();
        driver.findElement(By.id("nome")).sendKeys(registro);
        driver.findElement(By.tagName("button")).click();
        driver.quit();

        new MassaDAOImpl().inserirMassa(CHAVE_CONTA_SB, registro);
    }

    public void gerarConta() throws Exception {
        Faker faker = new Faker();
        ContaService service = new ContaService();
        UsuarioService userService = new UsuarioService();
        Usuario usuarioGlobal = new Usuario(faker.name().fullName() + "US", faker.internet().emailAddress(), faker.internet().password());
        Usuario usuarioSalvo = userService.salvar(usuarioGlobal);
        Conta conta = new Conta(faker.superhero().name(), usuarioSalvo);
        service.salvar(conta);
        new MassaDAOImpl().inserirMassa(CHAVE_CONTA, conta.getNome());
    }

    public static void main(String[] args) throws Exception {
        GeradorMassas gerador = new GeradorMassas();
        for(int i = 0; i < 10 ; i++){
            gerador.gerarConta();
        }

       /* for(int i = 0; i < 5 ; i++){
            gerador.gerarContaSeuBarriga();
        }
        String massa = new MassaDAOImpl().obterMassa(CHAVE_CONTA_SB);
        System.out.println(massa);*/
    }
}
