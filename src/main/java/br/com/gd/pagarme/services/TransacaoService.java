package br.com.gd.pagarme.services.impl;

import br.com.gd.pagarme.entities.TransacaoEntity;

import java.util.List;

public interface TransacaoService {

    TransacaoEntity salvar (TransacaoEntity transacaoEntity);
    List<TransacaoEntity> listar ();
    void deletar ();
}
