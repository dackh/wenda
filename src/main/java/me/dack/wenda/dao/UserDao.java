package me.dack.wenda.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import me.dack.wenda.model.User;

@Mapper
public interface UserDao {

	String TABLE_NAME = "user";
	String INSERT_FIELDS = " name, username, password, salt, head_url";
	String SELECT_FIELDS = "id," + INSERT_FIELDS;
	
	@Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{name},#{username},"
			+ "#{password},#{salt},#{headUrl})"})
	public int addUser(User user);
	
	@Update({"update",TABLE_NAME,"set password = #{password} where id = #{id}"})
	public int updatePassword(User user);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
	public User getUserById(@Param("id")int id);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME ,"where name=#{name}"})
	public User getUserByName(@Param("name")String name);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME ,"where username=#{username}"})
	public User getUserByUserName(@Param("username")String username);
}
