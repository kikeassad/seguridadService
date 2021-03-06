package mx.gob.rtc.servicios.seguridad;

import mx.gob.rtc.servicios.seguridad.entities.Usuario;
import mx.gob.rtc.servicios.seguridad.services.SeguridadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserInformation implements UserDetailsService {

    @Autowired
    SeguridadService seguridadService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = seguridadService.getUser(username);
        if(user == null)
            throw new UsernameNotFoundException(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getNombre()));
        });
        return new User(user.getNombre(), user.getContrasena(), authorities);
    }
}
