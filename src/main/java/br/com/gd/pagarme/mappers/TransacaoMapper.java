package br.com.gd.pagarme.mappers;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.dtos.responses.SaldoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.exceptions.PagamentoException;
import br.com.gd.pagarme.exceptions.enums.PagamentoExceptionEnum;
import br.com.gd.pagarme.services.PagamentoService;
import br.com.gd.pagarme.services.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Slf4j
public class TransacaoMapper {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Autowired
    private ModelMapper modelMapper;


    private final BigDecimal TAXA_DEBITO = new BigDecimal("0.03");

    private final BigDecimal TAXA_CREDITO = new BigDecimal("0.05");


    public String esconderNumerosDoCartao (String string){
        log.info("escondendo os numeros do cartao do cliente");
        String NUM_CARTAO_CRIP = "XXXX-XXXX-XXXX-";
        return NUM_CARTAO_CRIP + string.substring(string.length() -4);
    }

    public TransacaoResponseDTO converterTransacaoEntityParaTransacaoResponseDTO(TransacaoEntity transacaoEntity) {
        log.info("convertendo TransacaoEntity : {}, para TransacaoResponseDTO", transacaoEntity);
        return modelMapper.map(transacaoEntity, TransacaoResponseDTO.class);
    }

    public TransacaoEntity converterTransacaoRequestDTOParaTransacaoEntity(TransacaoRequestDTO transacaoRequestDTO) {
        log.info("convertendo TransacaoRequestDTO : {}, para TransacaoEntity", transacaoRequestDTO);
        TransacaoEntity transacaoEntity = modelMapper.map(transacaoRequestDTO, TransacaoEntity.class);
        transacaoEntity.setPagamento(pagamentoMapper.criarPagamento(transacaoRequestDTO.getMetodoPagamento()));
        return transacaoEntity;
    }


    public BigDecimal calculoTaxaDebito (BigDecimal valorTransacao){
        log.info("calculando a taxa para transacoes no debito");
        log.info("taxa de : {}", TAXA_DEBITO);
        BigDecimal taxa = valorTransacao.multiply(TAXA_DEBITO);
        return valorTransacao.subtract(taxa);
    }

    public BigDecimal calculoTaxaCredito (BigDecimal valorTransacao){
        log.info("calculando a taxa para transacoes no credito");
        log.info("taxa de : {}", TAXA_CREDITO);
        BigDecimal taxa = valorTransacao.multiply(TAXA_CREDITO);
        return valorTransacao.subtract(taxa);
    }

}
