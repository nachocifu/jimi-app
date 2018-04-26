package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.DishDao;
import edu.itba.paw.jimi.interfaces.UserDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

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
