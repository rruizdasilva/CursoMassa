package estrategia4;

import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.UsuarioService;
import dbunit.ImportExport;
import org.junit.Assert;
import org.junit.Test;

public class ContaServiceTestDBUnit {

    ContaService service = new ContaService();
    UsuarioService userService = new UsuarioService();

    @Test
    public void testInserir() throws Exception {
        ImportExport.importarBanco("est4_inserirConta.xml");
        Usuario usuario = userService.findById(1L);
        Conta conta = new Conta("Conta Salva", usuario);
        Conta contaSalva = service.salvar(conta);
        Assert.assertNotNull(contaSalva.getId());
    }

    @Test
    public void testInserir_Assertion() throws Exception {
        ImportExport.importarBanco("est4_inserirConta.xml");
        Usuario usuario = userService.findById(1L);
        Conta conta = new Conta("Conta Salva", usuario);
        Conta contaSalva = service.salvar(conta);

        // estado atual do banco

        // estado esperado (XML)

        // Comparar os dois estados
    }

    @Test
    public void testAlterar() throws Exception {
        ImportExport.importarBanco("est4_umaConta.xml");
        Conta contaTeste = service.findByName("Conta para testes");
        contaTeste.setNome("Conta alterada");
        service.printAll();
        Conta contaAlterada = service.salvar(contaTeste);
        Assert.assertEquals("Conta alterada", contaAlterada.getNome());
        service.printAll();
    }

    @Test
    public void testConsultar() throws Exception {
        ImportExport.importarBanco("est4_umaConta.xml");
        Conta contaBuscada = service.findById(1L);
        Assert.assertEquals("Conta para testes", contaBuscada.getNome());
    }

    @Test
    public void testExcluir() throws Exception {
        ImportExport.importarBanco("est4_umaConta.xml");
        Conta contaTeste = service.findByName("Conta para testes");
        service.printAll();
        service.delete(contaTeste);
        Conta contaBuscada = service.findById(contaTeste.getId());
        Assert.assertNull(contaBuscada);
        service.printAll();
    }
}
