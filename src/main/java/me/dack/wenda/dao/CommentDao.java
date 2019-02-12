package me.dack.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import me.dack.wenda.model.Comment;

@Mapper
public interface CommentDao {

	String TABLE_NAME = "comment";
	String INSERT_FIELDS = " user_id, content, create_time, entity_id,entity_type, status";
	String SELECT_FIELDS = "id ," + INSERT_FIELDS;
	
	@Insert({"insert",TABLE_NAME,"(",INSERT_FIELDS,") values "
			+ "#{userId},#{content},#{create_time},#{entity_id},#{entity_type},#{status}"})
	int addComment(Comment comment);
	
	@Update({"update",TABLE_NAME,"set content=#{content} where id = #{id}"})
	int updateCommentContent(String content,int id);
	
	@Update({"update",TABLE_NAME,"set status = #{status} where id = #{id}"})
	int updateCommentStatus(int id,int status);
	
	@Select({"select count(id) from",TABLE_NAME,
		"where entity_id = #{entity_id} and entity_type= #{entity_type} and status=0"})
    int getCommentCount(int entity_id,int entity_type);
    
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,
    	"where entity_id = #{entity_id} and entity_type= #{entity_type} and status=0"})
    List<Comment> queryComment(int entity_id,int entity_type);
    
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id} and status=0"})
    Comment getCommentById(int id);
}
