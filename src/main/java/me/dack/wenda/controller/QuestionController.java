package me.dack.wenda.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.dack.wenda.model.Question;
import me.dack.wenda.model.Result;
import me.dack.wenda.service.QuestionService;

@Controller
@RequestMapping("/question")
public class QuestionController {

	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	private QuestionService questionService;
	
	
	@RequestMapping("/addQuestion")
	@ResponseBody
	public Result addQuestion(@RequestParam("title") String title,
			@RequestParam("content") String content){
		Question question = new Question();
		question.setTitle(title);
		question.setContent(content);
		question.setCreateTime(new Date());
		//
		question.setCommentCount(0);
		question.setStatus(0);
		question.setUserId(1);
		try{
			if(questionService.addQuestion(question) > 0){
				Result result = new Result(true, "添加成功");
				return result;
			}
		}catch (Exception e) {
			logger.error("添加问题失败"+e.getMessage());
		}
		Result result = new Result(false, "添加失败");
		return result;
	}
	
	@RequestMapping("/getLaststQuestions")
	@ResponseBody
	public Result getLaststQuestions(@RequestParam("userId")int userId,@RequestParam("offset")int offset,
			@RequestParam("limit")int limit){
		try{
			List<Question> latestQuestions = questionService.getLatestQuestions(userId, offset, limit);
			Result result = new Result(true, "查找失败");
			result.setRes(latestQuestions);
		}catch (Exception e) {
			logger.error("查看最新问题失败"+e.getMessage());
		}
		Result result = new Result(false,"查找失败");
		return result;
	}
	
	@RequestMapping("/updateQuestion")
	@ResponseBody
	public Result updateQuestion(@RequestParam("id")int id,@RequestParam("title")String title,
			@RequestParam("content")String content){
		try{
			if(questionService.updateQuestion(id, title, content) > 0){
				Result result = new Result(true, "修改成功");
				return result;
			}
		}catch (Exception e) {
			logger.error("修改问题内容失败"+e.getMessage());
		}
		Result result = new Result(false,"修改失败");
		return result;
	}
	
	@RequestMapping("/deleteQuestion")
	@ResponseBody
	public Result deleteQuestion(@RequestParam("id")int id){
		try{
			if(questionService.deleteQuestion(id) > 0){
				Result result = new Result(true, "删除成功");
				return result;
			}
		}catch (Exception e) {
			logger.error("删除问题内容失败"+e.getMessage());
		}
		Result result = new Result(false,"删除失败");
		return result;
	}
}
