package br.com.gd.pagarme.facades;

import br.com.gd.pagarme.dtos.requests.TransacaoRequestDTO;
import br.com.gd.pagarme.dtos.responses.TransacaoResponseDTO;

import java.util.List;

public interface TransacaoFacade {

    TransacaoResponseDTO salvar (TransacaoRequestDTO transacaoRequestDTO);
    List<TransacaoResponseDTO> listar ();
    void deletar ();
}
