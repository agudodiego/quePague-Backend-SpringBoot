package com.agudodiego.quePague.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /* Esta clase actua como binding (Spring <-> mi filtro) ya que le dice a Spring que configuracion queremos usar
    para hacer que el proceso de autenticacion funcione */

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenicationProvider;

    /*cuando la app arranca, security va a buscar un bean del tipo SecurityFilterChain, éste bean es el responsable de
    configurar toda la seguridad http de nuestra app. */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            // configuramos la "lista blanca" (aquellas urls que no necesitan filtrarse)
            .authorizeHttpRequests()
            .requestMatchers("/API/auth/**")
            .permitAll()
            // luego le decimos que cualquier otra request debe estar autenticada
            .anyRequest()
            .authenticated()
            .and()
            // luego configuro el manejo de la sesion, el estado de la sesion NO debe ser guardado, ésto nos ayuda a que podamos verificar CADA request
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // luego le decimos que proveedor de autenticacion queremos usar
            .authenticationProvider(authenicationProvider)
            // luego uso el filtro creado por mi, lo ejecuto ANTES del filtro UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
