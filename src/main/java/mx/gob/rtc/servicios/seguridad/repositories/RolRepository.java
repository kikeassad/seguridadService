package mx.gob.rtc.servicios.seguridad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.gob.rtc.servicios.seguridad.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, String> {
    public Rol findByNombre(String nombre);
}
