package mx.gob.rtc.servicios.seguridad.services;

import mx.gob.rtc.servicios.seguridad.entities.Rol;
import mx.gob.rtc.servicios.seguridad.entities.Usuario;
import mx.gob.rtc.servicios.seguridad.repositories.RolRepository;
import mx.gob.rtc.servicios.seguridad.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeguridadServiceImpl implements SeguridadService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Usuario> getUsers() {
        return usuarioRepository.findAll();
    }

    @Override
    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Usuario getUser(String username) {
        return usuarioRepository.findByNombre(username);
    }

    @Override
    public Usuario saveUser(Usuario user) {
        String hashPassword = bCryptPasswordEncoder.encode(user.getContrasena());
        user.setContrasena(hashPassword);
        return usuarioRepository.save(user);
    }

    @Override
    public Rol getRole(String rolename) {
        return rolRepository.findByNombre(rolename);
    }

    @Override
    public Rol saveRole(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void addRoleToUser(Usuario user, Rol role) {
        user.getRoles().add(role);
        usuarioRepository.save(user);
    }

}
