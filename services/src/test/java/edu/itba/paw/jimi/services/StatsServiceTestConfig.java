package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.daos.UserDao;
import edu.itba.paw.jimi.interfaces.services.DishService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@ComponentScan("edu.itba.paw.jimi.services")
@Configuration
public class StatsServiceTestConfig {

    @Bean
    @Primary
    public DishServiceImpl dishService() {
        return Mockito.mock(new DishServiceImpl());
    }

    @Bean
    @Primary
    public TableServiceImpl tableService() {
        return Mockito.mock(TableServiceImpl.class);
    }

}
