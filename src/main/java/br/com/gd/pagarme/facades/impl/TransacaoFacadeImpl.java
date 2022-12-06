package br.com.gd.pagarme.facades.impl;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.dtos.responses.SaldoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.facades.TransacaoFacade;
import br.com.gd.pagarme.services.PagamentoService;
import br.com.gd.pagarme.services.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    private final String NUM_CARTAO_CRIP = "XXXX-XXXX-XXXX-";

    @Override
    public TransacaoResponseDTO salvar(TransacaoRequestDTO transacaoRequestDTO) {
        return converterTransacaoEntityParaTransacaoResponseDTO(
                transacaoService.salvar(converterTransacaoRequestDTOParaTransacaoEntity(transacaoRequestDTO)));
    }
    @Override
    public List<TransacaoResponseDTO> listar() {
        List<TransacaoResponseDTO> transacaoResponseDTOList = new ArrayList<>();
        for (TransacaoEntity transacaoEntity : transacaoService.listar()) {
            transacaoResponseDTOList.add(converterTransacaoEntityParaTransacaoResponseDTO(transacaoEntity));
        }
        return transacaoResponseDTOList;
    }
    @Override
    public SaldoResponseDTO consultarSaldo() {
        BigDecimal saldoDisponivel = BigDecimal.ZERO;
        BigDecimal saldoALiberar = BigDecimal.ZERO;
        for (TransacaoEntity transacaoEntity : transacaoService.listar()){
            if (transacaoEntity.getMetodoPagamento() == MetodoPagamentoEnum.DEBIT_CARD){
                BigDecimal valorTransacao = transacaoEntity.getValorTransacao();
                saldoDisponivel = valorTransacao.add(saldoDisponivel);
            } else {
                BigDecimal valorTransacao = transacaoEntity.getValorTransacao();
                saldoALiberar = valorTransacao.add(saldoALiberar);
            }
        }
        SaldoResponseDTO saldoResponseDTO = new SaldoResponseDTO();
        saldoResponseDTO.setSaldoDisponivel(saldoDisponivel);
        saldoResponseDTO.setSaldoALiberar(saldoALiberar);
        saldoResponseDTO.setSaldoTotal(saldoDisponivel.add(saldoALiberar));

        return saldoResponseDTO;
    }
    @Override
    public void deletar() {
        transacaoService.deletar();
    }
    private TransacaoResponseDTO converterTransacaoEntityParaTransacaoResponseDTO(TransacaoEntity transacaoEntity) {
        TransacaoResponseDTO transacaoResponseDTO = modelMapper.map(transacaoEntity, TransacaoResponseDTO.class);
        PagamentoResponseDTO pagamentoResponseDTO = new PagamentoResponseDTO();
        pagamentoResponseDTO.setDataPagamento(transacaoEntity.getPagamento().getDataPagamento());
        pagamentoResponseDTO.setStatus(transacaoEntity.getPagamento().getStatus());
        transacaoResponseDTO.setPagamento(pagamentoResponseDTO);
        String ultimosDigitoCartao = transacaoResponseDTO.getNumeroCartao().substring(transacaoResponseDTO.getNumeroCartao().length() - 4);
        transacaoResponseDTO.setNumeroCartao(NUM_CARTAO_CRIP + ultimosDigitoCartao);
        return transacaoResponseDTO;
    }
    private TransacaoEntity converterTransacaoRequestDTOParaTransacaoEntity(TransacaoRequestDTO transacaoRequestDTO) {
        TransacaoEntity transacaoEntity = modelMapper.map(transacaoRequestDTO, TransacaoEntity.class);
        transacaoEntity.setPagamento(criarPagamento(transacaoRequestDTO));
        return transacaoEntity;
    }
    private PagamentoEntity criarPagamento(TransacaoRequestDTO transacaoRequestDTO) {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        if (transacaoRequestDTO.getMetodoPagamento() == MetodoPagamentoEnum.DEBIT_CARD) {
            pagamentoEntity.setStatus(PagamentoEnum.PAID);
            pagamentoEntity.setDataPagamento(LocalDateTime.now());
        } else {
            pagamentoEntity.setStatus(PagamentoEnum.WAITING_FUNDS);
            pagamentoEntity.setDataPagamento(LocalDateTime.now().plusDays(30));
        }
        return pagamentoService.salvar(pagamentoEntity);
    }
}
