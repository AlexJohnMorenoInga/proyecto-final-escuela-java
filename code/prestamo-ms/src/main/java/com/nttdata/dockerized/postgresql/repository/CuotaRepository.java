package com.nttdata.dockerized.postgresql.repository;

import com.nttdata.dockerized.postgresql.model.entity.Cuota;
import org.springframework.data.repository.CrudRepository;

public interface CuotaRepository extends CrudRepository<Cuota, Long> {

    Iterable<Cuota> findByPrestamoIdPrestamo(Long idPrestamo);

}
