package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
public class ArticuloDeRopaEntity extends ProductoEntity {

    private String talla;
    private String color;
    private Integer numDisponible;

    @PodamExclude
    @ManyToOne
    private SedeEntity sede;

}
