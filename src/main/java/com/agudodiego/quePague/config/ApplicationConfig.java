package com.agudodiego.quePague.config;

import com.agudodiego.quePague.exceptions.NotFoundException;
import com.agudodiego.quePague.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    // Esta clase contiene todas las configuraciones de la aplicacion tales como Beans, etc.

    private final PersonRepository personRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> personRepository.findByUsername(username)
                .orElseThrow(()-> new NotFoundException("Person not found"));
    }

    @Bean
    public AuthenticationProvider authenicationProvider() {
    /* El authenicationProvider es el "Data Access Object" (DAO) responsable de capturar los userDetails y tambien de codificar
    la contraseña, etc. */
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    /* El Authentication Manager es responsable de manejar la utenticacion, para eso usamos un metodo en particular
    que nos ayuda a hacer la autenticacion basado en el username y password.
    El objeto config tiene la inforamacion del manager */
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    /* Este es el metodo de encriptacion */
        return new BCryptPasswordEncoder();
    }
}
