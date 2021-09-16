package com.example.demo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration		//cette classe s'execute au début
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	//indiquer à spring comment chercher les utilisateurs
		PasswordEncoder passwordEncoder = passwordEncoder() ;
		System.out.println("********************");
		System.out.println(passwordEncoder.encode("1234"));
		System.out.println("********************");
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username as principal , password as credentials , active from users where username=?")
			.authoritiesByUsernameQuery("select username as principal , role as role from users_roles where username=?")
			.passwordEncoder(passwordEncoder)
			.rolePrefix("ROLE_");
		/*auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("1234")).roles("USER");
		auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("1234")).roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("1234")).roles("USER" , "ADMIN");*/
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");		//http.formLogin().loginPage("/login");
		//http.httpBasic();
		http.authorizeRequests().antMatchers("/admin/**","/save**/**" , "delete**/**" , "/form**/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/produits").hasRole("USER");
		http.authorizeRequests().antMatchers("/user/**" , "/login" , "/webjars/**").permitAll(); //améliorer sécurité (interdir toutes les ressources sauf ce qui commence par user)
		http.authorizeRequests().anyRequest().authenticated();	//toutes les requetes nécessite une authentification
		http.csrf();	//activer le mécanisme de parade contre les attaques de type csrf
		//http.csrf().disabled(); désactiver
		http.exceptionHandling().accessDeniedPage("/notAuthorized");	//à chaque fois un uuser demande une ressource dont il n'a pas le droit d'y acceder tu vas vers cette page
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
