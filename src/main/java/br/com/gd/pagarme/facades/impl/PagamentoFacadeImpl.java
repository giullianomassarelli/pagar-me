package br.com.gd.pagarme.facades.impl;

import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.exceptions.PagamentoException;
import br.com.gd.pagarme.exceptions.enums.PagamentoExceptionEnum;
import br.com.gd.pagarme.facades.PagamentoFacade;
import br.com.gd.pagarme.services.PagamentoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class PagamentoFacadeImpl implements PagamentoFacade {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PagamentoResponseDTO criarPagamento(MetodoPagamentoEnum metodoPagamento) {
        return convertPagamentoEntityParaPagamentoResponseDTO(
                pagamentoService.salvar(criarPlanoDePagamento(metodoPagamento)));
    }

    private PagamentoEntity criarPlanoDePagamento (MetodoPagamentoEnum metodoPagamento){

        verificaMetodoPagamento(metodoPagamento);

        PagamentoEntity pagamentoEntity = new PagamentoEntity();

        if (metodoPagamento == MetodoPagamentoEnum.DEBIT_CARD) {
            pagamentoEntity.setStatus(PagamentoEnum.PAID);
            pagamentoEntity.setDataPagamento(LocalDateTime.now());
        } else {
            pagamentoEntity.setStatus(PagamentoEnum.WAITING_FUNDS);
            pagamentoEntity.setDataPagamento(LocalDateTime.now().plusDays(30));
        }

        return pagamentoEntity;
    }

    private void verificaMetodoPagamento (MetodoPagamentoEnum metodoPagamento){
        if (metodoPagamento == null)
            throw new PagamentoException(PagamentoExceptionEnum.PAGAMENTO_INVALIDO);
    }

    private PagamentoResponseDTO convertPagamentoEntityParaPagamentoResponseDTO(PagamentoEntity pagamentoEntity) {
        return modelMapper.map(pagamentoEntity, PagamentoResponseDTO.class);
    }
}
