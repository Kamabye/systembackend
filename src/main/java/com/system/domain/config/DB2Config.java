package com.system.domain.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@Profile("db2")
@EnableTransactionManagement // Se habilitan las transacciones en la base de datos
@EnableJpaRepositories(basePackages = "com.system.domain.repository.postgresql", entityManagerFactoryRef = "db2EntityManagerFactory", transactionManagerRef = "db2TransactionManager")
public class DB2Config {
	
	@Bean(name = "db2DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.db2")
	DataSource db2DataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "db2EntityManagerFactory")
	LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
	  EntityManagerFactoryBuilder builder,
	  @Qualifier("db2DataSource") DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory = builder.dataSource(dataSource).packages("com.system.domain.model.postgresql")
		  .persistenceUnit("db2").build();
		
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
	
	@Bean(name = "db2TransactionManager")
	PlatformTransactionManager db2TransactionManager(
	  @Qualifier("db2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
