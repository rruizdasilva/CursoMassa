package estrategia4;

import br.ce.wcaquino.dao.SaldoDAO;
import br.ce.wcaquino.dao.impl.SaldoDAOImpl;
import br.ce.wcaquino.entidades.Conta;
import br.ce.wcaquino.entidades.TipoTransacao;
import br.ce.wcaquino.entidades.Transacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.service.ContaService;
import br.ce.wcaquino.service.TransacaoService;
import br.ce.wcaquino.service.UsuarioService;
import dbunit.ImportExport;
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
        ImportExport.importarBanco("saldo.xml");
        SaldoDAO saldoDAO = new SaldoDAOImpl();
        Assert.assertEquals(new Double(162d), saldoDAO.getSaldoConta(1L));
        Assert.assertEquals(new Double(8d), saldoDAO.getSaldoConta(2L));
        Assert.assertEquals(new Double(4d), saldoDAO.getSaldoConta(3L));
    }

    public Date obterDataComDiferencaDias(int dias){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }
}
