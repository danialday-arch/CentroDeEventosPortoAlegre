package com.example.ms_pedidos.client;

import com.example.ms_pedidos.client.dto.OrdenTrabajoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-orden-cocina", url = "http://${MS_COCINA_HOST:localhost}:8089/api/cocina")
public interface CocinaClient {

    @PostMapping("/ordenes")
    void enviarOrden(@RequestBody OrdenTrabajoDTO orden);
}