package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.UserDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@ComponentScan("edu.itba.paw.jimi.services")
@Configuration
public class DishServiceTestConfig {
	
	@Bean
	@Primary
	public DishDao dishDao() {
		return Mockito.mock(DishDao.class);
	}
	
	@Bean
	@Primary
	public UserDao userDao() {
		return Mockito.mock(UserDao.class);
	}
	
	
}
