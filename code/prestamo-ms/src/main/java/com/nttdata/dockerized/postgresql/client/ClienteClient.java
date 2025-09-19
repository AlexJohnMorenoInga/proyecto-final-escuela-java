package com.nttdata.dockerized.postgresql.client;

import com.nttdata.dockerized.postgresql.model.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-ms")
public interface ClienteClient {

    @GetMapping("/api/clientes/numero_documento/{numeroDocumento}")
    ClienteDTO traerClientePorNumeroDocumento(@PathVariable(value = "numeroDocumento") String numeroDocumento);

}
