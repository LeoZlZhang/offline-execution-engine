package leo.webapplication.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by leozhang on 9/10/16.
 * Cache repository
 */

@Repository
public class RedisRepository {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public String helloRedis(@RequestParam("name") String name) {
        String value = stringRedisTemplate.opsForValue().get(name);
        return String.format("debug: %s", value);
    }
}
