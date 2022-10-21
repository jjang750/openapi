package com.aegisep.config;

import com.aegisep.auth.ApiUserDetailsService;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/openapi/**", "/pages", "/auth", "/login", "/resources/**", "/static/**",
            "/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico", "/oauth2"
    };
    
    @Bean
    public ApiUserDetailsService userDetailsService(){
        return new ApiUserDetailsService();
    }
	@Bean
    public PasswordEncoder passwordEncoder(){
        // for development
        return new BCryptPasswordEncoder(30);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        log.info("filterChain start");

        // We don't need CSRF for this example
        httpSecurity.authorizeRequests()
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
//                .antMatchers("/admin/**").hasAnyRole("ADMIN")
//                .antMatchers("/manager/**").hasAnyRole("MANAGER")
//                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/auth")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .csrf().disable();

        log.info("filterChain end");

        return httpSecurity.build();
    }
}
