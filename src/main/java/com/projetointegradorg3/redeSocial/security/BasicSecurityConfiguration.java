package com.projetointegradorg3.redeSocial.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {
   

	private @Autowired UserDetailsServiceImplements service;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(service);

		auth.inMemoryAuthentication().withUser("root").password(passwordEncoder().encode("root"))
				.authorities("ROLE_ADMIN");
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
        .antMatchers("/**").permitAll()
        .antMatchers("/usuarios/logar").permitAll()
        .antMatchers("/usuarios/cadastrar").permitAll()
        .antMatchers(HttpMethod.POST ,"/postagem").permitAll()
        .antMatchers(HttpMethod.POST ,"/tema").permitAll()
        .antMatchers(HttpMethod.GET ,"/postagem").permitAll()
        .antMatchers(HttpMethod.GET ,"/tema").permitAll()
	.antMatchers(HttpMethod.PUT ,"/postagem").permitAll()
        .antMatchers(HttpMethod.PUT ,"/tema").permitAll()
        .anyRequest().authenticated()
        .and().httpBasic()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().cors()
        .and().csrf().disable();
    }
}
