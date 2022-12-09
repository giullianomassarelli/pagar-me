package br.com.gd.pagarme.facades.impl;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.SaldoResponseDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;
import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;
import br.com.gd.pagarme.enums.PagamentoEnum;
import br.com.gd.pagarme.facades.PagamentoFacade;
import br.com.gd.pagarme.facades.TransacaoFacade;
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
    private PagamentoFacade pagamentoFacade;
    @Autowired
    private ModelMapper modelMapper;
    private final BigDecimal TAXA_DEBITO = new BigDecimal("0.03");
    private final BigDecimal TAXA_CREDITO = new BigDecimal("0.05");
    @Override
    public TransacaoResponseDTO salvar(TransacaoRequestDTO transacaoRequestDTO) {

        transacaoRequestDTO.setNumeroCartao(esconderNumerosDoCartao(transacaoRequestDTO.getNumeroCartao()));

        transacaoRequestDTO.setPagamento(pagamentoFacade.criarPagamento(transacaoRequestDTO.getMetodoPagamento()));

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
        BigDecimal taxa;
        BigDecimal saldoAtualizado;

        log.info("Consultando saldo disponivel");
        for (TransacaoEntity transacaoEntity : transacaoService.listar()){
            BigDecimal valorTransacao = transacaoEntity.getValorTransacao();
                if (transacaoEntity.getPagamento().getStatus() == PagamentoEnum.PAID){
                    taxa = valorTransacao.multiply(TAXA_DEBITO);
                    saldoAtualizado = valorTransacao.subtract(taxa);
                    saldoDisponivel = saldoDisponivel.add(saldoAtualizado);
                } else {
                    taxa = valorTransacao.multiply(TAXA_CREDITO);
                    saldoAtualizado = valorTransacao.subtract(taxa);
                    saldoALiberar = saldoALiberar.add(saldoAtualizado);
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

    private String esconderNumerosDoCartao (String string){
        String NUM_CARTAO_CRIP = "XXXX-XXXX-XXXX-";
        return NUM_CARTAO_CRIP + string.substring(string.length() -4);
    }

    private TransacaoResponseDTO converterTransacaoEntityParaTransacaoResponseDTO(TransacaoEntity transacaoEntity) {
        return modelMapper.map(transacaoEntity, TransacaoResponseDTO.class);
    }

    private TransacaoEntity converterTransacaoRequestDTOParaTransacaoEntity(TransacaoRequestDTO transacaoRequestDTO) {
        return modelMapper.map(transacaoRequestDTO, TransacaoEntity.class);
    }
}
