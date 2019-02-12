package me.dack.wenda.service;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import me.dack.wenda.dao.UserDao;
import me.dack.wenda.model.Result;
import me.dack.wenda.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public Result register(String name,String username,String password){
		Result result = new Result();
		if(StringUtils.isBlank(name)){
			result.setFlag(false);
			result.setMessage("用户名不能为空");
			return result;
		}
		if(StringUtils.isBlank(username)){
			result.setFlag(false);
			result.setMessage("账号不能为空");
			return result;
		}
		if(StringUtils.isBlank((password))){
			result.setFlag(false);
			result.setMessage("密码不能为空");
			return result;
		}
		User user = userDao.getUserByName(name);
		if(user != null){
			result.setFlag(false);
			result.setMessage("用户名已经被注册");
			return result;
		}
		user = userDao.getUserByUserName(username);
		if(user != null){
			result.setFlag(false);
			result.setMessage("该账号已经被注册");
			return result;
		}
		user = new User();
		user.setName(name);
		user.setUsername(username);
		String salt = UUID.randomUUID().toString().substring(0,5);
		user.setSalt(salt);
		//MD5Encoder.encode(password+salt);
		user.setPassword(password);
		user.setHeadUrl("head_url");
		userDao.addUser(user);
		result.setFlag(true);
		result.setMessage("注册成功");
		return result;
	}
	
	public Result login(String username,String password){
		Result result = new Result();
		if(StringUtils.isBlank(username)){
			result.setFlag(false);
			result.setMessage("账号不能为空");
			return result;
		}
		if(StringUtils.isBlank(password)){
			result.setFlag(false);
			result.setMessage("密码不能为空");
			return result;
		}
		User user = userDao.getUserByUserName(username);
		if(user == null){
			result.setFlag(false);
			result.setMessage("该账号不存在");
			return result;
		}
		//MD5Encoder.encode(password+user.getSalt());
		if(!user.getPassword().equals(password)){
			result.setFlag(false);
			result.setMessage("密码不正确");
			return result;
		}
		
		//token
		result.setFlag(true);
		result.setMessage("登录成功");
		result.setRes(new ArrayList<>());
		return result;
	}
	
	//退出
	public void logout(){
		
	}
}
