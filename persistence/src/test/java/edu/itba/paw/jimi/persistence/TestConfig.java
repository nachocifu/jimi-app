package edu.itba.paw.jimi.persistence;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan({"edu.itba.paw.jimi.persistence"})
@Configuration
public class TestConfig {
	
	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(JDBCDriver.class);
		ds.setUrl("jdbc:hsqldb:mem:paw");
		ds.setUsername("ha");
		ds.setPassword("");
		return ds;
	}
	
	@Bean(name = "testName")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("edu.itba.paw.jimi.models");
		
		factoryBean.setDataSource(dataSource());
		
		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");

//        properties.setProperty("hibernate.show_sql", "true");
//        properties.setProperty("format_sql", "true");
		
		factoryBean.setJpaProperties(properties);
		return factoryBean;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
}
