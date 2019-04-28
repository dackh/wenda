package me.dack.wenda.service;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dack.wenda.dao.UserDao;
import me.dack.wenda.model.Errcode;
import me.dack.wenda.model.Result;
import me.dack.wenda.model.User;
import me.dack.wenda.utils.JedisAdapter;
import me.dack.wenda.utils.MD5Util;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private JedisAdapter adapter;
	
	public Result register(String name,String username,String password){
		Result result = new Result();
		if(StringUtils.isBlank(name)){
			result.setErrcode(Errcode.Error);
			result.setMessage("用户名不能为空");
			return result;
		}
		if(StringUtils.isBlank(username)){
			result.setErrcode(Errcode.Error);
			result.setMessage("账号不能为空");
			return result;
		}
		if(StringUtils.isBlank((password))){
			result.setErrcode(Errcode.Error);
			result.setMessage("密码不能为空");
			return result;
		}
		User user = userDao.getUserByName(name);
		if(user != null){
			result.setErrcode(Errcode.Error);
			result.setMessage("用户名已经被注册");
			return result;
		}
		user = userDao.getUserByUserName(username);
		if(user != null){
			result.setErrcode(Errcode.Error);
			result.setMessage("该账号已经被注册");
			return result;
		}
		user = new User();
		user.setName(name);
		user.setUsername(username);
		String salt = UUID.randomUUID().toString().substring(0,5);
		user.setSalt(salt);
		user.setPassword(MD5Util.MD5(password+user.getSalt()));
		user.setHeadUrl("https://pic4.zhimg.com/aadd7b895_s.jpg");
		userDao.addUser(user);
		result.setErrcode(Errcode.Null);
		result.setMessage("注册成功");
		return result;
	}
	
	public Result login(String username,String password){
		Result result = new Result();
		if(StringUtils.isBlank(username)){
			result.setErrcode(Errcode.Error);
			result.setMessage("账号不能为空");
			return result;
		}
		if(StringUtils.isBlank(password)){
			result.setErrcode(Errcode.Error);
			result.setMessage("密码不能为空");
			return result;
		}
		User user = userDao.getUserByUserName(username);
		if(user == null){
			result.setErrcode(Errcode.Error);
			result.setMessage("该账号不存在");
			return result;
		}
		if(!user.getPassword().equals(MD5Util.MD5(password+user.getSalt()))){
			result.setErrcode(Errcode.Error);
			result.setMessage("密码不正确");
			return result;
		}
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		long redisValue = adapter.set(token, String.valueOf(user.getId()), 60*30);
		if (redisValue != 0){
			result.setErrcode(Errcode.Null);
			result.setMessage("登录成功");
			result.setRes(token);
			return result;
		}
		result.setErrcode(Errcode.Error);
		result.setMessage("登录失败");
		return result;
	}
	
	//退出
	public void logout(){
		
	}
	
	public User getUserById(int id) {
		return userDao.getUserById(id);
	}
}
