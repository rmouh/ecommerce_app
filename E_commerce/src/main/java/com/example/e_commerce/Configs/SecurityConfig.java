package com.example.e_commerce.Configs;
import com.example.e_commerce.Services.UserService;
import com.example.e_commerce.Controllers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //@Value("$security.jwt.secret-key")
    public static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    private String jwtSecretKey=SECRET_KEY;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())//web api
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/store/**").permitAll()
                        .requestMatchers("/{id}").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/account").permitAll()
                        .requestMatchers("/account/register").permitAll()
                        .requestMatchers("/account/login").permitAll()
                        .requestMatchers("/api/collections/add").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt((Customizer.withDefaults())))
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .build();
    }
    @Bean
    public JwtDecoder jwyDecoder(){
        var secretKey = new SecretKeySpec((jwtSecretKey.getBytes()), "");
        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256).build();

    }
    //configure the autoManager to user the service classe
    //it will use the jwtDecoder to decode the receved json web token
    @Bean
    public AuthenticationManager authenticationManager(UserService userService){
        DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return  new ProviderManager(provider);
    }
    /*
    @Autowired
    private CustomUserDetailsService userDetailsService; // Ajoutez ceci

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/users/register").permitAll() // Permet l'inscription sans authentification
                .anyRequest().authenticated() // Autres requêtes nécessitent une authentification
            .and()
            .httpBasic(); // Authentification basique
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}


