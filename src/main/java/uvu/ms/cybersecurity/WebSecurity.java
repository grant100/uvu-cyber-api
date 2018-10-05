package uvu.ms.cybersecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uvu.ms.cybersecurity.security.filters.JWTAuthenticationFilter;
import uvu.ms.cybersecurity.security.providers.JWTAuthProvider;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{


    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().formLogin().disable()
                .exceptionHandling()
                .and()
                .addFilterAt(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthProvider());
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JWTAuthenticationFilter(authenticationManager());
    }

    @Bean
    JWTAuthProvider jwtAuthProvider() {
        return new JWTAuthProvider();
    }
}
