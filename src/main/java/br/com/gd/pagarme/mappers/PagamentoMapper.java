package br.com.gd.pagarme.mappers;

import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.exceptions.PagamentoException;
import br.com.gd.pagarme.exceptions.enums.PagamentoExceptionEnum;
import br.com.gd.pagarme.services.PagamentoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class PagamentoMapper {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private ModelMapper modelMapper;


    public PagamentoEntity criarPagamento(MetodoPagamentoEnum metodoPagamento) {
        log.info("criando novo pagamento");
        return pagamentoService.salvar(criarPlanoDePagamento(metodoPagamento));
    }

    private PagamentoEntity criarPlanoDePagamento (MetodoPagamentoEnum metodoPagamento){
        log.info("criando plano de pagamento para o pagamento do tipo: {}", metodoPagamento);
        verificaMetodoPagamento(metodoPagamento);
                return verificarTipoPagamento(metodoPagamento);
    }

    private PagamentoEntity verificarTipoPagamento (MetodoPagamentoEnum metodoPagamento){
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        log.info("verificando o tipo do pagamento");
        if(metodoPagamento == MetodoPagamentoEnum.DEBIT_CARD) {
            pagamentoEntity.setStatus(PagamentoEnum.PAID);
            pagamentoEntity.setDataPagamento(LocalDateTime.now());

        } else {
            pagamentoEntity.setStatus(PagamentoEnum.WAITING_FUNDS);
            pagamentoEntity.setDataPagamento(LocalDateTime.now().plusDays(30));
        }

        return pagamentoEntity;
    }

    private void verificaMetodoPagamento (MetodoPagamentoEnum metodoPagamento){
        log.info("verificando se o metodo de pagamento e valido");
        if (metodoPagamento == null)
            throw new PagamentoException(PagamentoExceptionEnum.PAGAMENTO_INVALIDO);
    }
}
