package com.example.ms_pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-empleados", url = "http://${MS_EMPLEADOS_HOST:localhost}:8086/api/empleados")
public interface EmpleadoClient {

    @GetMapping("/{id}")
    Object obtenerEmpleadoPorId(@PathVariable("id") Long id);
}