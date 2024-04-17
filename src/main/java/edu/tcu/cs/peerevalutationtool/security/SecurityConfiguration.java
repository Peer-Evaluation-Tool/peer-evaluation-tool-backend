/*package edu.tcu.cs.peerevalutationtool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // This is just an example and should be replaced with actual authentication provider
        auth
                .inMemoryAuthentication()
                .withUser("user@example.com")
                .password(passwordEncoder().encode("password"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // If you are not creating a CSRF token in forms you should disable it
                .authorizeRequests()
                .antMatchers("/register", "/login", "/h2-console/**").permitAll()  // Allows public access to registration and login
                .anyRequest().authenticated()  // All other requests need authentication
                .and()
                .formLogin()
                .loginPage("/login")  // Specifies the path to the login page
                .defaultSuccessUrl("/homepage", true)  // Redirect to homepage on success
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")  // Redirect to login page on logout
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");  // Custom page for 403 errors

        // Configurations for H2 console
        http.headers().frameOptions().disable();  // Required to enable H2 console in web browser
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
*/