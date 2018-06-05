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
	
	private static final String KEY = "LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFb3dJQkFBS0NBUUVBd" +
			"yt2S0dvajlnVjVqQmZ6cmtON0ZBU3ovbzc3VlU3YzlJM3J1TGVQNHpOZ2RNT0kyCjdhODVTd0h1NjdlYmVRU281cXJQ" +
			"SGx0MmNLeGdIdUdSZTA5RUFqbGdGRzNuWElBTEEvRWdOdXpJeFIxOW44Y0cKaldxZWQ0dTk3a2ZuMmhTN3NDd0ZHNlU" +
			"ybDQ4T1pxeE81MnRONS9CSjc1SS8rdTl0cUpBRWNneXlxTE5QWnUwUQpoT1lOcG5xeEJoNEt0d2tvbnR3YTVuSnRTTW" +
			"FOZEdSeWh3Y0R5c2FyWTU0elZBUzIyTjA5WUFsV2I5T3VjL1JSCm9GRWk0VVlyc01xTnBHamdSWDdJaUY1emRpUVY3S" +
			"WNaRXNrdkovdEJjQWJwRXA3dlJWckhkdy8wYkw0QXhaWFQKdWFJUVRNZTk4aHJLQ1czUmU4aUQwNTZTVnE4dmZodzJz" +
			"cGRVM3dJREFRQUJBb0lCQUhWcFdlMzdDTWs5c21SRgpSVHJGZGhTVlRnSm9lOW0ycDJvYTRxQVh6ZU9jNUNtR2Q5cUx" +
			"OdStQa2dvck4zNFgzOHlVc3BrRHdRQVhIV2VFClVMWXdNNWFOb2RtOFgzbWN0bjAvUk1vRnJ2QUtFMTNzWFMza3BtRU" +
			"lHdU93cmU2UW5FWWdxMUw4K0xGdEp2azYKNi9KZDZRdEZnbG1UTU1xR01CWmpjSGFVeTRDekg3TXZybjUzNHVaeW9WS" +
			"HNLVU1SYjlEbUdJR05jbUJPcEVwcgptZllxaWM2V0NxUS9kOW1BZU5BdytqQTBpdE1NRDJKN3VCK0pidXRKbHgvUjQ4" +
			"bXE1Y28zeVBCVjFoTW1VQjRKCnpKWmF1M05kMHFZUDNqT09wZjhhRHFuVkJmVTFHSytpVFNsbTVjTnRSN0JPMGljTkM" +
			"1UTdTMkpiTEVIUllkc1UKOFZMRlhKRUNnWUVBN3hiOE5Fc2xOMVVPMlFsTGpDcEZtcTdFcjVWWUY3Yjlsc2I2KzFFem" +
			"hDbTZGTEVCRnJKaApGV2Q4VlN1T1J4L3doMUlCTVBDQUtGSU5PcEZtNFNQMzU4Z0Q1azBlNGNpK1dSTlNydjVrRDROS" +
			"TAyQ1duelcwClppYzdrK2t0aDFCUTh4Q0kyRDJaVC9yYUN2UmgvS3JVWU9SZFZrbFhQY0dKVmZ5ZytPREh5OGtDZ1lF" +
			"QTBjY3UKWm0zRCs2aUd1REhzSXJBdHpRZ0IwbXhOcWtManFoMXA5V1BUOUhxZ0x4aXpPOWpUZ1J2TDlXSXN0SDJ2UFZ" +
			"SeAo4TkRkdnF3dmkrdFYyNkY2THpDTWxnSHNPQW81NjhiYlVnSFY5ZDR4RlhYQUlsQ0szWURPWW0xS0R6dzZSeVdYCj" +
			"BFY1dRWG1TVWhOVzJEZHY1VVZiS0tkZzVhYUNEUVdycm9IUEgyY0NnWUVBcWtjeGVjRG5FWUJYb2NWWDg4eUwKWS80Q" +
			"2dKYVpkZEpTeXhhTjIzMWFkazhOZ3YwSkxIUUFackVjUU1CVGhWNCsvUEV2ZmY1YkY5eFFKWjAycXpQbAp0RUtrWEZv" +
			"aW0yVlFBN3FhVVoxR0FESmRKSVlPMy9ic3N6QlA0eWpYeng0cGVPTVd3d01qOUcxUTZ5cGZ1d2FqCldScHJTeS9TblY" +
			"0QUJLaDJzS25PUWRrQ2dZQTlBbVNvUFh3bUVKazlzS2N3WDNTeWtJV1hJUkJsV0JiR1BzKzIKSWxaZVg5dCtWdnlwaH" +
			"lIY0JxMkdyQXE4WTJFM3dQdHlRZ3g3aTdBNFRUVW5Md3ROOWh5czg5b3pZaVgrUUZOQQp3bS9ybGJkSXVub3FnS29pU" +
			"1hlOGdIUnRnZUtoRm5uVWhSZ2Y2cTkzVjFDR2lYanRnUUpHRElmTndtTklUa0hqCityUzJPUUtCZ0Y0TUFVa3RiUHZ0" +
			"TTk2T0tMZTVsUFBuUG13eXdSRy9xdVFIK3NlTDVpT1VYaklJT0pMZkRzQUwKbXI5VDErSGoyTnNTZHZVT2NNa3czYkY" +
			"2V3R6MldUditGNDVnNUVGREsyL0FHNkJBMGozb0V3aWl4aXdDdURQUQp6SEJsOEFSZ24wbzgzSXhWZUtCNytCbzZKbE" +
			"dKRFI5QnZCRjFQajZlcHdOTVZSUTZzOSs1Ci0tLS0tRU5EIFJTQSBQUklWQVRFIEtFWS0tLS0tCg==";
	
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
