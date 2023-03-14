package co.edu.uniandes.dse.thespa.controllers;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.dto.ServicioDTO;
import co.edu.uniandes.dse.thespa.services.SedeAndServicioService;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;


@RestController
@RequestMapping("/sedes")
public class SedeAndServiciosController {

    // inyectar el servicio de sedes y trabajadores
    private SedeAndServicioService SaS;

    // inyecta el model mapper
    private ModelMapper modelMapper;

    // metodo para encontrar todos los servicios dentro de una sede dado su id
    @GetMapping(value = "{id}/servicios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        List<ServicioEntity> servicios = SaS.obtenerAllServicios(id);
        return modelMapper.map(servicios, new TypeToken<List<ServicioDTO>>() {
        }.getType());
    }

    // metodo para agregar un servicio a una sede dado su id
    @PostMapping(value = "{id}/servicios/{idServicio}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioDTO create(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioEntity servicio = SaS.addSedeServicio(id, idServicio);
        return modelMapper.map(servicio, ServicioDTO.class);
    }

    // metodo para eliminar un servicio de una sede dado su id
    @DeleteMapping(value = "{id}/servicio/{idServicio}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ServicioDTO delete(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioEntity servicioEliminado = SaS.deleteSedeServicio(id, idServicio);
        return modelMapper.map(servicioEliminado, ServicioDTO.class);
    }

}