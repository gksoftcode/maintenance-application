package com.gksoft.maintenance.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.gksoft.maintenance.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.gksoft.maintenance.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.gksoft.maintenance.domain.User.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Authority.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.User.class.getName() + ".authorities");
            createCache(cm, com.gksoft.maintenance.domain.Region.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Country.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Location.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Department.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Department.class.getName() + ".employees");
            createCache(cm, com.gksoft.maintenance.domain.Task.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Task.class.getName() + ".jobs");
            createCache(cm, com.gksoft.maintenance.domain.Employee.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Employee.class.getName() + ".jobs");
            createCache(cm, com.gksoft.maintenance.domain.Job.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Job.class.getName() + ".tasks");
            createCache(cm, com.gksoft.maintenance.domain.JobHistory.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Client.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Client.class.getName() + ".appintments");
            createCache(cm, com.gksoft.maintenance.domain.Appintment.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Actions.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.ItemModels.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.ItemBrand.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.Status.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.WorkOrders.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.WorkOrders.class.getName() + ".assignedStaffs");
            createCache(cm, com.gksoft.maintenance.domain.WorkOrders.class.getName() + ".workOrdersHistories");
            createCache(cm, com.gksoft.maintenance.domain.WorkOrdersHistory.class.getName());
            createCache(cm, com.gksoft.maintenance.domain.WorkOrdersHistory.class.getName() + ".attachments");
            createCache(cm, com.gksoft.maintenance.domain.Attachments.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
