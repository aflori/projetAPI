package fr.ecolnum.projectapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class declares settings for authentication and authorization
 */
@Configuration
@EnableMethodSecurity(
        securedEnabled = true
)
public class SecurityConfig {

    /**
     * This method return the encoder used for password
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * This method defines the necessary authorizations for each route, it is executed from top to bottom.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                //replaced by securedEnabled
                .authorizeHttpRequests((requests) -> requests
                                .anyRequest().permitAll() // no authentication needed for dev and test purpose
//                        .requestMatchers("/api/admin/criteria").permitAll() // you don't need to be authenticated
//                        .requestMatchers("/api/admin/candidate").hasRole("ADMIN") // you need to be authenticated with admin role
//                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN","USER") // you need to be authenticated with admin or user role
//                        .anyRequest().authenticated() // you need to be authenticated no matter your role
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
