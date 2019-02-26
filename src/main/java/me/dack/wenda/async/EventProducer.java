package me.dack.wenda.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import me.dack.wenda.utils.JedisAdapter;
import me.dack.wenda.utils.RedisKeyUtils;

@Service
public class EventProducer {

	@Autowired
	JedisAdapter adapter;
	
	public boolean produceEvent(EventModel eventModel) {
		try {
			String json = JSONObject.toJSONString(eventModel);
			String key = RedisKeyUtils.getEventQueueKey();
			adapter.lpush(key, json);
			return true;			
		}catch (Exception e) {
			return false;
		}
	}
}
