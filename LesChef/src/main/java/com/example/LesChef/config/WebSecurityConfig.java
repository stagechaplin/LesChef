package com.example.LesChef.config;

import com.example.LesChef.Repository.CustomerRepository;
import com.example.LesChef.entity.Customer;
import com.example.LesChef.service.CustomerDetailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration //
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    //로그인 안되어있을 때 허용되는 stataic 파일
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring()
                .requestMatchers("/css/**")
                .requestMatchers("/img/**")
                .requestMatchers("/js/**")
        );
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize // 로그인 없이 이동 가능한 메서드 및 페이지
//                        .requestMatchers("/signup","/findId","/findPwd").permitAll()
                        .requestMatchers("/main", "/list/**","/NoticeBoardMain")
                        .permitAll().anyRequest().authenticated()
                )

                // 로그인 메서드
                .formLogin(login -> login
                        .loginPage("/main").permitAll()
                        .loginProcessingUrl("/login")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            User user = (User)authentication.getPrincipal();    //로그인 성공 시 인증 정보를 가져옴
                            Customer customer = customerRepository.findById(user.getUsername()).orElse(null);
                            if(customer != null){
                                HttpSession session = request.getSession(true);
                                session.setAttribute("customer", customer);
                                session.setMaxInactiveInterval(1800);
                                response.sendRedirect("/list");

                            }

                        })
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
                            request.getSession(false).invalidate();
                            response.sendRedirect("/main");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID", "remember-me")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/main")


                ).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, CustomerDetailService customerDetailService)
            throws Exception{
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerDetailService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
