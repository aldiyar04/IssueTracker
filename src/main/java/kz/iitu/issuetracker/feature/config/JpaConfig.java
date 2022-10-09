package kz.iitu.issuetracker.feature.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
@Profile(AppProfile.DEVELOPMENT)
@EnableTransactionManagement
public class JpaConfig {
    private final ConfigurableEnvironment env;

    private boolean isDevProfile;
    private boolean isTestProfile;

    @Autowired
    public JpaConfig(ConfigurableEnvironment env) {
        this.env = env;
    }

    @PostConstruct
    public void initProfiles() {
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        isDevProfile = activeProfiles.contains(AppProfile.DEVELOPMENT);
        isTestProfile = activeProfiles.contains(AppProfile.TEST);

        if (isDevProfile && isTestProfile) {
            throw new IllegalStateException(String.format("Both %s and %s profiles are set. Datasource configuration " +
                    "needs either one or the other.", AppProfile.DEVELOPMENT, AppProfile.TEST));
        } else if (!isDevProfile && !isTestProfile) {
            throw new IllegalStateException(String.format("No valid profile is set. Datasource configuration needs " +
                    "either %s or %s profile to be set.", AppProfile.DEVELOPMENT, AppProfile.TEST));
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
        transactionManager.setJpaDialect(isolationSupportJpaDialect());
        return transactionManager;
    }

    @Bean(name="entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setPackagesToScan("kz.iitu.issuetracker.entity");
        factoryBean.setJpaDialect(isolationSupportJpaDialect());
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        if (isDevProfile) {
            setAsPostgreSQL(dataSource);
        } else if (isTestProfile) {
            setAsH2(dataSource);
        }
        return dataSource.build();
    }

    private void setAsPostgreSQL(DataSourceBuilder dataSource) {
        dataSource.driverClassName("org.postgresql.Driver");
        dataSource.url("jdbc:postgresql://localhost:5432/issenbayev");
        dataSource.username("postgres");
        dataSource.password("pass");
    }

    private void setAsH2(DataSourceBuilder dataSource) {
        dataSource.driverClassName("org.h2.Driver");
        dataSource.url("jdbc:h2:mem:issenbayev;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
        dataSource.username("sa");
        dataSource.password("pass");
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();

        props.put("hibernate.dll-auto", "none"); // DB is initiated using schema.sql and data.sql

        // Caching
        props.put("hibernate.cache.use_second_level_cache", true);
        props.put("hibernate.cache.use_query_cache", true);
        props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.JCacheRegionFactory");

        // Fetching
        props.put("hibernate.jdbc.batch_size", 10);
        props.put("hibernate.jdbc.fetch_size", 50);

        // Show SQL
        props.put("hibernate.format_sql", true);
        props.put("hibernate.use_sql_comments", true);
        props.put("hibernate.show_sql", true);

        return props;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private JpaDialect isolationSupportJpaDialect() {
        return new IsolationSupportJpaDialect();
    }
}
