package mx.gob.rtc.servicios.seguridad.web;


import mx.gob.rtc.servicios.seguridad.entities.Rol;
import mx.gob.rtc.servicios.seguridad.entities.Usuario;
import mx.gob.rtc.servicios.seguridad.entities.UsuarioRol;
import mx.gob.rtc.servicios.seguridad.services.SeguridadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class SeguridadController {

    @Autowired
    private SeguridadService seguridadService;

    @GetMapping("/admin/usuarios")
    public List<Usuario> obtieneUsuarios(){
        return seguridadService.getUsers();
    }

    @GetMapping("/admin/roles")
    public List<Rol> obtieneRoles(){
        return seguridadService.getRoles();
    }

    @PostMapping("/admin/guarda-usuario")
    public Usuario guardaUsuario(@RequestBody Usuario user){
        if(user.getId() == null && seguridadService.getUser(user.getNombre())!=null)
            throw new RuntimeException("The username already exists");
        seguridadService.saveUser(user);
        return user;
    }

    @PostMapping("/admin/guarda-rol")
    public Rol guardaRol(@RequestBody Rol rol){
        if(rol.getId()==null && seguridadService.getRole(rol.getNombre())!=null)
            throw new RuntimeException("The rolename already exists");
        seguridadService.saveRole(rol);
        return rol;
    }

    @PostMapping("/admin/enrola-usuario")
    public void enrolaUsuario(@RequestBody UsuarioRol usuarioRol){
        Usuario usuario = seguridadService.getUser(usuarioRol.getUsuario());
        Rol rol = seguridadService.getRole(usuarioRol.getRol());
        seguridadService.addRoleToUser(usuario, rol);
    }


}
