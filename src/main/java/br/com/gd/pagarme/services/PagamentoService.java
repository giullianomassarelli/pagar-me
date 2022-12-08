package br.com.gd.pagarme.services.impl;

import br.com.gd.pagarme.entities.PagamentoEntity;

public interface PagamentoService {

    PagamentoEntity salvar (PagamentoEntity pagamentoEntity);
    void deletar ();
}
