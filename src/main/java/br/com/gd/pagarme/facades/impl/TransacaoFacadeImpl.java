package br.com.gd.pagarme.facades.impl;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.facades.TransacaoFacade;
import br.com.gd.pagarme.services.TransacaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransacaoFacadeImpl implements TransacaoFacade {

    @Autowired
    private TransacaoService transacaoService;

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
        return modelMapper.map(transacaoEntity, TransacaoResponseDTO.class);
    }

    private TransacaoEntity converterTransacaoRequestDTOParaTransacaoEntity (TransacaoRequestDTO transacaoRequestDTO){
        return modelMapper.map(transacaoRequestDTO, TransacaoEntity.class);
    }
}
