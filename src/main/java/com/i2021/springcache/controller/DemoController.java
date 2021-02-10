package com.i2021.springcache.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author demo
 * @date 2021-02-10
 */
@RequestMapping("test")
@RestController
public class DemoController {

    @Cacheable(value = "testCache")
    @GetMapping("cache")
    public String testCacheable() {
        return "cacheable" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Cacheable(value = "testCache2", key = "#id")
    @GetMapping("cache2")
    public String testCacheable(Integer id) {
        return "cacheable" + id;
    }

    /**
     * CacheEvict失效模式：执行完方法体之后，将缓存删除，等待下一次执行@Cacheable标注的方法时，
     * 再将缓存存入Redis中
     * @param id
     * @return
     */
    @CacheEvict(value = "testCache2", key="#id")
    @GetMapping("updateCache2")
    public String updateCacheable(Integer id) {
        return "update Cache.., cacheEvict";
    }

    /**
     *  CachePut双写模式：执行完方法体之后，修改缓存中的数据为方法的返回值
     *
     * @param id
     * @return
     */
    @CachePut(value = "testCache2", key = "#id")
    @GetMapping("putCache")
    public String putCache(Integer id) {
        return "put cache:" + id;
    }


    /**
     *  如果在一个方法里面，需要清除多个缓存，可以使用@Caching，将多条@CacheEvict语句整合在一起
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = "testCache2",key = "'1'"),
            @CacheEvict(value = "testCache2",key = "'2'")
    })
    @GetMapping("caching")
    public String caching(){
        return "Hello Caching";
    }

    /**
     * 1. 缓存穿透，可以通过缓存空值（cache-null-value=true）解决，默认也是缓存空值的。
     * 2. 缓存击穿，大量并发查询一条正好过期的数据时，默认是没有加锁，直接访问数据库的，因此无法解决击穿问题。
     *   可以通过添加sync=true，使得读取操作加锁执行。
     * 3. 缓存雪崩，通过给缓存设置同一过期时间来解决，并不一定都是同一时刻触发保存缓存的操作，缓存过期的时间一般也比较分散。
     * 4. 所有的写模式都没有加锁
     * @param id
     * @return
     */
    @Cacheable(value = "testCache2",key = "#id",sync = true)
    @GetMapping("syncCache")
    public String testCacheableSync(Integer id){
        return "syncCache" + id;
    }
}
