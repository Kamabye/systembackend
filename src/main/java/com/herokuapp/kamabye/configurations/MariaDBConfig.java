package com.herokuapp.kamabye.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Profile("mariadb")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.domain.system.repository.mariadb", entityManagerFactoryRef = "entityManagerFactoryMariaDB", transactionManagerRef = "transactionManagerMariaDB")
public class MariaDBConfig {

	// @Primary
	@Bean
	@ConfigurationProperties("mariadb.datasource")
	public DataSourceProperties mariadbDataSourceProperties() {
		return new DataSourceProperties();
	}

	// @Primary
	@Bean(name = "dataSourceMariaDB")
	@ConfigurationProperties(prefix = "mariadb.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
		// return mariadbDataSourceProperties().initializeDataSourceBuilder().build();
	}

	// @Primary
	@Bean(name = "entityManagerFactoryMariaDB")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSourceMariaDB") DataSource dataSource) {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MariaDBDialect");
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory = builder.dataSource(dataSource).packages("com.domain.system.models.mariadb").persistenceUnit("mariadb")
				.build();

		factory.setJpaVendorAdapter(vendorAdapter);

		return factory;
	}

	// @Primary
	@Bean(name = "transactionManagerMariaDB")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactoryMariaDB") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
