package me.dack.wenda.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisAdapter implements InitializingBean{

	private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
	private JedisPool pool;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		pool = new JedisPool("119.23.227.157",6379);
	}
	
	public long set(String key,String value,int expire) {
		Jedis jedis = null;
		try{
			jedis = pool.getResource();	
			jedis.set(key, value);
			return jedis.expire(key, expire);
		}catch (Exception e) {
			logger.error("redis连接异常"+e.getMessage());
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
		return 0;
	}
	
	public String get(String key) {
		Jedis jedis = null;
		try{
			jedis = pool.getResource();		
			return jedis.get(key);
		}catch (Exception e) {
			logger.error("redis连接异常"+e.getMessage());
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
		return "";
	}
	
	public long del(String key) {
		Jedis jedis = null;
		try{
			jedis = pool.getResource();	
			return jedis.del(key);
		}catch (Exception e) {
			logger.error("redis连接异常"+e.getMessage());
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
		return 0;
	}
	
	
	public long sadd(String key,String value){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.sadd(key, value);
		}catch (Exception e) {
			logger.error("redis链接异常"+e.getMessage());
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return 0;
	}
	
	public long srem(String key,String value){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.srem(key, value);
		}catch (Exception e) {
			logger.error("redis链接异常"+e.getMessage());
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return 0;
	}
	
	public long scard(String key){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			return jedis.scard(key);
		}catch (Exception e) {
			logger.error("redis链接异常"+e.getMessage());
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return 0;
	}
	
	public boolean sismember(String key,String value){
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.sismember(key, value);
		} catch (Exception e) {
			logger.error("redis链接异常"+e.getMessage());
		}finally {
			if(jedis != null){
				jedis.close();
			}
		}
		return false;
	}
	
	 public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

}
