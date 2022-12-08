package br.com.gd.pagarme.facades;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.PagamentoResponseDTO;
import br.com.gd.pagarme.enums.MetodoPagamentoEnum;

public interface PagamentoFacade {

    PagamentoResponseDTO criarPagamento (MetodoPagamentoEnum metodoPagamento);
}
