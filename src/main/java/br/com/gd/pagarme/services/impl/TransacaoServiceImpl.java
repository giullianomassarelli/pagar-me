package br.com.gd.pagarme.services.impl.impl;

import br.com.gd.pagarme.entities.TransacaoEntity;
import br.com.gd.pagarme.repositories.TransacaoRepository;
import br.com.gd.pagarme.services.impl.TransacaoService;
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
        log.info("Salvando uma nova transação");
        return transacaoRepository.save(transacaoEntity);
    }

    @Override
    public List<TransacaoEntity> listar() {
        log.info("Listando todas as transações");
        return transacaoRepository.findAll();
    }

    @Override
    public void deletar() {
        log.warn("Deletando todos as transações do banco de dados");
        transacaoRepository.deleteAll();
    }
}
