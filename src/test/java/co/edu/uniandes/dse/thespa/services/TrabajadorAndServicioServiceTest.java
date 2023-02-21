package co.edu.uniandes.dse.thespa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ TrabajadorService.class, ServicioService.class })
public class TrabajadorAndServicioServiceTest {
    // Servicio que se va a probar
    @Autowired
    private TrabajadorService trabajadorService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de trabajadores
    private List<TrabajadorEntity> trabajadores = new ArrayList<>();

    // Lista de servicios
    private List<ServicioEntity> servicios = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from TrabajadorEntity");
        entityManager.getEntityManager().createQuery("delete from ServicioEntity");

    }

    // Inserta los datos de prueba en la lista de Trabajadores
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            TrabajadorEntity tEntity = factory.manufacturePojo(TrabajadorEntity.class);
            ServicioEntity sEntity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(sEntity);

            List<ServicioEntity> s = new ArrayList<>();
            s.add(sEntity);
            tEntity.setServicios(s);
            entityManager.persist(tEntity);
            trabajadores.add(tEntity);

        }
        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(entity);
            servicios.add(entity);
        }
    }

    @Test
    // agregar un servicios a un trabajador
    void testAddServiciosToTrabajador() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicio = servicios.get(0);

        ServicioEntity answer = trabajadorService.addServicioToTrabajador(trabajador.getId(), servicio.getId());
        assertNotNull(answer);
        assertEquals(servicio.getId(), answer.getId());
    }

    @Test
    // agregar un servicio ya existente en un trabajador -> Operación ilegal
    void testAddServicioToTrabajadorAlreadyExist() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicio = trabajador.getServicios().get(0);

        assertThrows(IllegalOperationException.class, () -> {
            trabajadorService.addServicioToTrabajador(trabajador.getId(), servicio.getId());
        });
    }

    @Test
    // eliminar un servicio de un trabajador
    void testdeleteServicioToTrabajador() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicio = trabajador.getServicios().get(0);

        ServicioEntity answer = trabajadorService.deleteServicioTrabajador(trabajador.getId(), servicio.getId());
        assertNotNull(answer);
        assertEquals(servicio.getId(), answer.getId());
    }

    @Test
    // eliminar un servicio que no existe de un trabajador -> Operación ilegal
    void testdeleteServicioToTrabajadorNotExist() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicio = servicios.get(0);

        assertThrows(IllegalOperationException.class, () -> {
            trabajadorService.deleteServicioTrabajador(trabajador.getId(), servicio.getId());
        });
    }
}
