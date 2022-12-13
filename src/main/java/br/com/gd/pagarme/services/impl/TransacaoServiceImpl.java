package br.com.gd.pagarme.services.impl;

import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.repositories.TransacaoRepository;
import br.com.gd.pagarme.services.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class TransacaoServiceImpl implements TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    public TransacaoEntity salvar(TransacaoEntity transacaoEntity) {
        log.info("salvando uma nova transacao : {}", transacaoEntity);
        return transacaoRepository.save(transacaoEntity);
    }

    @Override
    public List<TransacaoEntity> listar() {
        log.info("listando todas as transacoes");
        return transacaoRepository.findAll();
    }

    @Override
    public void deletar() {
        log.warn("deletando todos as transacoes do banco de dados");
        transacaoRepository.deleteAll();
    }
}
