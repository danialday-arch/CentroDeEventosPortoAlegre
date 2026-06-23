package com.example.ms_pedidos.client;

import com.example.ms_pedidos.client.dto.ProductoCatalogoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", url = "http://${MS_CATALOGO_HOST:localhost}:8087/api/catalogo")
public interface CatalogoClient {

    @GetMapping("/{id}")
    ProductoCatalogoDTO obtenerProducto(@PathVariable("id") Long id);
}