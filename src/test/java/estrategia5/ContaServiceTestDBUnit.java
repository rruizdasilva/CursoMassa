package estrategia5;

import br.ce.wcaquino.dao.utils.ConnectionFactory;
import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;
import dbunit.ImportExport;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

public class ContaServiceTestDBUnit {

    ContaService service = new ContaService();
    UsuarioService userService = new UsuarioService();

    @BeforeClass
    public static void inserirConta() throws Exception {
        ImportExport.importarBanco("est5.xml");
    }

    @Test
    public void testInserir() throws Exception {
        Usuario usuario = userService.findById(1L);
        Conta conta = new Conta("Conta Salva", usuario);
        Conta contaSalva = service.salvar(conta);
        Assert.assertNotNull(contaSalva.getId());
        userService.printAll();
        service.printAll();
    }

    @Test
    public void testAlterar() throws Exception {
        Conta contaTeste = service.findByName("Conta CT005 alteracao");
        contaTeste.setNome("Conta alterada");
        service.printAll();
        Conta contaAlterada = service.salvar(contaTeste);
        Assert.assertEquals("Conta alterada", contaAlterada.getNome());
        service.printAll();
    }

    @Test
    public void testConsultar() throws Exception {
        Conta contaBuscada = service.findById(1L);
        Assert.assertEquals("Conta para testes", contaBuscada.getNome());
    }

    @Test
    public void testExcluir() throws Exception {
        Conta contaTeste = service.findByName("Conta para deletar");
        service.printAll();
        service.delete(contaTeste);
        Conta contaBuscada = service.findById(contaTeste.getId());
        Assert.assertNull(contaBuscada);
        service.printAll();
    }
}