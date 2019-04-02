package me.dack.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import me.dack.wenda.model.Question;

@Mapper
public interface QuestionDao {
	String TABLE_NAME = "question";
	String INSERT_FIELDS = " title, content, create_time, comment_count, status, user_id";
	String SELECT_FIELDS = "id," + INSERT_FIELDS;
	
	List<Question> getLatestQuestions(@Param("userId") int userId,@Param("offset")int offset,
							@Param("limit")int limit);
	
	@Insert({"insert into " , TABLE_NAME , "(",  INSERT_FIELDS ,
			") values (#{title},#{content},#{createTime},#{commentCount},#{status},#{userId})"})
	int addQuestion(Question question);
	
	@Select({"select",SELECT_FIELDS,"from ",TABLE_NAME,"where id=#{id} and status = 0"})
	Question getQuestionById(@Param("id")int id);
	 
	@Update({"update ",TABLE_NAME, " set comment_count = #{commentCount} where id = #{id}"})
	int updateQuestionCount(@Param("id")int id,@Param("commentCount")int commentCount);

	@Update({"update ",TABLE_NAME, " set title = #{title} , content = #{content} where id = #{id}"})
	int updateQuestionTitleAndContent(@Param("id")int id,@Param("title")String title,@Param("content")String content);
	
	@Update({"update ",TABLE_NAME, " set status = #{status} where id = #{id}"})
	int updateQuestionStatus(@Param("id")int id,@Param("status")int status);
}
