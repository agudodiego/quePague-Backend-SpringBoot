package com.agudodiego.quePague.config;

import com.agudodiego.quePague.model.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /* Esta clase actua como binding (Spring <-> mi filtro) ya que le dice a Spring que configuracion queremos usar
    para hacer que el proceso de autenticacion funcione */


    @Qualifier("delegatedAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authEntryPoint;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenicationProvider;

    /*cuando la app arranca, security va a buscar un bean del tipo SecurityFilterChain, éste bean es el responsable de
    configurar toda la seguridad http de nuestra app. */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .exceptionHandling()
            .authenticationEntryPoint(authEntryPoint)
            .and()
            .csrf()
            .disable()
            .cors(Customizer.withDefaults())

            // configuramos la "lista blanca" (aquellas urls que no necesitan filtrarse)
            .authorizeHttpRequests()
            .requestMatchers("/API/auth/**")
            .permitAll()

            // configuramos el endpoint /API/admin para que solo sea accesible por los usuarios )
            .requestMatchers("/API/admin/**").hasAuthority(Role.ADMIN.name())

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
