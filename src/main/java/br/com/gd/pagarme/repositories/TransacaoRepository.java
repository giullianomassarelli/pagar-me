package br.com.gd.pagarme.repositories;

import br.com.gd.pagarme.entities.TransacaoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface TransacaoRepository extends MongoRepository<TransacaoEntity, String> {
}
