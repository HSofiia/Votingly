package be.kdg.team9.integration4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeHttpRequests(
                        auths -> auths
                                .requestMatchers(regexMatcher("^/(signup)"))
                                .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/js/**"),
                                        antMatcher(HttpMethod.GET, "/css/**"),
                                        antMatcher(HttpMethod.GET, "/images/**"),
                                        antMatcher(HttpMethod.GET, "/webjars/**"),
                                        regexMatcher(HttpMethod.GET, "\\.ico$"))
                                .permitAll()
                                .requestMatchers(
                                        antMatcher(HttpMethod.GET, "/api/**"),
                                        antMatcher(HttpMethod.POST, "/api/**"),
                                        antMatcher(HttpMethod.GET, "/surveys/**"),
                                        antMatcher(HttpMethod.GET, "/signup/**"),
                                        antMatcher(HttpMethod.POST, "/api/signup/**"),
                                        regexMatcher(HttpMethod.GET, "^/login\\?.*"),
                                        antMatcher(HttpMethod.GET, "/thank-you-page/**"),
                                        antMatcher(HttpMethod.GET, "/features"),
                                        antMatcher(HttpMethod.GET, "/unauthenticated")
                                        )
                                .permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/"))
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                                // .permitAll() // for development
                )
                // for development
                // .csrf(csrf -> csrf.ignoringRequestMatchers(
                //         antMatcher(HttpMethod.POST, "/api/**")
                // ))
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(
                                (request, response, exception) -> {
                                    if (request.getRequestURI().startsWith("/api")) {
                                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    } else if (request.getRequestURI().startsWith("/error")) {
                                        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                                    }else if (request.getRequestURI().contains("/details")) {
                                        request.getRequestDispatcher("/unauthenticated").forward(request, response);
                                    } else if (request.getUserPrincipal() == null) {
                                        request.getRequestDispatcher("/unauthenticated").forward(request, response);
                                    } else {
                                        response.sendRedirect(request.getContextPath() + "/login");
                                    }
                                })
                ).logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
