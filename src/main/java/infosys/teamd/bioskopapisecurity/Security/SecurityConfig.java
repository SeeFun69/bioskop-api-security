package infosys.teamd.bioskopapisecurity.Security;

import infosys.teamd.bioskopapisecurity.Filter.CustomAuthenticationFilter;
import infosys.teamd.bioskopapisecurity.Filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/teamD/v1/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeHttpRequests().antMatchers("/teamD/v1/login/**", "/teamD/v1/token/refresh/**")
                .permitAll();

        http.authorizeHttpRequests().antMatchers(GET, "/teamD/v1/users/**", "/teamD/v1/users/{users_Id}")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN", "ROLE_MANAGER");

        http.authorizeHttpRequests().antMatchers(POST, "/teamD/v1/user/save/**", "/teamD/v1/role/addtouser/**", "/teamD/v1/users/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");

        http.authorizeHttpRequests().antMatchers(DELETE, "/teamD/v1/users/{users_Id}/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
