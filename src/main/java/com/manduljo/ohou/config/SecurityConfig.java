package com.manduljo.ohou.config;

import com.manduljo.ohou.filter.AuthenticationFilter;
import com.manduljo.ohou.filter.AuthorizationFilter;
import com.manduljo.ohou.filter.CustomAuthenticationEntryPoint;
import com.manduljo.ohou.mongo.constant.AcceptType;
import com.manduljo.ohou.oauth2.handler.OAuth2FailureHandler;
import com.manduljo.ohou.oauth2.handler.OAuth2SuccessHandler;
import com.manduljo.ohou.oauth2.service.CustomOAuth2UserService;
import com.manduljo.ohou.util.CookieUtil;
import com.manduljo.ohou.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final AuthorizationFilter authorizationFilter;
    private final JwtUtil jwtUtil;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final CookieUtil cookieUtil;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                    .requestMatchers(request -> !request.getHeader("accept").isBlank() && request.getHeader("accept").contains(AcceptType.API_V1)).permitAll()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/api/join").permitAll()
                    .antMatchers("/api/products/**").permitAll()
                    .antMatchers("/api/store/**").permitAll()
                    .antMatchers("/api/category/**").permitAll()
                    .antMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
                .and()
                .oauth2Login()
                    .successHandler(oAuth2SuccessHandler)
                    .failureHandler(oAuth2FailureHandler)
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService);
        http.addFilter(new AuthenticationFilter(authenticationManager(),jwtUtil,cookieUtil));
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
        
    }
}
