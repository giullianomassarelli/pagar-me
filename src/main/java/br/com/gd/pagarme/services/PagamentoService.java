package br.com.gd.pagarme.services;

import br.com.gd.pagarme.entities.PagamentoEntity;

public interface PagamentoService {

    PagamentoEntity salvar (PagamentoEntity pagamentoEntity);
    void deletar ();
}
