package co.edu.uniandes.dse.thespa.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.ArticuloDeRopaRepository;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeAndArticuloRopaService {

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Pack de Servicios
    @Autowired
    ArticuloDeRopaRepository articuloRepo;

    //Obtener todos los articulos de ropa de una sede
    @Transactional
    public List<ArticuloDeRopaEntity> obtenerAllArticulos(Long sedeId) {
        return articuloRepo.findAll();
    }

    // Añadir un Pack de servicios a la sede
    @Transactional
    public ArticuloDeRopaEntity addSedeArticuloDeRopa(Long sedeId, Long ArticuloDeRopaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un ArticuloDeRopa con con id = {0}", ArticuloDeRopaId);
        Optional<ArticuloDeRopaEntity> packEntity = articuloRepo.findById(ArticuloDeRopaId);
        if (packEntity.isEmpty()) {
            throw new EntityNotFoundException("ARTICULO_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el pack ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getArticulosDeRopa().contains(packEntity.get())) {
            throw new IllegalOperationException("ARTICULO_ALREADY_EXISTS");
        }

        List<ArticuloDeRopaEntity> ArticuloDeRopaS = sedeEntity.get().getArticulosDeRopa();
        ArticuloDeRopaS.add(packEntity.get());

        sedeEntity.get().setArticulosDeRopa(ArticuloDeRopaS);

        log.info("Termina proceso de añadir a la sede un Articulo con con id = {0}", sedeId);

        return packEntity.get();
    }

    // obtiene un articulo de una sede dado el id de la sede y el id del articulo
    @Transactional
    public ArticuloDeRopaEntity getArticulo(Long sedeid, Long articuloID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Consultando el articulo con id = {} de la sede con id = {}", articuloID, sedeid);

        // Busca la sede
        Optional<SedeEntity> sedeBuscado = sedeRepo.findById(sedeid);
        if (sedeBuscado.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // Busca el articulo
        Optional<ArticuloDeRopaEntity> articulo = articuloRepo.findById(articuloID);
        if (articulo.isEmpty()) {
            throw new EntityNotFoundException("ARTICULO_NOT_FOUND");
        }

        // Verifica que el articulo este en la sede
        if (!sedeBuscado.get().getArticulosDeRopa().contains(articulo.get())) {
            throw new IllegalOperationException("ARTICULO_NOT_FOUND_IN_SEDE");
        }

        log.info("Articulo encontrado");

        // Retorna el articulo
        return articulo.get();
    }

    // Eliminar un articulo de ropa de la sede
    @Transactional
    public ArticuloDeRopaEntity deleteSedeArticuloDeRopa(Long sedeId, Long ArticuloDeRopaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover a la sede un ArticuloDeRopa con con id = {0}", ArticuloDeRopaId);
        Optional<ArticuloDeRopaEntity> articuloEntity = articuloRepo.findById(ArticuloDeRopaId);
        if (articuloEntity.isEmpty()) {
            throw new EntityNotFoundException("ARTICULO_NOT_FOUND");
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException("SEDE_NOT_FOUND");
        }

        // revisa si el articulo no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (!sedeEntity.get().getArticulosDeRopa().contains(articuloEntity.get())) {
            throw new IllegalOperationException("ARTICULO_NOT_FOUND_IN_CURRENT_SEDE");
        }

        List<ArticuloDeRopaEntity> articulos = sedeEntity.get().getArticulosDeRopa();
        articulos.remove(articuloEntity.get());

        sedeEntity.get().setArticulosDeRopa(articulos);

        log.info("Termina proceso de eliminar de la sede un ArticuloDeRopa con con id = {0}", sedeId);

        return articuloEntity.get();
    }
}