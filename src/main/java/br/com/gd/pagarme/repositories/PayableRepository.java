package br.com.gd.pagarme.repositories;

import br.com.gd.pagarme.entities.PayableEntity;
import org.springframework.data.repository.CrudRepository;

public interface PayableRepository extends CrudRepository<PayableEntity, String> {
}
