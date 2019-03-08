package me.dack.wenda.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import me.dack.wenda.dao.UserDao;
import me.dack.wenda.model.Errcode;
import me.dack.wenda.model.HostHolder;
import me.dack.wenda.model.Result;
import me.dack.wenda.model.User;
import me.dack.wenda.utils.FastJsonUtil;
import me.dack.wenda.utils.JedisAdapter;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JedisAdapter adapter;
	@Autowired
	private HostHolder holder;
	@Autowired
	private UserDao userDao;
	@Override
	 public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		String token = httpServletRequest.getHeader("Authorization");
		if (token != ""){
			String value = adapter.get(token);
			if (value != null){
				int userId = Integer.parseInt(value);
				User user = userDao.getUserById(userId);
				holder.setUser(user);
				return true;
			}
		}
		httpServletResponse.setContentType("application/json;charset=utf-8");
		Result result = new Result(Errcode.Auth,"登陆授权码Authorization不匹配");
		httpServletResponse.getWriter().write(FastJsonUtil.bean2Json(result));
		return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    	holder.clear();
    }
	
}
