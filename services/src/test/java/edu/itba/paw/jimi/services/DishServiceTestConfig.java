package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.DishDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@ComponentScan("edu.itba.paw.jimi.services")
@Profile("test")
@Configuration
public class DishServiceTestConfig {

    @Bean
    @Primary
    public DishDao dishDao() {
        return Mockito.mock(DishDao.class);
    }

}
