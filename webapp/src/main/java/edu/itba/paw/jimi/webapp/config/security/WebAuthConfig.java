package edu.itba.paw.jimi.webapp.config.security;

import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("edu.itba.paw.jimi.webapp.config")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	
	private static final String KEY = "QH8wJ+cEvrlVJFebOQcGNEDan0N4KzjkNxM6ODOXxGc=";
	
	@Autowired
	private PawUserDetailsService userDetailsService;
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService).sessionManagement()
				.invalidSessionUrl("/login").and().authorizeRequests()
				.antMatchers("/login").anonymous()
				.antMatchers("/admin/**").hasRole(User.ADMIN)
				.antMatchers("/**").authenticated().and()
				.formLogin().usernameParameter("j_username").passwordParameter("j_password").defaultSuccessUrl("/", false).loginPage("/login").and()
				.rememberMe().rememberMeParameter("j_rememberme").userDetailsService(userDetailsService).key(KEY)
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)).and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login").and()
				.exceptionHandling().accessDeniedPage("/error/403").and()
				.csrf().disable();
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**", "/resources/css/**", "/resources/js/**", "/resources/img/**", "/resources/plugins/**", "/favicon.ico", "/error/403");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
}
