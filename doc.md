# Api规范

## 服务器地址：119.23.227.157:8080
#### 请求返回格式都是
	{
		"errcode": 3,					//请求是否成功  0为成功，2为token验证不通过，3为fail
		"message": "账号和密码不匹配"			  //提示信息
		"result":					//返回参数
	}
#### 1、用户登陆成功之后会返回一个Token字符串，这个Token用于身份验证，在请求其他的Api都需要在请求头Authorization带上这个Token，这个Token默认30分钟过期，需要重新登陆

#### 2、如果用户没有登陆就访问其他Api或者没有在请求头带上Token，那么请求将会被拦截，请求返回


	
	{
	    "errcode": 2,
	    "message": "登陆授权码Authorization不匹配"
	}
	


![](https://i.imgur.com/8eh2bIK.png)


# User
## 注册
#### /user/register

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|name|string|用户名|
|username|string|登陆账号|
|password|string|登陆密码|

#### 响应结果
    {
	    "errcode": 0,
	    "message": "注册成功",
	    "res": null
	}	

## 登陆
#### /user/login

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|username|string|登陆账号|
|password|string|登陆密码|

#### 响应结果
	{
	    "errcode": 0,
	    "message": "登录成功",
	    "res": "40941e1c21e743dfb0a9a1d90a6dba27"
	}


# Question

## 添加问题
#### /question/addQuestion

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|title|string|标题|
|content|string|内容|

#### 响应结果
	{
	    "errcode": 3,
	    "message": "添加失败",
	    "res": null
	}

## 获取最新问题
#### /question/getLaststQuestions
#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|userId|int|用户id|
|offset|int|偏移量|
|limit|int|查询数量|

**id为0时获取全部问题**

#### 响应结果
	{
	    {
	    "errcode": 0,
	    "message": "查找成功",
	    "res": [
	        {
	            "id": 1,
	            "title": "毕业论文怎么写",
	            "content": "大四了，毕业论文怎么写，有什么可以参考的吗哈哈哈",
	            "createTime": "2019-02-19T12:32:48.000+0000",
	            "userId": 1,
	            "commentCount": 0,
	            "status": 0
	        }
	    ]
	}

## 获取推荐问题
#### /question/getRecommandQuestions
#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|offset|int|偏移量|
|limit|int|查询数量|



#### 响应结果
	{
	    {
	    "errcode": 0,
	    "message": "查找成功",
	    "res": [
	        {
	            "id": 1,
	            "title": "毕业论文怎么写",
	            "content": "大四了，毕业论文怎么写，有什么可以参考的吗哈哈哈",
	            "createTime": "2019-02-19T12:32:48.000+0000",
	            "userId": 1,
	            "commentCount": 0,
	            "status": 0
	        }
	    ]
	}

## 修改问题
#### /question/updateQuestion

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|id|string|问题id|
|title|string|标题|
|content|string|内容|

#### 响应结果
	{
	    "errcode": 0,
	    "message": "修改成功",
	    "res": null
	}


## 删除问题
#### /question/deleteQuestion

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|id|string|问题id|

#### 响应结果
	{
	    "errcode": 0,
	    "message": "删除成功",
	    "res": null
	}

# 评论
## 添加评论
#### /comment/addComment

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|content|string|评论内容|
|entityId|int|实体id|
|entityType|int|实体类型|

**这里的实体类型entityType = 1 为问题 ， = 2 为评论**

**可以评论（问答）问题，也可以问问题的评论（问答）进行评论**

**后期可能添加 = 3 ，为用户，用户关注，例如关注某问题，某用户**

#### 响应结果
	{
	    "errcode": 0,
	    "message": "添加成功",
	    "res": null
	}	.

## 修改评论
#### /comment/updateCommentContent

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|content|string|评论内容|
|id|int|评论id|

#### 响应结果
	{
	    "errcode": 0,
	    "message": "修改成功",
	    "res": null
	}	

## 删除评论
#### /comment/deleteComment

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|id|int|评论id|

#### 响应结果
	{
	    "errcode": 0,
	    "message": "删除成功",
	    "res": null
	}	

## 查找评论
#### /comment/queryComment

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|entityId|int|实体id|
|entityType|int|实体类型|

#### 响应结果
	{
	    "errcode": 0,
	    "message": "查找成功",
	    "res": [
	        {
	            "id": 1,
	            "userId": 1,
	            "entityId": 1,
	            "entityType": 1,
	            "content": "这是一条很棒的内容",
	            "createTime": "2019-02-27T12:21:46.000+0000",
	            "status": 0
	        },
	        {
	            "id": 2,
	            "userId": 1,
	            "entityId": 1,
	            "entityType": 1,
	            "content": "这是一条很棒的内容",
	            "createTime": "2019-02-27T12:23:00.000+0000",
	            "status": 0
	        }
	    ]
	}

# 消息
**消息发送方 = 0时，为系统消息，用来提交问题被评论，或者评论被点赞，会话id(conversationId)是指跟某个用户之前的对话，例如用户1发送消息给用户2，以及用户2发送消息给用户1，都属于会话id = '1_2'**
## 添加（发送）消息
#### /message/addMessage

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|toId|int|接收方id|
|idcontent|string|消息内容|

#### 响应结果
	{
	    "errcode": 0,
	    "message": "添加成功",
	    "res": null
	}

## 获取会话信息
#### /message/getConversationDetail

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|conversationId|string|会话id|
|limit|int||
|offset|int||

#### 响应结果
	{
	    "errcode": 0,
	    "message": "查找成功",
	    "res": [
	        {
	            "id": 2,
	            "fromId": 1,
	            "toId": 2,
	            "content": "在下古天乐",
	            "createTime": "2019-03-12T12:12:02.000+0000",
	            "conversationId": "1_2"
	        },
	        {
	            "id": 10,
	            "fromId": 0,
	            "toId": 1,
	            "content": "用户dack回复了你的评论，http://127.0.0.1:8080/question/null",
	            "createTime": "2019-03-11T12:14:26.000+0000",
	            "conversationId": "0_1"
	        }
	    ]
	}

## 获取消息会话列表  
 **返回每个会话最新的一条消息**
#### /message/getConversationList



#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|limit|int||
|offset|int||

#### 响应结果
	{
	    "errcode": 0,
	    "message": "查找成功",
	    "res": [
	        {
	            "message": {
	                "id": 47,
	                "fromId": 0,
	                "toId": 1,
	                "content": "用户dack点赞了你的评论，http://119.23.227.157:8080/qusetion/null",
	                "createTime": "2019-04-24T10:21:39.000+0000",
	                "conversationId": "0_1"
	            },
	            "unReadCount": 7
	        },
	        {
	            "message": {
	                "id": 2,
	                "fromId": 1,
	                "toId": 2,
	                "content": "在下古天乐",
	                "createTime": "2019-03-12T12:12:02.000+0000",
	                "conversationId": "1_2"
	            },
	            "unReadCount": 0
	        }
	    ]
	}



## 修改消息状态为已读   

**点进某个会话之后该会话的所有未读消息都变为已读**
#### /message/updateConversationHasRead

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|conversationId|string|会话id|


#### 响应结果
	{
	    "errcode": 0,
	    "message": "修改成功",
	    "res": null
	}

# 赞踩
**目前只针对评论**
## 赞

#### /like/like

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|commentId|int|评论id|


#### 响应结果
	{
	    "errcode": 0,
	    "message": "点赞成功",
	    "res": 1			//该评论点赞数
	}

## 踩

#### /like/dislike

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|commentId|int|评论id|


#### 响应结果
	{
	    "errcode": 0,
	    "message": "点赞成功",
	    "res": 0  //该评论点赞数
	}

## 获取评论点赞数

#### /like/getLikeCount

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|commentId|int|评论id|


#### 响应结果
	{
	    "errcode": 0,
	    "message": "获取成功",
	    "res": 1		//该评论点赞数
	}

## 获取用户对评论的状态  赞/踩/两者都不

#### /like/getLikeStatus

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|commentId|int|评论id|


#### 响应结果
	{
	     "errcode": 0,
	    "message": "获取成功",
	    "res": -1			//1 为赞，0为默认，即非赞非踩，-1为踩
	}


# 关注
**目前只针对用户**
## 关注

#### /follow/follow

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|userId|int|用户id|


#### 响应结果
	{
	    "errcode": 0,
	    "message": "关注成功",
	    "res": 1			//用户关注数
	}

## 取消关注

#### /follow/unFollow

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|userId|int|用户id|


#### 响应结果
	{
	    "errcode": 0,
	    "message": "取消关注成功",
	    "res": 0			//用户关注数
	}

## 获取用户的粉丝

#### /follow/getFollowers

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|userId|int|用户id|
|limit|int||
|offset|int||


#### 响应结果
	{
	    "errcode": 0,
	    "message": "获取成功",
	    "res": {
	        "users": [
	            {
	                "id": 1,
	                "name": "dack",
	                "username": "root",
	                "password": "F59651BA6706151137F450A9EE796E26",
	                "salt": "19903",
	                "headUrl": "https://www.zhihu.com/people/zong-yi-64-70"
	            }
	        ],
	        "followCount": 1
	    }
	}

## 获取我关注的人

#### /follow/getFollowees

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|userId|int|用户id|
|limit|int||
|offset|int||


#### 响应结果
	{
	    "errcode": 0,
	    "message": "获取成功",
	    "res": {
	        "users": [
	            {
	                "id": 16,
	                "name": "Mr.BigCock",
	                "username": "",
	                "password": "",
	                "salt": "",
	                "headUrl": "https://pic4.zhimg.com/v2-dbbc554b7160dd294d83da842c409897_xs.jpg"
	            }
	        ],
	        "followCount": 1
	    }
	}

## 是否关注某用户

#### /follow/isFollower

#### 请求参数
| 参数 | 类型 | 含义 |
| ------ | ----- | ------ |
|userId|int|用户id|

#### 响应结果
	{
	    "errcode": 0,
	    "message": "查看成功",
	    "res": 1		//1为关注，0为否
	}
