package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.CrearPrestamoDTO;
import com.nttdata.dockerized.postgresql.model.entity.Prestamo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PrestamoMapper {

    PrestamoMapper INSTANCE = Mappers.getMapper(PrestamoMapper.class);

    Prestamo toPrestamo(CrearPrestamoDTO crearPrestamoDTO);

}
