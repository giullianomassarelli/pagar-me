package br.com.gd.pagarme.controllers;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.dtos.responses.SaldoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.facades.TransacaoFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TransacaoController.class)
@AutoConfigureMockMvc
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransacaoFacade transacaoFacade;

    //CONSTANTES USADAS NAS ROTAS DA CONTROLLER
    private final String ROTA_TRANSACAO = "/api/v1/transacoes";
    private final String ROTA_TRANSACAO_ERRADA = "/api/v1/transacoess";
    private final String ROTA_TRANSACAO_CONSULTA_SALDO = "/api/v1/transacoes/saldo";
    private final String ROTA_TRANSACAO_CONSULTA_SALDO_ERRADA = "/api/v1/transacoes/saldoo";

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

    private final BigDecimal SALDO_TOTAL = new BigDecimal(1500);
    private final BigDecimal SALDO_DISPONIVEL = new BigDecimal(1000);
    private final BigDecimal SALDO_A_LIBERAR = new BigDecimal(500);

    //CONSTATNES DE DATAS PARA O PAGAMENTO
    private final LocalDateTime DATA_HOJE = LocalDateTime.now();
    private final LocalDateTime DATA_30_DIAS = LocalDateTime.now().plusDays(30);

    @Before
    public void setMock(){
        MockitoAnnotations.openMocks(this);
        when(transacaoFacade.salvar(retornaTransacaoRequestDTO())).thenReturn(retornaTransacaoResponseDTO());
        when(transacaoFacade.listar()).thenReturn(retornaListaDeTransacaoResponseDTO());
        when(transacaoFacade.consultarSaldo()).thenReturn(retornaSaldoResponseDTO());
    }

    @Test
    public void chamarCadastrarTransacaoDeveRetornarOk () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post(ROTA_TRANSACAO).contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(retornaTransacaoRequestDTO())))
                        .andExpect(status().isCreated())
                        .andExpect(content().json(ow.writeValueAsString(retornaTransacaoResponseDTO())));
    }

    @Test
    public void chamarCadastrarTransacaoPassandoNumCartaoInvalidoDeveRetornarBadRequest () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post(ROTA_TRANSACAO).contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(retornaBadRequestTransacaoRequestDTO())))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void chamarCadastrarTransacaoPassandoRotaErradaDeveRetornarNotFound () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(post(ROTA_TRANSACAO_ERRADA))
                        .andExpect(status().isNotFound());
    }

    @Test
    public void chamarListarTransacoesDeveRetornarOk () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(get(ROTA_TRANSACAO).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(ow.writeValueAsString(retornaListaDeTransacaoResponseDTO())));
    }

    @Test
    public void chamarListarTransacoesPassandoRotaErradaDeveRetornarNotFound () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(get(ROTA_TRANSACAO_ERRADA))
                        .andExpect(status().isNotFound());
    }

    @Test
    public void chamarConsultarSaldoDeveRetornarAceito () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(get(ROTA_TRANSACAO_CONSULTA_SALDO).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isAccepted())
                        .andExpect(content().json(ow.writeValueAsString(retornaSaldoResponseDTO())));
    }

    @Test
    public void chamarConsultarSaldoPassandoRotaErradaDeveRetornarNotFound () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(get(ROTA_TRANSACAO_CONSULTA_SALDO_ERRADA))
                        .andExpect(status().isNotFound());
    }

    @Test
    public void chamarDeletarTransacoesDeveRetornarNoContent () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(delete(ROTA_TRANSACAO))
                        .andExpect(status().isNoContent());
    }

    @Test
    public void chamarDeletarTransacoesPassandoRotaErradaDeveRetornarNotFound () throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(delete(ROTA_TRANSACAO_ERRADA))
                .andExpect(status().isNotFound());
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
                null);
    }

    private TransacaoRequestDTO retornaBadRequestTransacaoRequestDTO () {
        return new TransacaoRequestDTO(
                DESCRICAO,
                VALOR_TRANSACAO,
                METODO_PAGAMENTO_DEBITO,
                NUMERO_CARTAO_INCORRETO,
                NOME_PORTADOR_CARTAO,
                DATA_VALIDADE_CARTAO,
                CVV_CARTAO,
                retornaPagamentoResponseDTO());
    }

    private List<TransacaoResponseDTO> retornaListaDeTransacaoResponseDTO (){
        List<TransacaoResponseDTO> list = new ArrayList<>();
        list.add(retornaTransacaoResponseDTO());
        list.add(retornaTransacaoResponseDTO());
        return list;
    }

    private SaldoResponseDTO retornaSaldoResponseDTO() {
        return new SaldoResponseDTO(
                SALDO_TOTAL,
                SALDO_DISPONIVEL,
                SALDO_A_LIBERAR);
    }


}
