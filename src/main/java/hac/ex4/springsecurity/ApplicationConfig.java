package hac.ex4.springsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The spring security configuration
 */
@Configuration
@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter {
    /**
     * sets the users, their passwords and roles.
     * @param auth - for authentication
     * @throws Exception - if it could not configure the authentication
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("password")).roles("ADMIN").
                and()
                .withUser("user1").password(encoder.encode("user")).roles("USER").
                and()
                .withUser("user2").password(encoder.encode("user")).roles("USER").
                and()
                .withUser("user3").password(encoder.encode("user")).roles("USER");

    }

    /**
     * sets the login and logout and the protected pages.
     * @param http security
     * @throws Exception - if it could not configure the authentication
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/pay").hasAnyRole("USER", "ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error.html");
    }
}
