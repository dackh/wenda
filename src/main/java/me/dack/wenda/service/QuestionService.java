package me.dack.wenda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import me.dack.wenda.dao.QuestionDao;
import me.dack.wenda.model.Question;
import me.dack.wenda.utils.JedisAdapter;
import me.dack.wenda.utils.RedisKeyUtils;

@Service
public class QuestionService {

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private SensitiveService sensitiveService;
	
	@Autowired
	private JedisAdapter adapter;
		
	
	public int addQuestion(Question question){
		question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
		question.setContent(HtmlUtils.htmlEscape(question.getContent()));
		
		//敏感词过滤
		question.setTitle(sensitiveService.filter(question.getTitle()));
		question.setContent(sensitiveService.filter(question.getContent()));
		
		return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
	}
	
	public List<Question> getLatestQuestions(int userId,int offset,int limit){
		return questionDao.getLatestQuestions(userId, offset, limit);
	}
	
	public List<Question> getRecommandQuestions(int offset,int limit){
		String recommendKey = RedisKeyUtils.getRecommendKey();
		Set<String> zrevrange = adapter.zrevrange(recommendKey, offset, limit);
		List<Question> list = new ArrayList<>();
		for(String key : zrevrange) {
			Question question = questionDao.getQuestionById(Integer.parseInt(key));
			list.add(question);
		}
		return list;
	}
	
	public int updateQuestionCount(int id,int commentCount){
		return questionDao.updateQuestionCount(id, commentCount);
	}
	
	public int updateQuestion(int id,String title,String content){
		return questionDao.updateQuestionTitleAndContent(id, title, content);
	}
	
	public int deleteQuestion(int id){
		return questionDao.updateQuestionStatus(id, 1);
	}
	
	public Question getQuestionById(int id) {
		return questionDao.getQuestionById(id);
	}
}
