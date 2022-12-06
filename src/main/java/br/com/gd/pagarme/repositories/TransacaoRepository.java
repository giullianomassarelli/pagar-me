package br.com.gd.pagarme.repositories;

import br.com.gd.pagarme.entities.TransacaoEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransacaoRepository extends CrudRepository<TransacaoEntity, String> {
}
