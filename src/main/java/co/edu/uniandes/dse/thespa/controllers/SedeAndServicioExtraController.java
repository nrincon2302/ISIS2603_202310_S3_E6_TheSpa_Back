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
import co.edu.uniandes.dse.thespa.dto.ServicioExtraDTO;
import co.edu.uniandes.dse.thespa.services.SedeAndServicioExtraService;
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;



@RestController
@RequestMapping("/sedes")
public class SedeAndServicioExtraController {

    // inyectar el servicio de sedes y servicios extras
    private SedeAndServicioExtraService SaE;

    // inyecta el model mapper
    private ModelMapper modelMapper;

    // metodo para encontrar todos los servicios extras dentro de una sede dado su id
    @GetMapping(value = "{id}/serviciosExtra")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioExtraEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        List<ServicioExtraEntity> servicios = SaE.obtenerAllServicios(id);
        return modelMapper.map(servicios, new TypeToken<List<ServicioExtraDTO>>() {
        }.getType());
    }

    // metodo para agregar un servicio Extra a una sede dado su id
    @PostMapping(value = "{id}/serviciosExtra/{idServicio}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioExtraDTO create(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioExtraEntity servicio = SaE.addSedeExtraService(id, idServicio);
        return modelMapper.map(servicio, ServicioExtraDTO.class);
    }

    // metodo para eliminar un servicio extra de una sede dado su id
    @DeleteMapping(value = "{id}/serviciosExtra/{idServicio}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ServicioExtraDTO delete(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioExtraEntity servicioEliminado = SaE.deleteSedeExtraService(id, idServicio);
        return modelMapper.map(servicioEliminado, ServicioExtraDTO.class);
    }

}
