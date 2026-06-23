package com.example.ms_reservas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-mesas", url = "http://${MS_MESAS_HOST:localhost}:8081/api/mesas")
public interface MesaClient {

    @GetMapping("/{id}")
    Object obtenerMesaPorId(@PathVariable("id") Long id);
}