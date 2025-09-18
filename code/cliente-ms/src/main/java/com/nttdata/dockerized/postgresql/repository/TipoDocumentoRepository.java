package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.model.entity.TipoDocumento;
import org.springframework.data.repository.CrudRepository;

public interface TipoDocumentoRepository extends CrudRepository<TipoDocumento, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

}
