package me.dack.wenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.dack.wenda.model.Result;
import me.dack.wenda.service.UserService;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/register")
	@ResponseBody
	public Result register(@RequestParam("name")String name,
			@RequestParam("username")String username,
			@RequestParam("password")String password){
		return userService.register(name, username, password);
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public Result login(@RequestParam("username")String username
			,@RequestParam("password")String password){
		return userService.login(username, password);
	}
	
	@RequestMapping("logout")
	@ResponseBody
	public Result logout(int id){
		return new Result();
	}
}
