package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    boolean existsByNumeroDocumentoIgnoreCase(String numeroDocumento);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByCelularIgnoreCase(String celular);

}
