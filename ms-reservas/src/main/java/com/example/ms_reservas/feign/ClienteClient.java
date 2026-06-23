package com.example.ms_reservas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes", url = "http://${MS_CLIENTES_HOST:localhost}:8082/api/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    Object obtenerClientePorId(@PathVariable("id") Long id);
}