package com.example.ms_reservas.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// La URL se inyecta directamente desde el docker-compose usando la variable MS_MESAS_HOST
@FeignClient(name = "ms-mesas", url = "${MS_MESAS_HOST}")
public interface MesaClient {

    @GetMapping("/api/mesas/{id}")
    Object obtenerMesaPorId(@PathVariable("id") Long id);
}