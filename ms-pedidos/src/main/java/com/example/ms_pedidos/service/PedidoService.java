package com.example.ms_pedidos.service;

import com.example.ms_pedidos.client.BarClient;
import com.example.ms_pedidos.client.CatalogoClient;
import com.example.ms_pedidos.client.CocinaClient;
import com.example.ms_pedidos.client.InventarioClient;
import com.example.ms_pedidos.client.EmpleadoClient; // Cliente añadido
import com.example.ms_pedidos.client.dto.OrdenTrabajoDTO;
import com.example.ms_pedidos.client.dto.ProductoCatalogoDTO;
import com.example.ms_pedidos.model.DetallePedido;
import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.repository.PedidoRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CatalogoClient catalogoClient;
    private final InventarioClient inventarioClient;
    private final CocinaClient cocinaClient;
    private final BarClient barClient;
    private final EmpleadoClient empleadoClient; // Inyección añadida

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        // 0. Validar que el empleado (mesero) exista antes de iniciar la transacción
        try {
            empleadoClient.obtenerEmpleadoPorId(pedido.getEmpleadoId());
        } catch (FeignException.NotFound e) {
            // Error 404 real: El ID no está en la base de datos de empleados
            throw new IllegalArgumentException("Error: El empleado con ID " + pedido.getEmpleadoId() + " no existe.");
        } catch (FeignException e) {
            // Error de conexión o ruta mal configurada
            System.err.println("=== ERROR FEIGN EMPLEADOS ===");
            e.printStackTrace();
            throw new IllegalArgumentException("Fallo técnico de conexión con ms-empleados: " + e.getMessage());
        }

        // 1. Vincula bidireccionalmente los detalles
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(detalle -> detalle.setPedido(pedido));
        }

        // 2. Guarda el pedido inicial en estado PENDIENTE
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // 3. Orquestación: Procesar cada detalle del pedido
        for (DetallePedido detalle : pedidoGuardado.getDetalles()) {

            // a. Traer la receta y el área desde el catálogo
            ProductoCatalogoDTO producto = catalogoClient.obtenerProducto(detalle.getProductoId());

            // b. Descontar del inventario cada insumo de la receta
            if (producto.getReceta() != null) {
                for (ProductoCatalogoDTO.RecetaDTO insumo : producto.getReceta()) {
                    Integer cantidadTotalADescontar = insumo.getCantidad() * detalle.getCantidad();
                    inventarioClient.asignarStock(insumo.getInsumoId(), cantidadTotalADescontar);
                }
            }

            // c. Armar la orden para enviar a las áreas de preparación
            OrdenTrabajoDTO orden = new OrdenTrabajoDTO();
            orden.setPedidoId(pedidoGuardado.getId());
            orden.setProductoId(detalle.getProductoId());
            orden.setCantidad(detalle.getCantidad());

            // d. Enrutar según el área
            if ("COCINA".equalsIgnoreCase(producto.getArea())) {
                cocinaClient.enviarOrden(orden);
            } else if ("BAR".equalsIgnoreCase(producto.getArea())) {
                barClient.enviarOrden(orden);
            }
        }

        // 4. Actualizar el estado a EN_PREPARACION tras mandar las órdenes exitosamente
        pedidoGuardado.setEstado("EN_PREPARACION");
        return pedidoRepository.save(pedidoGuardado);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }
}