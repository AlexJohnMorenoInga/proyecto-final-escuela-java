package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.ClienteDTO;
import com.nttdata.dockerized.postgresql.model.dto.GuardarClienteRequestDTO;
import com.nttdata.dockerized.postgresql.model.dto.GuardarClienteResponseDTO;
import com.nttdata.dockerized.postgresql.model.entity.Cliente;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toClienteEntity(GuardarClienteRequestDTO guardarClienteRequestDTO);

    GuardarClienteResponseDTO toGuardarClienteResponseDTO(Cliente cliente);

    ClienteDTO toClienteDTO(Cliente cliente);

}
