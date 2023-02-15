package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;
import co.edu.uniandes.dse.thespa.repositories.ArticuloDeRopaRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticuloDeRopaService {

    // Inyeccion de dependencias -> Repositorio ArticuloDeRopa
    @Autowired
    private ArticuloDeRopaRepository articuloDeRopaRepository;

    // creación de articulos de ropa
    @Transactional
    public ArticuloDeRopaEntity createArticuloDeRopa(ArticuloDeRopaEntity articuloDeRopa) {
        log.info("Creando un articulo de ropa nuevo");
        return articuloDeRopaRepository.save(articuloDeRopa);
    }

    // obtener todos los articulos de ropa
    @Transactional
    public List<ArticuloDeRopaEntity> getArticulosDeRopa() {
        log.info("Consultando todos los articulos de ropa");
        return articuloDeRopaRepository.findAll();
    }

    // obtener un articulo de ropa
    @Transactional
    public ArticuloDeRopaEntity getArticuloDeRopa(Long id) throws EntityNotFoundException {
        log.info("Consultando el articulo de ropa con id = {}", id);
        Optional<ArticuloDeRopaEntity> articulosBuscados = articuloDeRopaRepository.findById(id);
        if (articulosBuscados.isEmpty()) {
            throw new EntityNotFoundException("El articulo de ropa con el id = " + id + " no existe");
        }
        log.info("Articulo de ropa encontrado");
        return articulosBuscados.get();

    }

    // actualizar un articulo de ropa
    @Transactional
    public ArticuloDeRopaEntity updateArticuloDeRopaEntity(Long id, ArticuloDeRopaEntity articuloDeRopa)
            throws EntityNotFoundException {
        log.info("Actualizando el articulo de ropa con id = {}", id);
        Optional<ArticuloDeRopaEntity> articulosBuscados = articuloDeRopaRepository.findById(id);
        if (articulosBuscados.isEmpty()) {
            throw new EntityNotFoundException("El articulo de ropa con el id = " + id + " no existe");
        }

        articuloDeRopa.setId(id);
        log.info("articulo de ropa actualizado");
        return articuloDeRopaRepository.save(articuloDeRopa);
    }

    // borrar un articulo de ropa
    @Transactional
    public void deleteArticuloDeRopa(Long id) throws EntityNotFoundException {
        log.info("Borrando el articulo de ropa con id = {}", id);
        Optional<ArticuloDeRopaEntity> articulosBuscados = articuloDeRopaRepository.findById(id);
        if (articulosBuscados.isEmpty()) {
            throw new EntityNotFoundException("El articulo de ropa con el id = " + id + " no existe");
        }
        articuloDeRopaRepository.deleteById(id);
        log.info("articulo de ropa borrado");
    }

}