package me.dack.wenda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.dack.wenda.controller.CommentController;
import me.dack.wenda.controller.QuestionController;
import me.dack.wenda.controller.UserController;
import me.dack.wenda.model.Result;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WendaApplicationTests {

	
	@Autowired
	private UserController userController;
	@Autowired
	private QuestionController questionController;
	@Autowired
	private CommentController CommentController;
	
	@Test
	public void userControllerTest() {
		Result login = userController.login("123456", "123456");
		System.out.println(login);
		System.out.println(login.getMessage()+login.isFlag());
	}
	
	@Test
	public void questionControllerTest() {
		Result login = questionController.addQuestion("title", "content");
		System.out.println(login);
		System.out.println(login.getMessage()+login.isFlag());
	}
	
	@Test
	public void WendaApplicationTestsTest() {
		Result login = CommentController.updateCommentContent("updateContent", 1);
		System.out.println(login);
		System.out.println(login.getMessage()+login.isFlag());
	}

}

