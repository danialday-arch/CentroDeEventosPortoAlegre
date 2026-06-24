package com.example.ms_reservas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// La URL se inyecta directamente desde el docker-compose usando la variable MS_CLIENTES_HOST
@FeignClient(name = "ms-clientes", url = "${MS_CLIENTES_HOST}")
public interface ClienteClient {

    @GetMapping("/api/clientes/{id}")
    Object obtenerClientePorId(@PathVariable("id") Long id);
}