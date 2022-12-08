package br.com.gd.pagarme.facades;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.dtos.responses.SaldoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.facades.impl.TransacaoFacadeImpl;
import br.com.gd.pagarme.services.TransacaoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class TransacaoFacadeImplTest {

    @InjectMocks
    private TransacaoFacadeImpl transacaoFacade;

    @Mock
    private TransacaoService transacaoService;

    @Mock
    private PagamentoFacade pagamentoFacade;

    @Mock
    private ModelMapper modelMapper;

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
    private final LocalDateTime DATA_HOJE = LocalDateTime.now();
    private final LocalDateTime DATA_30_DIAS = LocalDateTime.now().plusDays(30);

    private final BigDecimal SALDO_TOTAL = new BigDecimal(1500);
    private final BigDecimal SALDO_DISPONIVEL = new BigDecimal(1000);
    private final BigDecimal SALDO_A_LIBERAR = new BigDecimal(500);

    @Before
    public void setupMock () {
        MockitoAnnotations.openMocks(this);
        when(transacaoService.salvar(retornaTransacaoEntity())).thenReturn(retornaTransacaoEntity());
        when(transacaoService.listar()).thenReturn(retornaListaTransacaoEntity());


        //convert entity to DTO:
        when(modelMapper.map(retornaTransacaoEntity(), TransacaoResponseDTO.class)).thenReturn(retornaTransacaoResponseDTO());
        //when(modelMapper.map(retornaPagamentoEntity(), PagamentoResponseDTO.class)).thenReturn(retornaPagamentoResponseDTO());
        //convert DTO to entity:
        when(modelMapper.map(retornaTransacaoRequestDTO(), TransacaoEntity.class)).thenReturn(retornaTransacaoEntity());
    }

    @Test
    public void chamarSalvarDeveRetornarOk () throws Exception {
        assertEquals(retornaTransacaoResponseDTO(), transacaoFacade.salvar(retornaTransacaoRequestDTO()));
    }

    @Test
    public void chamarListarDeveRetornarOk () throws Exception {
        assertEquals(retornaListaTransacaoResponseDTO(), transacaoFacade.listar());
    }

    @Test
    public void chamarConsultarSaldoDeveRetornarOk () throws Exception {
        assertEquals(retornaSaldoResponseDTO(), transacaoFacade.consultarSaldo());
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

    private TransacaoResponseDTO retornaTransacaoResponseDTO (){
        return new TransacaoResponseDTO(
                DESCRICAO,
                VALOR_TRANSACAO,
                METODO_PAGAMENTO_DEBITO,
                NUMERO_CARTAO_CORRETO,
                NOME_PORTADOR_CARTAO,
                DATA_VALIDADE_CARTAO,
                CVV_CARTAO,
                retornaPagamentoResponseDTO());
    }
    private PagamentoResponseDTO retornaPagamentoResponseDTO(){
        return new PagamentoResponseDTO(
                STATUS_PAGAMENTO_PAID,
                DATA_HOJE);
    }
    private TransacaoRequestDTO retornaTransacaoRequestDTO(){
        return new TransacaoRequestDTO(
                DESCRICAO,
                VALOR_TRANSACAO,
                METODO_PAGAMENTO_DEBITO,
                NUMERO_CARTAO_CORRETO,
                NOME_PORTADOR_CARTAO,
                DATA_VALIDADE_CARTAO,
                CVV_CARTAO,
                retornaPagamentoResponseDTO());
    }

    private List<TransacaoEntity> retornaListaTransacaoEntity (){
        List<TransacaoEntity> list = new ArrayList<>();
        list.add(retornaTransacaoEntity());
        list.add(retornaTransacaoEntity());
        return list;
    }

    private List<TransacaoResponseDTO> retornaListaTransacaoResponseDTO (){
        List<TransacaoResponseDTO> list = new ArrayList<>();
        list.add(retornaTransacaoResponseDTO());
        list.add(retornaTransacaoResponseDTO());
        return list;
    }

    private SaldoResponseDTO retornaSaldoResponseDTO () {
        return new SaldoResponseDTO(
                SALDO_TOTAL,
                SALDO_DISPONIVEL,
                SALDO_A_LIBERAR);
    }
}
