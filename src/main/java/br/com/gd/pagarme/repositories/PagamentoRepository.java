package br.com.gd.pagarme.repositories;

import br.com.gd.pagarme.entities.PagamentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PagamentoRepository extends MongoRepository<PagamentoEntity, String> {
}
