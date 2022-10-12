package com.sang.common.config.cache;

import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;

public class FuzzyDeleteRedisCacheManager extends RedisCacheManager {


    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfiguration defaultCacheConfig;
    private final Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
    private boolean enableTransactions;


    public FuzzyDeleteRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public FuzzyDeleteRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public FuzzyDeleteRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, boolean allowInFlightCacheCreation, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public FuzzyDeleteRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public FuzzyDeleteRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    /**
     * 这个构造方法最重要
     **/
    public FuzzyDeleteRedisCacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfiguration cacheConfiguration) {
        this(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),cacheConfiguration);
    }

    /**
     * 覆盖父类创建RedisCache
     */
    @Override
    protected FuzzyDeleteRedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new FuzzyDeleteRedisCache(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfig);
    }

    @Override
    public Map<String, RedisCacheConfiguration> getCacheConfigurations() {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(getCacheNames().size());
        getCacheNames().forEach(it -> {
            RedisCache cache = FuzzyDeleteRedisCache.class.cast(lookupCache(it));
            configurationMap.put(it, cache != null ? cache.getCacheConfiguration() : null);
        });
        return Collections.unmodifiableMap(configurationMap);
    }

    public static FuzzyDeleteRedisCacheManagerBuilder fuzzyBuilder() {
        return new FuzzyDeleteRedisCacheManagerBuilder();
    }

    public static FuzzyDeleteRedisCacheManagerBuilder fuzzyBuilder(RedisConnectionFactory connectionFactory) {

        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");

        return FuzzyDeleteRedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);
    }

    public static FuzzyDeleteRedisCacheManagerBuilder fuzzyBuilder(RedisCacheWriter cacheWriter) {

        Assert.notNull(cacheWriter, "CacheWriter must not be null!");

        return FuzzyDeleteRedisCacheManagerBuilder.fromCacheWriter(cacheWriter);
    }

    /**
     * Configurator for creating {@link FuzzyDeleteRedisCacheManager}.
     */
    public static class FuzzyDeleteRedisCacheManagerBuilder {

        private @Nullable RedisCacheWriter cacheWriter;
        private CacheStatisticsCollector statisticsCollector = CacheStatisticsCollector.none();
        private RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        private final Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
        private boolean enableTransactions;
        boolean allowInFlightCacheCreation = true;

        private FuzzyDeleteRedisCacheManagerBuilder() {}

        private FuzzyDeleteRedisCacheManagerBuilder(RedisCacheWriter cacheWriter) {
            this.cacheWriter = cacheWriter;
        }

        /**
         * Entry point for builder style {@link RedisCacheManager} configuration.
         *
         * @param connectionFactory must not be {@literal null}.
         * @return new {@link FuzzyDeleteRedisCacheManagerBuilder}.
         */
        public static FuzzyDeleteRedisCacheManagerBuilder fromConnectionFactory(RedisConnectionFactory connectionFactory) {

            Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");

            return new FuzzyDeleteRedisCacheManagerBuilder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory));
        }

        /**
         * Entry point for builder style {@link RedisCacheManager} configuration.
         *
         * @param cacheWriter must not be {@literal null}.
         * @return new {@link FuzzyDeleteRedisCacheManagerBuilder}.
         */
        public static FuzzyDeleteRedisCacheManagerBuilder fromCacheWriter(RedisCacheWriter cacheWriter) {

            Assert.notNull(cacheWriter, "CacheWriter must not be null!");

            return new FuzzyDeleteRedisCacheManagerBuilder(cacheWriter);
        }

        /**
         * Define a default {@link RedisCacheConfiguration} applied to dynamically created {@link RedisCache}s.
         *
         * @param defaultCacheConfiguration must not be {@literal null}.
         * @return this {@link FuzzyDeleteRedisCacheManagerBuilder}.
         */
        public FuzzyDeleteRedisCacheManagerBuilder cacheDefaults(RedisCacheConfiguration defaultCacheConfiguration) {

            Assert.notNull(defaultCacheConfiguration, "DefaultCacheConfiguration must not be null!");

            this.defaultCacheConfiguration = defaultCacheConfiguration;

            return this;
        }

        /**
         * Configure a {@link RedisCacheWriter}.
         *
         * @param cacheWriter must not be {@literal null}.
         * @return this {@link FuzzyDeleteRedisCacheManagerBuilder}.
         * @since 2.3
         */
        public FuzzyDeleteRedisCacheManagerBuilder cacheWriter(RedisCacheWriter cacheWriter) {

            Assert.notNull(cacheWriter, "CacheWriter must not be null!");

            this.cacheWriter = cacheWriter;

            return this;
        }

        /**
         * Enable {@link RedisCache}s to synchronize cache put/evict operations with ongoing Spring-managed transactions.
         *
         * @return this {@link FuzzyDeleteRedisCacheManagerBuilder}.
         */
        public FuzzyDeleteRedisCacheManagerBuilder transactionAware() {

            this.enableTransactions = true;

            return this;
        }

        /**
         * Append a {@link Set} of cache names to be pre initialized with current {@link RedisCacheConfiguration}.
         * <strong>NOTE:</strong> This calls depends on {@link #cacheDefaults(RedisCacheConfiguration)} using whatever
         * default {@link RedisCacheConfiguration} is present at the time of invoking this method.
         *
         * @param cacheNames must not be {@literal null}.
         * @return this {@link FuzzyDeleteRedisCacheManagerBuilder}.
         */
        public FuzzyDeleteRedisCacheManagerBuilder initialCacheNames(Set<String> cacheNames) {

            Assert.notNull(cacheNames, "CacheNames must not be null!");

            cacheNames.forEach(it -> withCacheConfiguration(it, defaultCacheConfiguration));
            return this;
        }

        /**
         * Append a {@link Map} of cache name/{@link RedisCacheConfiguration} pairs to be pre initialized.
         *
         * @param cacheConfigurations must not be {@literal null}.
         * @return this {@link FuzzyDeleteRedisCacheManagerBuilder}.
         */
        public FuzzyDeleteRedisCacheManagerBuilder withInitialCacheConfigurations(
                Map<String, RedisCacheConfiguration> cacheConfigurations) {

            Assert.notNull(cacheConfigurations, "CacheConfigurations must not be null!");
            cacheConfigurations.forEach((cacheName, configuration) -> Assert.notNull(configuration,
                    String.format("RedisCacheConfiguration for cache %s must not be null!", cacheName)));

            this.initialCaches.putAll(cacheConfigurations);
            return this;
        }

        /**
         * @param cacheName
         * @param cacheConfiguration
         * @return this {@link FuzzyDeleteRedisCacheManagerBuilder}.
         * @since 2.2
         */
        public FuzzyDeleteRedisCacheManagerBuilder withCacheConfiguration(String cacheName,
                                                                                 RedisCacheConfiguration cacheConfiguration) {

            Assert.notNull(cacheName, "CacheName must not be null!");
            Assert.notNull(cacheConfiguration, "CacheConfiguration must not be null!");

            this.initialCaches.put(cacheName, cacheConfiguration);
            return this;
        }

        /**
         * Disable in-flight {@link org.springframework.cache.Cache} creation for unconfigured caches.
         * <p>
         * {@link RedisCacheManager#getMissingCache(String)} returns {@literal null} for any unconfigured
         * {@link org.springframework.cache.Cache} instead of a new {@link RedisCache} instance. This allows eg.
         * {@link org.springframework.cache.support.CompositeCacheManager} to chime in.
         *
         * @return this {@link FuzzyDeleteRedisCacheManagerBuilder}.
         * @since 2.0.4
         */
        public FuzzyDeleteRedisCacheManagerBuilder disableCreateOnMissingCache() {

            this.allowInFlightCacheCreation = false;
            return this;
        }

        /**
         * Get the {@link Set} of cache names for which the builder holds {@link RedisCacheConfiguration configuration}.
         *
         * @return an unmodifiable {@link Set} holding the name of caches for which a {@link RedisCacheConfiguration
         *         configuration} has been set.
         * @since 2.2
         */
        public Set<String> getConfiguredCaches() {
            return Collections.unmodifiableSet(this.initialCaches.keySet());
        }

        /**
         * Get the {@link RedisCacheConfiguration} for a given cache by its name.
         *
         * @param cacheName must not be {@literal null}.
         * @return {@link Optional#empty()} if no {@link RedisCacheConfiguration} set for the given cache name.
         * @since 2.2
         */
        public Optional<RedisCacheConfiguration> getCacheConfigurationFor(String cacheName) {
            return Optional.ofNullable(this.initialCaches.get(cacheName));
        }

        /**
         * @return
         * @since 2.4
         */
        public FuzzyDeleteRedisCacheManagerBuilder enableStatistics() {

            this.statisticsCollector = CacheStatisticsCollector.create();
            return this;
        }

        /**
         * Create new instance of {@link FuzzyDeleteRedisCacheManager} with configuration options applied.
         *
         * @return new instance of {@link FuzzyDeleteRedisCacheManager}.
         */
        public FuzzyDeleteRedisCacheManager build() {

            Assert.state(cacheWriter != null,
                    "CacheWriter must not be null! You can provide one via 'RedisCacheManagerBuilder#cacheWriter(RedisCacheWriter)'.");

            RedisCacheWriter theCacheWriter = cacheWriter;

            if (!statisticsCollector.equals(CacheStatisticsCollector.none())) {
                theCacheWriter = cacheWriter.withStatisticsCollector(statisticsCollector);
            }

            FuzzyDeleteRedisCacheManager cm = new FuzzyDeleteRedisCacheManager(theCacheWriter, defaultCacheConfiguration, initialCaches,
                    allowInFlightCacheCreation);

            cm.setTransactionAware(enableTransactions);

            return cm;
        }
    }

}
