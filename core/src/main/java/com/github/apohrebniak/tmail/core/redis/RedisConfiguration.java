package com.github.apohrebniak.tmail.core.redis;

import com.google.common.primitives.Longs;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

@Configuration
public class RedisConfiguration {

  @Value("${tmail.redis.host}")
  private String redisHost;
  @Value("${tmail.redis.port}")
  private Integer redisPort;
  @Value("${tmail.core.ttl}")
  private Integer ttlMinutes;

  @Bean(name = "stringLongRedis")
  public RedisTemplate<String, Long> redisStringLongTemplate() {
    RedisTemplate<String, Long> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new LongRedisSerializer());
    return template;
  }

  @Bean(name = "longStringRedis")
  public RedisTemplate<Long, String> redisLongStringTemplate() {
    RedisTemplate<Long, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    template.setValueSerializer(new StringRedisSerializer());
    template.setKeySerializer(new LongRedisSerializer());
    return template;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  @Bean
  public RedisMailboxRegistry redisRecipientRegistry(
      @Qualifier("stringLongRedis") RedisTemplate redisTemplate) {
    return new RedisMailboxRegistry(ttlMinutes, redisTemplate);
  }

  @Bean
  public RedisUserRegistry redisUserRegistry(
      @Qualifier("longStringRedis") RedisTemplate redisTemplate) {
    return new RedisUserRegistry(redisTemplate);
  }

  static class LongRedisSerializer implements RedisSerializer<Long> {

    @Nullable
    @Override
    public byte[] serialize(@Nullable Long aLong) throws SerializationException {
      return Longs.toByteArray(aLong);
    }

    @Nullable
    @Override
    public Long deserialize(@Nullable byte[] bytes) throws SerializationException {
      return bytes == null
          ? null
          : Longs.fromByteArray(bytes);
    }
  }
}
