# wenda
A question and answer website like zhihu 

### 毕设作品，感兴趣的可以看下，这里只是服务端接口，前台页面另一个同学写的。

### 模仿知乎一个web网站

### 技术点

- 对问题、回答的内容进行了敏感词过滤，通过前缀树算法实现。
- 通过 redis 实现异步队列对点赞、评论等操作的站内通知。
- 根据 Stack Overflow 的排名算法，实现了问题的推荐模块。
- 推拉结合模式实现了 feed 流推送新鲜事 timeline。
- 通过 go 的中 goquery 框架爬取知乎的数据作为早期的数据存储。

## 接口文档
- [接口文档](https://github.com/dackh/wenda/blob/master/doc.md)



## 数据爬取
- [爬取知乎数据demo](https://github.com/dackh/goquery_demo)
