package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.GuardarClienteRequestDTO;
import com.nttdata.dockerized.postgresql.model.dto.GuardarClienteResponseDTO;
import com.nttdata.dockerized.postgresql.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toClienteEntity(GuardarClienteRequestDTO guardarClienteRequestDTO);

    GuardarClienteResponseDTO toGuardarClienteResponseDTO(Cliente cliente);

}
