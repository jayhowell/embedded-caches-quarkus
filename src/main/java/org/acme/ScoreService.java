package org.acme;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import org.infinispan.context.Flag;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;

@ApplicationScoped
public class ScoreService {

    Cache<Object, Score> scoreCache; 

    Logger log = LoggerFactory.getLogger(ScoreService.class); 

    EmbeddedCacheManager cacheManager; 

    public List<Score> getAll() { 
        return new ArrayList<>(scoreCache.values());
    }

    public List<Score> getLambdaList() { 
        //Default functionality where entryset.stream() is a remote operation....
        return scoreCache.entrySet().stream()
                     .filter(entry -> entry.getValue().matches("Jay")) // Filter based on the value
                     .map(Map.Entry::getValue)                        // Extract the values (Score)
                     .collect(Collectors.toList());   
        
        //Serialization going local
        /*return scoreCache.getAdvancedCache()
        .withFlags(Flag.CACHE_MODE_LOCAL)   // Ensure the operation is local
        .entrySet()
        .stream()
        .filter(entry -> entry.getValue().matches("Jay")) // Filter based on the Score object
        .map(Map.Entry::getValue)                        // Extract the Score values
        .collect(Collectors.toList());*/
        
        /*Map<Object, Score> deepCopiedMap = new HashMap<>();
        //add each entry into the deep copy.
        for (Map.Entry<Object, Score> entry : scoreCache.entrySet()) {
            // Clone key and value (if mutable)
            deepCopiedMap.put(entry.getKey(), entry.getValue());
        }
        return    // Ensure the operation is local
        deepCopiedMap.entrySet()
        .stream()
        .filter(entry -> entry.getValue().matches("Jay")) // Filter based on the Score object
        .map(Map.Entry::getValue)                        // Extract the Score values
        .collect(Collectors.toList());*/

        
    }

    public void save(Score entry) { 
        scoreCache.put(getKey(entry), entry);
    }

    public void delete(Score entry) { 
        scoreCache.remove(getKey(entry));
    }

    public void getEntry(Score entry){ 
        scoreCache.get(getKey(entry));
    }

    public static String getKey(Score entry){
        return entry.getPlayerId()+","+entry.getCourse();
    }

    public Score findById(String key) {
        return scoreCache.get(key);
    }

    void onStart(@Observes @Priority(value = 1) StartupEvent ev){
        System.setProperty("jgroups.dns.query", "jcache-quarkus-ping");

        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global.transport()
            .clusterName("ScoreCard")
            .addProperty("configurationFile", "default-configs/default-jgroups-kubernetes.xml");
        cacheManager = new DefaultCacheManager(global.build());

        ConfigurationBuilder config = new ConfigurationBuilder();

        config.expiration().lifespan(30, TimeUnit.MINUTES)
                .clustering().cacheMode(CacheMode.DIST_ASYNC)
                .hash()
                .numOwners(2);

        cacheManager.defineConfiguration("scoreboard", config.build());
        scoreCache = cacheManager.getCache("scoreboard");
        scoreCache.addListener(new CacheListener());


        log.info("Cache initialized");

    }
 
}