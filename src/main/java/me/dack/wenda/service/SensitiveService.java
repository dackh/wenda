package me.dack.wenda.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


@Service
public class SensitiveService implements InitializingBean{

	
	private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
	
	/**
	 * 默认敏感词替换符
	 */
	private static final String DEFAULT_REPLACEMENT = "***";
	
	private class TrieNode{
		/**
		 * true关键字终结，false继续
		 */
		private boolean end = false;
		
		
		/**
		 * key下一个字符，value是对应节点
		 */
		private Map<Character, TrieNode> subNodes = new HashMap<>();
		
		/**
		 * 向指定位置添加节点
		 * @param key
		 * @param node
		 */
		void addSubNode(Character key,TrieNode node){
			subNodes.put(key,node);
		}
		
		/**
		 * 获取下一个节点
		 * @param key
		 * @return
		 */
		TrieNode getSubNode(Character key){
			return subNodes.get(key);
		}
		
		boolean isKeyWordEnd(){
			return end;
		}
		
		void setKeyWordEnd(boolean end){
			this.end = end;
		}
	}	
	/**
	 * 根节点
	 */
	private TrieNode rootNode = new TrieNode();
	
	/**
	 * 判断是否是一个符号
	 * 
	 */
	private boolean isSymbol(char c){
		int ic = (int) c;
		// 0x2E80-0x9FFF 东亚文字范围
		return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
	}
	
		
	private void addWord(String lineText){
		TrieNode tempNode = rootNode;
		
		for(int i = 0;i < lineText.length();++i){
			Character c = lineText.charAt(i);
			
			//过滤空格
			if(isSymbol(c)){
				continue;
			}
			
			TrieNode node = tempNode.getSubNode(c);
			if(node == null){
				node = new TrieNode();
				tempNode.addSubNode(c, node);
			}
			
			tempNode = node;
			
			if(i == lineText.length() - 1){
				tempNode.setKeyWordEnd(true);
			}
		}
	}
	
	
	/**
	  * 过滤敏感词
	 */
	public String filter(String text){
		if(StringUtils.isBlank(text)){
			return text;
		}
		String replacement = DEFAULT_REPLACEMENT;
		StringBuilder result = new StringBuilder();
		
		TrieNode tempNode = rootNode;
		int begin = 0;
		int position = 0;
		while(position < text.length()){
			char c = text.charAt(position);
			
			//空格直接跳过
			if(isSymbol(c)){
				if(tempNode == rootNode){
					result.append(c);
					++begin;
				}
				++position;
				continue;
			}
			
			tempNode = tempNode.getSubNode(c);
			if(tempNode == null){
				result.append(text.charAt(begin));
				position = begin + 1;
				begin = position;
				tempNode = rootNode;
			}else if(tempNode.isKeyWordEnd()){
				result.append(replacement);
				position = position + 1;
				begin = position;
				tempNode = rootNode;
			}else{
				++position;
			}
		}
		
		result.append(text.substring(begin));
		
		return result.toString();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		rootNode = new TrieNode();
		
		try{
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String lineText;
			while((lineText = bufferedReader.readLine()) != null){
				lineText = lineText.trim();
				addWord(lineText);
			}
			reader.close();
			
		}catch (Exception e) {
			logger.error("读取敏感词文件失败"+e.getMessage());
		}
		
	}

//	public static void main(String[] args){
//		SensitiveService sensitiveService = new SensitiveService();
//		sensitiveService.addWord("色情");
//		sensitiveService.addWord("暴力");
//		
//		System.out.println(sensitiveService.filter("色ギ情   你好色    情"));
//	}
	
}
