package mx.gob.rtc.servicios.seguridad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.gob.rtc.servicios.seguridad.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    public Usuario findByNombre(String nombre);
}
