package estrategia1;

import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.UsuarioService;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioServicoTest {

    private UsuarioService servico = new UsuarioService();
    private static Usuario usuarioGlobal;

    @Before
    public void login() {
    }

    @Test
    public void teste1_inserir() throws Exception {
        Usuario usuario = new Usuario("Usuario estrategia #1", "user@email.com", "1q2w3e4r");
        usuarioGlobal = servico.salvar(usuario);
        Assert.assertNotNull(usuarioGlobal.getId());
    }

    @Test
    public void teste2_consultar() throws Exception {
        Usuario usuario = servico.findById(usuarioGlobal.getId());
        Assert.assertEquals("Usuario estrategia #1", usuario.getNome());

    }

    @Test
    public void teste3_alterar() throws Exception {
        Usuario usuario = servico.findById(usuarioGlobal.getId());
        usuario.setNome("Usuario estrategia #1 alterado");
        Usuario usuarioAlterado = servico.salvar(usuario);
        Assert.assertEquals("Usuario estrategia #1 alterado", usuarioAlterado.getNome());
    }

    @Test
    public void teste4_excluir() throws Exception {
        servico.delete(usuarioGlobal);
        Usuario usuarioRemovido = servico.findById(usuarioGlobal.getId());
        Assert.assertNull(usuarioRemovido);
    }

    @After
    public void fechar(){
    }
}
