package me.dack.wenda.model;

import org.springframework.stereotype.Component;

/**
 * 用户类
 * @author Administrator
 *
 */
@Component
public class HostHolder {
	
	private static ThreadLocal<User> users = new ThreadLocal<User>();
	
	public User getUser() {
		return users.get();
	}
	
	public void setUser(User user) {
		users.set(user);
	}
	
	public void clear() {
		users.remove();
	}
	
}
