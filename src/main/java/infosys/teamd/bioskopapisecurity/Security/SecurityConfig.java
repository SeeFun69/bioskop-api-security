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

        http.authorizeHttpRequests().antMatchers(GET,
                        /*endpoint user*/
                        "/teamD/v1/users/**", "/teamD/v1/users/{users_Id}/**",
                        /*endpoint booking*/
                        "/teamD/v1/booking/**",
                        /*endpoint films*/
                        "/teamD/v1/films/**", "/teamD/v1/films/{filmId}/**",
                        /*endpoint seats*/
                        "/teamD/v1/seats/**"
                )
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");

        http.authorizeHttpRequests().antMatchers(POST,
                        /*endpoint user*/
                        "/teamD/v1/user/save/**", "/teamD/v1/role/addtouser/**", "/teamD/v1/users/**",
                        /*endpoint films*/
                        "teamD/v1/films/**", "teamD/v1/films/{isPlaying}/**",
                        /*endpoint seats*/
                        "teamD/v1/seats/**", "teamD/v1/seats/isAvailable/**"
                )
                .hasAnyAuthority("ROLE_ADMIN");

        http.authorizeHttpRequests().antMatchers(DELETE,
                        /*endpoint user*/
                        "/teamD/v1/users/{users_Id}/**",
                        /*endpoint films*/
                        "/teamD/v1/films/{filmId}/**",
                        /*endpoint seats*/
                        "/teamD/v1/seats/{seatId}/**"
                )
                .hasAnyAuthority("ROLE_ADMIN");

        http.authorizeHttpRequests().antMatchers(PUT,
                        /*endpoint user*/
                        "/teamD/v1/users/{users_Id}/**",
                        /*endpoint films*/
                        "/teamD/v1/films/{filmId}/**",
                        /*endpoint seats*/
                        "/teamD/v1/seats/{seatId/**}"
                )
                .hasAnyAuthority("ROLE_ADMIN");




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
