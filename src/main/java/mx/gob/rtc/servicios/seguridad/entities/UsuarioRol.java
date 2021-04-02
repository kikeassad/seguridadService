package mx.gob.rtc.servicios.seguridad.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class UsuarioRol implements Serializable {
    private String usuario;
    private String rol;
}
