package me.dack.wenda.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.dack.wenda.async.EventHandler;
import me.dack.wenda.async.EventModel;
import me.dack.wenda.async.EventType;
import me.dack.wenda.model.Comment;
import me.dack.wenda.model.EntityType;
import me.dack.wenda.model.Question;
import me.dack.wenda.service.CommentService;
import me.dack.wenda.service.QuestionService;
import me.dack.wenda.utils.JedisAdapter;
import me.dack.wenda.utils.RedisKeyUtils;

@Component
public class RecommendHandler implements EventHandler{

	@Autowired
	private QuestionService questionService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private JedisAdapter adapter;
	
	@Override
	public void doHandle(EventModel model) {
		
		//计算公式：((log10Qviews) * 4 + (Qscore + Qanswers) / 5 + Ascores ) / (Qage　+ 1 - ((Qage - Qupdated) / 2)^1.5)
		
		//浏览次数 Qview
		
		Question question = questionService.getQuestionById(model.getEntityId());
		int Qanswers = question.getCommentCount();	//问题评论数
		
		long Qscore = 10;		//问题得分 = 问题的点赞数 - 点踩数
//		String likeQuestionKey = RedisKeyUtils.getLikeKey(EntityType.QUESTION_ENTITY, model.getEntityId());
//		Qscore += adapter.scard(likeQuestionKey);
//		String disLikeQuestionKey = RedisKeyUtils.getDisLikeKey(EntityType.QUESTION_ENTITY, model.getEntityId());
//		Qscore -= adapter.scard(disLikeQuestionKey);			
		
		long Ascores = 0;		//回答得分 = 回答的点赞数 - 点踩数
		List<Comment> queryComment = commentService.queryComment(model.getEntityId(), EntityType.QUESTION_ENTITY);
		for(Comment comment : queryComment){
			String likeCommentKey = RedisKeyUtils.getLikeKey(EntityType.QUESTION_ENTITY, comment.getId());
			Ascores += adapter.scard(likeCommentKey);
			String disLikeCommentKey = RedisKeyUtils.getDisLikeKey(EntityType.QUESTION_ENTITY, comment.getId());
			Ascores -= adapter.scard(disLikeCommentKey);			
		}
		
		Date now = new Date();
		
		long Qage = now.getTime() - question.getCreateTime().getTime();
		long Qupdated = queryComment.isEmpty() ? 0 : queryComment.get(0).getCreateTime().getTime();
		
//		double QuestionScore = ((Qscore + Qanswers) / 5 + Ascores ) / (Qage + 1 - Math.pow(((Qage - Qupdated) / 2),1.5));
//		double QuestionScore = ((Qscore + Qanswers) / 5 + Ascores ) / (Math.pow(((Qage - Qupdated) / 2),1.5));
//		System.out.println(QuestionScore);
		String recommendKey = RedisKeyUtils.getRecommendKey();
//		adapter.zadd(recommendKey,QuestionScore, String.valueOf(model.getEntityId()),7 * 24 * 60 * 60);
		adapter.zadd(recommendKey,100, String.valueOf(model.getEntityId()),7 * 24 * 60 * 60);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.RECOMMEND);
	}

}
