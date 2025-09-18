package com.nttdata.dockerized.postgresql.mapper;

import com.nttdata.dockerized.postgresql.model.dto.TipoDocumentoDTO;
import com.nttdata.dockerized.postgresql.model.entity.TipoDocumento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TipoDocumentoMapper {

    TipoDocumentoMapper INSTANCE = Mappers.getMapper(TipoDocumentoMapper.class);

    TipoDocumento toTipoDocumento(TipoDocumentoDTO tipoDocumentoDTO);

}
