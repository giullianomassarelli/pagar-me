package br.com.gd.pagarme.services.impl.impl;

import br.com.gd.pagarme.entities.PagamentoEntity;
import br.com.gd.pagarme.repositories.PagamentoRepository;
import br.com.gd.pagarme.services.impl.PagamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Override
    public PagamentoEntity salvar(PagamentoEntity pagamentoEntity) {
        log.info("Salvando um novo pagamento");
        return pagamentoRepository.save(pagamentoEntity);
    }

    @Override
    public void deletar() {
        log.warn("Deletando todos os pagamentos do banco de dados");
        pagamentoRepository.deleteAll();
    }
}
