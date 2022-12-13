package br.com.gd.pagarme.facades.impl;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.SaldoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.facades.TransacaoFacade;
import br.com.gd.pagarme.mappers.PagamentoMapper;
import br.com.gd.pagarme.mappers.TransacaoMapper;
import br.com.gd.pagarme.services.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TransacaoFacadeImpl implements TransacaoFacade {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private TransacaoMapper transacaoMapper;

    @Override
    public TransacaoResponseDTO salvar(TransacaoRequestDTO transacaoRequestDTO) {
        transacaoRequestDTO.setNumeroCartao(transacaoMapper.esconderNumerosDoCartao(transacaoRequestDTO.getNumeroCartao()));

        return transacaoMapper.converterTransacaoEntityParaTransacaoResponseDTO(
                transacaoService.salvar(transacaoMapper.converterTransacaoRequestDTOParaTransacaoEntity(transacaoRequestDTO)));
    }

    @Override
    public List<TransacaoResponseDTO> listar() {
        List<TransacaoResponseDTO> transacaoResponseDTOList = new ArrayList<>();

        for (TransacaoEntity transacaoEntity : transacaoService.listar()) {
            transacaoResponseDTOList.add(transacaoMapper.converterTransacaoEntityParaTransacaoResponseDTO(transacaoEntity));
        }

        return transacaoResponseDTOList;
    }

    @Override
    public SaldoResponseDTO consultarSaldo() {
        log.info("consultando saldo disponivel");

        BigDecimal saldoDisponivel = BigDecimal.ZERO;
        BigDecimal saldoALiberar = BigDecimal.ZERO;
        BigDecimal saldoTotal;

        for (TransacaoEntity transacaoEntity : transacaoService.listar()){
            log.info("transacao de id: {}, valor : {}", transacaoEntity.getId(), transacaoEntity.getValorTransacao());

            if (transacaoEntity.getMetodoPagamento() == MetodoPagamentoEnum.DEBIT_CARD){
                saldoDisponivel = saldoDisponivel.add(transacaoMapper.calculoTaxaDebito(transacaoEntity.getValorTransacao()));
            } else {
                saldoALiberar = saldoALiberar.add(transacaoMapper.calculoTaxaCredito(transacaoEntity.getValorTransacao()));
            }

            saldoTotal = saldoDisponivel.add(saldoALiberar);
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

}
