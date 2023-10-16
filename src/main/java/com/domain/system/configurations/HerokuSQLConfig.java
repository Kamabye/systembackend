package com.domain.system.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
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
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@Profile("herokusql")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.domain.system.repository.heroku", entityManagerFactoryRef = "entityManagerFactoryHerokuSQL", transactionManagerRef = "transactionManagerHerokuSQL")
public class HerokuSQLConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties("heroku.datasource")
	public DataSourceProperties postgreDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean(name = "dataSourceHeroku")
	@ConfigurationProperties(prefix = "heroku.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
		// return postgreDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Primary
	@Bean(name = "entityManagerFactoryHerokuSQL")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSourceHeroku") DataSource dataSource) {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory = builder.dataSource(dataSource).packages("com.domain.system.models.heroku")
				.persistenceUnit("herokusql").build();

		factory.setJpaVendorAdapter(vendorAdapter);

		return factory;
	}

	@Primary
	@Bean(name = "transactionManagerHerokuSQL")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactoryHerokuSQL") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
