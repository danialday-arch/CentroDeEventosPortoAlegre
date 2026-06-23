package com.example.ms_mesas.service;

import com.example.ms_mesas.model.Mesa;
import com.example.ms_mesas.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository mesaRepository;
    private final RestTemplate restTemplate;

    // Helper para obtener el host (Docker o Localhost)
    private String getHost(String envVar) {
        return System.getenv(envVar) != null ? System.getenv(envVar) : "localhost";
    }

    public List<Mesa> findAll() {
        return mesaRepository.findAll();
    }

    public List<Mesa> findByEstado(String estado) {
        return mesaRepository.findByEstado(estado);
    }

    public Optional<Mesa> findById(Long id) {
        return mesaRepository.findById(id);
    }

    public Mesa save(Mesa mesa) {
        try {
            // Infraestructura: usa variable MS_INFRAESTRUCTURA_HOST
            String host = getHost("MS_INFRAESTRUCTURA_HOST");
            String urlEspacio = "http://" + host + ":8085/api/infraestructura/" + mesa.getEspacioId();
            restTemplate.getForEntity(urlEspacio, Object.class);

        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Error: El espacio con ID " + mesa.getEspacioId() + " no existe.");
        } catch (Exception e) {
            throw new IllegalArgumentException("Fallo técnico de conexión con ms-infraestructura: " + e.getMessage());
        }

        if (mesa.getEstado() == null) {
            mesa.setEstado("LIBRE");
        }

        if (mesa.getUtensilios() != null) {
            String hostInv = getHost("MS_INVENTARIO_HOST");
            mesa.getUtensilios().forEach(utensilio -> {
                utensilio.setMesa(mesa);
                String url = "http://" + hostInv + ":8083/api/inventario/" + utensilio.getInsumoId()
                        + "/asignar?cantidad=" + utensilio.getCantidad();
                restTemplate.put(url, null);
            });
        }
        return mesaRepository.save(mesa);
    }

    public Optional<Mesa> update(Long id, Mesa mesaActualizada) {
        return mesaRepository.findById(id).map(mesaExistente -> {
            mesaExistente.setNumero(mesaActualizada.getNumero());
            mesaExistente.setEstado(mesaActualizada.getEstado());
            mesaExistente.setEspacioId(mesaActualizada.getEspacioId());

            if (mesaActualizada.getUtensilios() != null) {
                mesaExistente.getUtensilios().clear();
                mesaActualizada.getUtensilios().forEach(u -> u.setMesa(mesaExistente));
                mesaExistente.getUtensilios().addAll(mesaActualizada.getUtensilios());
            }
            return mesaRepository.save(mesaExistente);
        });
    }

    public void deleteById(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));

        if (mesa.getUtensilios() != null) {
            String hostInv = getHost("MS_INVENTARIO_HOST");
            mesa.getUtensilios().forEach(utensilio -> {
                String url = "http://" + hostInv + ":8083/api/inventario/" + utensilio.getInsumoId()
                        + "/liberar?cantidad=" + utensilio.getCantidad();
                restTemplate.put(url, null);
            });
        }
        mesaRepository.deleteById(id);
    }
}