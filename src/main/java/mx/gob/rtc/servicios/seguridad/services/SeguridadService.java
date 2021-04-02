package mx.gob.rtc.servicios.seguridad.services;

import mx.gob.rtc.servicios.seguridad.entities.Rol;
import mx.gob.rtc.servicios.seguridad.entities.Usuario;


import java.util.List;

public interface SeguridadService {

    public List<Usuario> getUsers();
    public List<Rol> getRoles();
    public Usuario getUser(String username);
    public Usuario saveUser(Usuario user);
    public Rol getRole(String rolename);
    public Rol saveRole(Rol role);
    public void addRoleToUser(Usuario user, Rol role);

}
