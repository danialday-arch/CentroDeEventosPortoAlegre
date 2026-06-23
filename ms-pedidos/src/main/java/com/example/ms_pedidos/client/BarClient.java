package com.example.ms_pedidos.client;

import com.example.ms_pedidos.client.dto.OrdenTrabajoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-orden-bar", url = "http://${MS_BAR_HOST:localhost}:8090/api/bar")
public interface BarClient {

    @PostMapping("/ordenes")
    void enviarOrden(@RequestBody OrdenTrabajoDTO orden);
}