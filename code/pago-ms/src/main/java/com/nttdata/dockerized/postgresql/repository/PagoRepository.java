package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.model.entity.Pago;
import org.springframework.data.repository.CrudRepository;

public interface PagoRepository extends CrudRepository<Pago, Long> {
}
