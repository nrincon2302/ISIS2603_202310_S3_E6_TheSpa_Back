package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.Entity;

import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que contiene las coordenadas geográficas
 *
 * @author Luisa Hernández
 */

@Getter
@Setter
@Entity
public class UbicacionEntity extends BaseEntity {

    private Double latitud;
    private Double longitud;
    private String ciudad;
    private String direccion;

    @PodamExclude
    @OneToOne
    private SedeEntity sede;

}