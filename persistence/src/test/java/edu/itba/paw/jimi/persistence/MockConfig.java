package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import org.hsqldb.jdbc.JDBCDriver;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@ComponentScan({ "edu.itba.paw.jimi.persistence" })
@Configuration
public class  MockConfig {

    @Bean
    @Primary
    public OrderDao orderDao() {
        return Mockito.mock(OrderDao.class);
    }
    @Bean
    @Primary
    public DishDao dishDao() {
        return Mockito.mock(DishDao.class);
    }

}
