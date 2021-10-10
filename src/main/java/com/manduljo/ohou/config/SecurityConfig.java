package com.manduljo.ohou.config;
import com.manduljo.ohou.filter.AuthenticationFilter;
import com.manduljo.ohou.filter.AuthorizationFilter;
import com.manduljo.ohou.oauth2.OAuth2SuccessHandler;
import com.manduljo.ohou.oauth2.service.CustomOAuth2UserService;
import com.manduljo.ohou.util.CookieUtil;
import com.manduljo.ohou.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final AuthorizationFilter authorizationFilter;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
                .and()
                .oauth2Login()
                    .successHandler(oAuth2SuccessHandler)
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService);
        http.addFilter(new AuthenticationFilter(authenticationManager(),jwtUtil,cookieUtil));
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
