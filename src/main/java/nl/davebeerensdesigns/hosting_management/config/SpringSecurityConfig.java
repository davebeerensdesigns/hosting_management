package nl.davebeerensdesigns.hosting_management.config;

import nl.davebeerensdesigns.hosting_management.filter.JwtRequestFilter;
import nl.davebeerensdesigns.hosting_management.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    public CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter){
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                ex.getMessage()
                        )
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Public endpoints
                .antMatchers(HttpMethod.POST,"/wp-json/hm/v1/authenticate").permitAll()
                .antMatchers(HttpMethod.GET,"/wp-json/hm/v1/authenticated").permitAll()
                .antMatchers(HttpMethod.GET,"/wp-json/hm/v1/clients").permitAll()
                .antMatchers(HttpMethod.GET,"/wp-json/hm/v1/clients/**").permitAll()
                // Private endpoints
                .antMatchers(HttpMethod.GET,"/wp-json/hm/v1/authenticated").authenticated()
                .antMatchers("/wp-json/hm/v1/users/*/authorities").hasRole("ADMIN")
                .antMatchers("/wp-json/hm/v1/users").hasRole("ADMIN")
                .antMatchers("/wp-json/hm/v1/users/**").hasRole("ADMIN")
                .antMatchers("/wp-json/hm/v1/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(
                jwtRequestFilter,
                UsernamePasswordAuthenticationFilter.class
        );

    }

}