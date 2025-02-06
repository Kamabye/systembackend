package com.system.domain.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@Profile("db1")
@EnableTransactionManagement // Se habilitan las transacciones en la base de datos
@EnableJpaRepositories(basePackages = "com.system.domain.repository.postgresql", entityManagerFactoryRef = "db1EntityManagerFactory", transactionManagerRef = "db1TransactionManager")
@Primary
public class DB1Config {
	
	@Bean(name = "db1DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.db1")
	DataSource db1DataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "db1EntityManagerFactory")
	LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
	  EntityManagerFactoryBuilder builder,
	  @Qualifier("db1DataSource") DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory = builder.dataSource(dataSource).packages("com.system.domain.models.postgresql")
		  .persistenceUnit("db1").build();
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		// vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect"); //
		// Opcional, se puede configurar en application.properties
		// vendorAdapter.setShowSql(true); // Opcional
		factory.setJpaVendorAdapter(vendorAdapter);
		
		// Properties jpaProperties = new Properties();
		// jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update"); // Opcional
		// factory.setJpaProperties(jpaProperties);
		// factory.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
		// factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		
		return factory;
	}
	
	@Bean(name = "db1TransactionManager")
	PlatformTransactionManager db1TransactionManager(
	  @Qualifier("db1EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
