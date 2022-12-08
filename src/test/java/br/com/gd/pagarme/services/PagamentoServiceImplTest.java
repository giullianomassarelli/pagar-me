package br.com.gd.pagarme.services;

import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.repositories.PagamentoRepository;
import br.com.gd.pagarme.repositories.TransacaoRepository;
import br.com.gd.pagarme.services.impl.PagamentoServiceImpl;
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
public class PagamentoServiceImplTest {

    @InjectMocks
    private PagamentoServiceImpl pagamentoService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    private final String ID_PAGAMENTO = "TEST_ID_PG";
    private final PagamentoEnum STATUS_PAGAMENTO_PAID = PagamentoEnum.PAID;
    private final PagamentoEnum STATUS_PAGAMENTO_WAITING_FUNDS = PagamentoEnum.WAITING_FUNDS;

    private final LocalDateTime DATA_HOJE = LocalDateTime.now();
    private final LocalDateTime DATA_30_DIAS = LocalDateTime.now().plusDays(30);

    @Before
    public void setupMock () {
        MockitoAnnotations.openMocks(this);
        when(pagamentoRepository.save(retornaPagamentoEntityComStatusPaid())).thenReturn(retornaPagamentoEntityComStatusPaid());
        when(pagamentoRepository.save(retornaPagamentoEntityComStatusWaitingFunds())).thenReturn(retornaPagamentoEntityComStatusWaitingFunds());
    }

    @Test
    public void salvarPagamentoComStatusPaidDeveRetornarOk () throws Exception {
        assertEquals(retornaPagamentoEntityComStatusPaid(), pagamentoRepository.save(retornaPagamentoEntityComStatusPaid()));
    }

    @Test
    public void salvarPagamentoComStatusWaitingFundsDeveRetornarOk () throws Exception {
        assertEquals(retornaPagamentoEntityComStatusWaitingFunds(), pagamentoRepository.save(retornaPagamentoEntityComStatusWaitingFunds()));
    }

    @Test
    public void deletarPagamentosDeveRetornarOk () throws  Exception {
        deletarPagamentos();
    }


    private PagamentoEntity retornaPagamentoEntityComStatusPaid(){
        return new PagamentoEntity(
                ID_PAGAMENTO,
                STATUS_PAGAMENTO_PAID,
                DATA_HOJE);
    }

    private PagamentoEntity retornaPagamentoEntityComStatusWaitingFunds(){
        return new PagamentoEntity(
                ID_PAGAMENTO,
                STATUS_PAGAMENTO_WAITING_FUNDS,
                DATA_30_DIAS);
    }

    private void deletarPagamentos (){
        pagamentoService.deletar();
        verify(pagamentoRepository, timeout(1)).deleteAll();
    }

}
