package edu.itba.paw.jimi.webapp.config;

import edu.itba.paw.jimi.webapp.auth.StatelessAuthenticationFilter;
import edu.itba.paw.jimi.webapp.auth.StatelessLoginSuccessHandler;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Base64;

@Configuration
@EnableWebSecurity
@ComponentScan("edu.itba.paw.jimi.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private StatelessLoginSuccessHandler statelessLoginSuccessHandler;

	@Autowired
	private StatelessAuthenticationFilter statelessAuthenticationFilter;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService).sessionManagement()
				.and()
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
				.and().authorizeRequests()
				.antMatchers("/api/dishes/**").hasRole("ADMIN")
				.antMatchers("/api/admin/**").hasRole("ADMIN")
				.antMatchers("/api/tables/**").hasAnyRole("ADMIN", "USER")
				.antMatchers("/api/users/**").hasRole("ADMIN")
				.antMatchers("/api/kitchen/**").authenticated()
				.antMatchers("/api/**").authenticated()
				.anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.formLogin().usernameParameter("username").passwordParameter("password").loginProcessingUrl("/api/login")
				.successHandler(statelessLoginSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler())
				.and()
				.addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers( "/resources/css/**", "/resources/js/**", "/resources/img/**", "/resources/plugins/**", "/favicon.ico", "/error/403");
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

	@Bean
	public String tokenSigningKey() {
		return Base64.getEncoder().encodeToString("E3E4B1AFE1B1457AAC1CAF95E8AD5888DF00E6A63E48B9CE2F241B59CAD955D1052415930B3B1ECFBE82BC4A9B4328E50DE23D3A129070DD8D7F2DC78F0F130F".getBytes());
	}

}
