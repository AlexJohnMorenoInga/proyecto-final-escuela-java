package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    boolean existsByNumeroDocumentoIgnoreCase(String numeroDocumento);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByCelularIgnoreCase(String celular);

    Optional<Cliente> findClienteByNumeroDocumento(String numeroDocumento);

}
