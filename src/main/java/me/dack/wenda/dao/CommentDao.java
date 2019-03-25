package me.dack.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import me.dack.wenda.model.Comment;

@Mapper
public interface CommentDao {

	String TABLE_NAME = "comment";
	String INSERT_FIELDS = " user_id, content, create_time, entity_id,entity_type, status";
	String SELECT_FIELDS = "id ," + INSERT_FIELDS;
	
	@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values "
			+ "(#{userId},#{content},#{createTime},#{entityId},#{entityType},#{status})"})
	int addComment(Comment comment);
	
	@Update({"update",TABLE_NAME,"set content = #{content} where id = #{id}"})
	int updateCommentContent(String content,int id);
	
	@Update({"update",TABLE_NAME,"set status = #{status} where id = #{id}"})
	int updateCommentStatus(@Param("id")int id,@Param("status")int status);
	
	@Select({"select count(id) from",TABLE_NAME,
		"where entity_id = #{entityId} and entity_type= #{entityType} and status=0"})
    int getCommentCount(@Param("entityId")int entityId,@Param("entityType")int entityType);
    
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,
    	"where entity_id = #{entityId} and entity_type= #{entityType} and status=0"})
    List<Comment> queryComment(@Param("entityId")int entityId,@Param("entityType")int entityType);
    
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id} and status=0"})
    Comment getCommentById(@Param("id")int id);
}
