package com.Buster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
                  .csrf().disable()
                  .authorizeRequests()
                  .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                  .antMatchers(HttpMethod.POST,"/api/**").permitAll()
                  .anyRequest()
                  .authenticated()
                  .and()
                  .httpBasic();
     }
     @Bean
     public PasswordEncoder passwordEncoder(){

          return new BCryptPasswordEncoder();
     }

     @Override
     @Bean
     protected UserDetailsService userDetailsService() {
          UserDetails admin = User.builder().username("admin").password(
                  passwordEncoder().encode("abhikshaa")).roles("ADMIN").build();
          UserDetails user = User.builder().username("user").password(
                  passwordEncoder().encode("hello")).roles("USER").build();
          return new InMemoryUserDetailsManager(admin,user);
     }
}
