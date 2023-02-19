package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;
import co.edu.uniandes.dse.thespa.repositories.ServicioExtraRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServicioExtraService {

    // Inyeccion de dependencias -> Repositorio ServicioExtra
    @Autowired
    private ServicioExtraRepository servicioExtraRepository;

    // Método para la Creación de un servicio extra
    @Transactional
    public ServicioExtraEntity createServicioExtra(ServicioExtraEntity servicioExtraEntity)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia el proceso de creación del servicio extra");

        if (servicioExtraEntity.getNombre() == null)
            throw new IllegalOperationException("El nombre del servicio extra no puede estar vacío");

        log.info("Termina proceso de creación del servicio extra");
        return servicioExtraRepository.save(servicioExtraEntity);
    }

    // Método para obtener todos los servicios extra
    @Transactional
    public List<ServicioExtraEntity> getServiciosExtras() {
        log.info("Inicia proceso de consultar todos los servicios extra");
        return servicioExtraRepository.findAll();
    }

    // Método para obtener un servicio extra por ID
    @Transactional
    public ServicioExtraEntity getTrabajador(Long servicioExtraId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el servicio extra con id = {0}", servicioExtraId);
        Optional<ServicioExtraEntity> servicioExtraEntity = servicioExtraRepository.findById(servicioExtraId);
        if (servicioExtraEntity.isEmpty())
            throw new EntityNotFoundException("El servicio extra con el id = " + servicioExtraId + " no existe");
        log.info("Termina proceso de consultar el servicio extra con id = {0}", servicioExtraId);
        return servicioExtraEntity.get();
    }

    // Método para actualizar un servicio extra
    @Transactional
    public ServicioExtraEntity updateServicioExtra(Long servicioExtraId, ServicioExtraEntity servicioExtra)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar el servicio extra con id = {0}", servicioExtraId);
        Optional<ServicioExtraEntity> servicioExtraEntity = servicioExtraRepository.findById(servicioExtraId);
        if (servicioExtraEntity.isEmpty())
            throw new EntityNotFoundException("El servicio extra con el id = " + servicioExtraId + " no existe");

        if (servicioExtra.getNombre() == null)
            throw new IllegalOperationException("El nombre del servicio extra no puede estar vacío");

        servicioExtra.setId(servicioExtraId);
        log.info("Termina proceso de actualizar el servicio extra con id = {0}", servicioExtraId);
        return servicioExtraRepository.save(servicioExtra);
    }

    // Método para borrar un servicio extra
    @Transactional
    public void deleteServicioExtra(Long servicioExtraId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el servicio extra con id = {0}", servicioExtraId);
        Optional<ServicioExtraEntity> servicioExtraEntity = servicioExtraRepository.findById(servicioExtraId);
        if (servicioExtraEntity.isEmpty())
            throw new EntityNotFoundException(
                    "No se encontró el servicio extra con id = " + servicioExtraId + " no existe");

        servicioExtraRepository.deleteById(servicioExtraId);
        log.info("Termina proceso de borrar el servicio extra con id = {0}", servicioExtraId);
    }
}