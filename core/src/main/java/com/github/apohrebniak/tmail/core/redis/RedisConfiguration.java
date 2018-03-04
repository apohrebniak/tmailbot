package com.github.apohrebniak.tmail.core.redis;

import com.google.common.primitives.Longs;
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
  @Value("${tmail.redis.ttl}")
  private Integer ttlMinutes;

  @Bean
  public RedisTemplate<String, Long> redisTemplate() {
    RedisTemplate<String, Long> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new LongRedisSerializer());
    return template;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  @Bean
  public RedisRecipientRegistry redisRecipientRegistry(RedisTemplate redisTemplate) {
    return new RedisRecipientRegistry(ttlMinutes, redisTemplate);
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
