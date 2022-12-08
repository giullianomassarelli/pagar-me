package br.com.gd.pagarme.facades;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.dtos.responses.SaldoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.facades.impl.PagamentoFacadeImpl;
import br.com.gd.pagarme.facades.impl.TransacaoFacadeImpl;
import br.com.gd.pagarme.services.PagamentoService;
import br.com.gd.pagarme.services.TransacaoService;
import br.com.gd.pagarme.services.impl.PagamentoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class PagamentoFacadeImplTest {

    @InjectMocks
    private PagamentoFacadeImpl pagamentoFacade;

    @Mock
    private PagamentoService pagamentoService;

    @Mock
    private ModelMapper modelMapper;
    private final String ID_PAGAMENTO = "TEST_ID_PG";
    private final PagamentoEnum STATUS_PAGAMENTO_PAID = PagamentoEnum.PAID;
    private final PagamentoEnum STATUS_PAGAMENTO_WAITING_FUNDS = PagamentoEnum.WAITING_FUNDS;

    private final LocalDateTime DATA_HOJE = LocalDateTime.now();
    private final LocalDateTime DATA_30_DIAS = LocalDateTime.now().plusDays(30);

    @Before
    public void setupMock () {
        MockitoAnnotations.openMocks(this);
        when(pagamentoService.salvar(retornaPagamentoEntity())).thenReturn(retornaPagamentoEntity());
        //convert entity to DTO:
        when(modelMapper.map(retornaPagamentoEntity(), PagamentoResponseDTO.class)).thenReturn(retornaPagamentoResponseDTO());
    }

    @Test
    public void chamarCriarPagamentoDeveRetornarOk () throws Exception {
        assertEquals(retornaPagamentoResponseDTO(), pagamentoFacade.criarPagamento(MetodoPagamentoEnum.DEBIT_CARD));
    }

    private PagamentoEntity retornaPagamentoEntity(){
        return new PagamentoEntity(
                ID_PAGAMENTO,
                STATUS_PAGAMENTO_PAID,
                DATA_HOJE);
    }

    private PagamentoResponseDTO retornaPagamentoResponseDTO(){
        return new PagamentoResponseDTO(
                STATUS_PAGAMENTO_PAID,
                DATA_HOJE);
    }
  }
