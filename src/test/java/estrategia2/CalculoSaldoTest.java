package estrategia2;

import br.ce.wcaquino.dao.SaldoDAO;
import br.ce.wcaquino.dao.impl.SaldoDAOImpl;
import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.TipoTransacao;
import br.ce.wcaquino.entidades.Transacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.TransacaoService;
import br.ce.wcaquino.service.UsuarioService;
import estrategia1.UsuarioServicoTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class CalculoSaldoTest {

    // 1 usuario
    // 1 conta
    // 1 transacao

    // deve considerar transacoes do mesmo usuario
    // deve considerar transacoes da mesma conta
    // deve considerar transacoes pagas
    // deve considerar transacoes até a data corrente
        // ontem / +hoje / amanhã
    // deve somar receitas
    // deve subtrair despesas

    @Test
    public void deveCalcularSaldoCorreto() throws Exception {
        UsuarioService usuarioService = new UsuarioService();
        ContaService contaService = new ContaService();
        TransacaoService transacaoService = new TransacaoService();

        //usuários
        Usuario usuario = usuarioService.salvar((new Usuario("Usuario", "email@email.com", "123")));
        Usuario usuarioAlternativo = usuarioService.salvar((new Usuario("Usuario Alternativo qualquer", "email@qualquer.com", "123")));

        // contas
        Conta conta = contaService.salvar(new Conta("Conta principal", usuario.getId()));
        Conta contaSecundaria = contaService.salvar(new Conta("Conta Secundária", usuario.getId()));
        Conta contaUsuarioAlternativo = contaService.salvar(new Conta("Conta Usuário Alternativo", usuarioAlternativo.getId()));

        // transacoes
        // 2

        // Transacao Correta / Saldo esperado = 2
        transacaoService.salvar(new Transacao("Transacao inicial", "Envolvido", TipoTransacao.RECEITA,
                new Date(), 2d, true, conta, usuario));

        // Transacao usuario alternativo / Saldo = 2
        transacaoService.salvar(new Transacao("Transacao outro usuario", "Envolvido", TipoTransacao.RECEITA,
                new Date(), 4d, true, contaUsuarioAlternativo, usuarioAlternativo));

        // Transacao de outra conta / Saldo = 2
        transacaoService.salvar(new Transacao("Transacao outra conta", "Envolvido", TipoTransacao.RECEITA,
                new Date(), 8d, true, contaSecundaria, usuario));

        // Transacao pendente / Saldo = 2
        transacaoService.salvar(new Transacao("Transacao pendente", "Envolvido", TipoTransacao.RECEITA,
                new Date(), 16d, false, conta, usuario));

        // Transacao passada / Saldo = 34
        transacaoService.salvar(new Transacao("Transacao passada", "Envolvido", TipoTransacao.RECEITA,
                obterDataComDiferencaDias(-1), 32d, true, conta, usuario));

        // Transacao futura / Saldo = 34
        transacaoService.salvar(new Transacao("Transacao futura", "Envolvido", TipoTransacao.RECEITA,
                obterDataComDiferencaDias(1), 64d, true, conta, usuario));

        // Transacao despesa / Saldo = -94
        transacaoService.salvar(new Transacao("Transacao despesa", "Envolvido", TipoTransacao.DESPESA,
                new Date(), 128d, true, conta, usuario));

        // Testes de saldo com valor negativo dá azar / Saldo = 162
        transacaoService.salvar(new Transacao("Transacao da sorte", "Envolvido", TipoTransacao.RECEITA,
                new Date(), 256d, true, conta, usuario));

        SaldoDAO saldoDAO = new SaldoDAOImpl();
        Assert.assertEquals(new Double(162d), saldoDAO.getSaldoConta(conta.getId()));
        Assert.assertEquals(new Double(8d), saldoDAO.getSaldoConta(contaSecundaria.getId()));
        Assert.assertEquals(new Double(4d), saldoDAO.getSaldoConta(contaUsuarioAlternativo.getId()));
    }

    public Date obterDataComDiferencaDias(int dias){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }
}
