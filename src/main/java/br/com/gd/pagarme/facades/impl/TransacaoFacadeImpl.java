package br.com.gd.pagarme.facades.impl;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.facades.TransacaoFacade;
import br.com.gd.pagarme.services.PagamentoService;
import br.com.gd.pagarme.services.TransacaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransacaoFacadeImpl implements TransacaoFacade {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public TransacaoResponseDTO salvar(TransacaoRequestDTO transacaoRequestDTO) {
        return converterTransacaoEntityParaTransacaoResponseDTO(
                transacaoService.salvar(converterTransacaoRequestDTOParaTransacaoEntity(transacaoRequestDTO)));
    }

    @Override
    public List<TransacaoResponseDTO> listar() {
        List<TransacaoResponseDTO> transacaoResponseDTOList = new ArrayList<>();
        for (TransacaoEntity transacaoEntity : transacaoService.listar()){
            transacaoResponseDTOList.add(converterTransacaoEntityParaTransacaoResponseDTO(transacaoEntity));
        }
        return transacaoResponseDTOList;
    }

    @Override
    public void deletar() {
        transacaoService.deletar();
    }

    private TransacaoResponseDTO converterTransacaoEntityParaTransacaoResponseDTO (TransacaoEntity transacaoEntity){
        TransacaoResponseDTO transacaoResponseDTO = modelMapper.map(transacaoEntity, TransacaoResponseDTO.class);
        PagamentoResponseDTO pagamentoResponseDTO = new PagamentoResponseDTO();
        pagamentoResponseDTO.setDataPagamento(transacaoEntity.getPagamento().getDataPagamento());
        pagamentoResponseDTO.setStatus(transacaoEntity.getPagamento().getStatus());
        transacaoResponseDTO.setPagamento(pagamentoResponseDTO);
        return transacaoResponseDTO;
    }

    private TransacaoEntity converterTransacaoRequestDTOParaTransacaoEntity (TransacaoRequestDTO transacaoRequestDTO){
        TransacaoEntity transacaoEntity = modelMapper.map(transacaoRequestDTO, TransacaoEntity.class);
        transacaoEntity.setPagamento(criarPagamento(transacaoRequestDTO));
        return transacaoEntity;
    }

    private PagamentoEntity criarPagamento (TransacaoRequestDTO transacaoRequestDTO){
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
            if(transacaoRequestDTO.getMetodoPagamento() == MetodoPagamentoEnum.DEBIT_CARD){
                pagamentoEntity.setStatus(PagamentoEnum.PAID);
                pagamentoEntity.setDataPagamento(LocalDateTime.now());
            } else {
                pagamentoEntity.setStatus(PagamentoEnum.WAITING_FUNDS);
                pagamentoEntity.setDataPagamento(LocalDateTime.now().plusDays(30));
            }
        return pagamentoService.salvar(pagamentoEntity);
    }

    private PagamentoResponseDTO converterPagamentoEntityParaPagamentoResponseDTO (PagamentoEntity pagamentoEntity){
        return modelMapper.map(pagamentoEntity, PagamentoResponseDTO.class);
    }
}
