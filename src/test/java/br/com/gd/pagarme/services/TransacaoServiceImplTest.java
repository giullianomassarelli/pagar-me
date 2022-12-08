package br.com.gd.pagarme.services;

import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.repositories.TransacaoRepository;
import br.com.gd.pagarme.services.impl.TransacaoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class TransacaoServiceImplTest {

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    //CONSTANTES USADAS NO MOCK DE TRANSACAO
    private final String ID_TRANSACAO = "TESTE_ID_TRS";
    private final String DESCRICAO = "Descrição teste";
    private final BigDecimal VALOR_TRANSACAO = new BigDecimal(1000);
    private final MetodoPagamentoEnum METODO_PAGAMENTO_DEBITO = MetodoPagamentoEnum.DEBIT_CARD;
    private final MetodoPagamentoEnum METODO_PAGAMENTO_CREDITO = MetodoPagamentoEnum.CREDIT_CARD;
    private final String NUMERO_CARTAO_CORRETO= "5555555555555555";
    private final String NUMERO_CARTAO_INCORRETO= "5555555555555";
    private final String NOME_PORTADOR_CARTAO = "GIULLIANO";
    private final String DATA_VALIDADE_CARTAO = "10/22";
    private final String CVV_CARTAO = "123";

    //CONSTANTES USADAS NO MOCK DE PAGAMENTOS
    private final String ID_PAGAMENTO = "TEST_ID_PG";
    private final PagamentoEnum STATUS_PAGAMENTO_PAID = PagamentoEnum.PAID;
    private final PagamentoEnum STATUS_PAGAMENTO_WAITING_FUNDS = PagamentoEnum.WAITING_FUNDS;

    //CONSTANTES USADAS NO MOCK DE SALDO
    private final LocalDateTime DATA_HOJE = LocalDateTime.now();
    private final LocalDateTime DATA_30_DIAS = LocalDateTime.now().plusDays(30);

    @Before
    public void setupMock () {
        MockitoAnnotations.openMocks(this);
        when(transacaoRepository.save(retornaTransacaoEntity())).thenReturn(retornaTransacaoEntity());
        when(transacaoRepository.findAll()).thenReturn(retornaListaTransacaoEntity());
    }

    @Test
    public void salvarTransacaoDeveRetornarOk () throws Exception {
        assertEquals(retornaTransacaoEntity(), transacaoRepository.save(retornaTransacaoEntity()));
    }

    @Test
    public void listarTodasTransacoesDeveRetornarOk () throws  Exception {
        assertEquals(retornaListaTransacaoEntity(), transacaoRepository.findAll());
    }

    @Test
    public void deletarTransacoesDeveRetornarOk () throws  Exception {
        deletarTransacaoes();
    }


    private TransacaoEntity retornaTransacaoEntity (){
        return new TransacaoEntity(
                ID_TRANSACAO,
                DESCRICAO,
                VALOR_TRANSACAO,
                METODO_PAGAMENTO_DEBITO,
                NUMERO_CARTAO_CORRETO,
                NOME_PORTADOR_CARTAO,
                DATA_VALIDADE_CARTAO,
                CVV_CARTAO,
                retornaPagamentoEntity());
    }

    private PagamentoEntity retornaPagamentoEntity(){
        return new PagamentoEntity(
                ID_PAGAMENTO,
                STATUS_PAGAMENTO_PAID,
                DATA_HOJE);
    }

    private List<TransacaoEntity> retornaListaTransacaoEntity (){
        List<TransacaoEntity> list = new ArrayList<>();
        list.add(retornaTransacaoEntity());
        list.add(retornaTransacaoEntity());
        return list;
    }

    private void deletarTransacaoes (){
        transacaoService.deletar();
        verify(transacaoRepository, timeout(1)).deleteAll();
    }

}
