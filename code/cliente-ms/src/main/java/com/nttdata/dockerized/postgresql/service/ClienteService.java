package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.Cliente;

public interface ClienteService {

    Cliente guardarCliente(Long idTipoDocumento, Cliente cliente);

    Cliente traerClientePorId(Long idCliente);

    Cliente traerClientePorNumeroDocumento(String numeroDocumento);

}
