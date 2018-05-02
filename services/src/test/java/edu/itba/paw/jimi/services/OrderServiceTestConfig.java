package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@ComponentScan("edu.itba.paw.jimi.services")
@Configuration
public class OrderServiceTestConfig {
    @Bean
    @Primary
    public OrderDao orderDao() {
        return Mockito.mock(OrderDao.class);
    }
}
