package co.edu.uniandes.dse.thespa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SedeDTO {
    private String nombre;
    private String imagen;
    private UbicacionDTO ubicacion;
}