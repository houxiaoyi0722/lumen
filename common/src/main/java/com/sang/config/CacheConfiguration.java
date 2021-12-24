//package com.sang.config;
//
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//import java.lang.reflect.Method;
//import java.time.Duration;
//
///**
// *
// * @author hxy
// */
//@Configuration
//public class CacheConfiguration extends CachingConfigurerSupport {
//
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//
//        // 初始化一个RedisCacheWriter
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//        // 设置CacheManager的值序列化方式为json序列化
//        // GenericJackson2JsonRedisSerializer序列化器会保存类型信息
//        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
//
//        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
//                .fromSerializer(jsonSerializer);
//
//        // 设置默认超过期时间是半小时
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration
//                .defaultCacheConfig()
//                //该方法会返回一个新的实例
//                .entryTtl(Duration.ofMinutes(30))
//                .serializeValuesWith(pair);
//
//        // 开启使用缓存名称做为key前缀(这样所有同名缓存会整理在一起比较容易查找)
//        defaultCacheConfig.usePrefix();
//
//        // 初始化RedisCacheManager
//        return RedisCacheManager
//                .builder(redisCacheWriter)
//                .cacheDefaults(defaultCacheConfig)
//                .transactionAware()
//                .build();
//    }
//
//
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append("CacheKey:");
//                sb.append(target.getClass().getSimpleName());
//                sb.append('.').append(method.getName()).append(":");
//
//                // 这里需要注意，参数太多的话考虑使用其他拼接方式
//                for (Object obj : params) {
//                    if (obj != null) {
//                        sb.append(obj.toString());
//                        sb.append(".");
//                    }
//                }
//                return sb.toString();
//            }
//        };
//    }
//}
