package com.example.ms_pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-inventario", url = "http://${MS_INVENTARIO_HOST:localhost}:8083")
public interface InventarioClient {

    @PutMapping("/api/inventario/{id}/asignar")
    void asignarStock(@PathVariable("id") Long id, @RequestParam("cantidad") Integer cantidad);

    @PutMapping("/api/inventario/{id}/liberar")
    void liberarStock(@PathVariable("id") Long id, @RequestParam("cantidad") Integer cantidad);
}