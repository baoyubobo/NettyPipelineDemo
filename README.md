# NettyPipelineDemo
## 介绍
NettyPipelineDemo 项目可作为一个通用的字节流数据解析通用模板。

本项目的示例代码主要分为两部分：
1. Netty Client : 主动与 Server 建立连接， 发送消息字节流。
2. Netty Server : 接收字节流消息，并解析成模型对象，打印出来。


## 自定义数据格式
* prefixMark - 占2字节 - 消息头前缀标识 - 内容固定为##
* bodyLen - 占4字节 - 消息体长度
* name - 占6字节 - 消息体内容：某人的姓名
* age - 占4字节 - 消息体内容：某人的年龄
* city - 占10字节 - 消息体内容：某人所在的城市 

```java
|<------ 2 ------>|<------- 4 ------->|<----- 6 ------->|<----- 4 ----->|<---------- 10 --------->|
|                 |                   |                 |               |                         |
|-----------------|-------------------|-----------------|---------------|-------------------------|
|   prefixMark    |     bodyLen       |      name       |      age      |           city          |
|      ##         |       20          |                 |               |                         |
|-----------------|-------------------|-----------------|---------------|-------------------------|
|                                     |                                                           |
|<------------ header---------------->|<---------------------- body ----------------------------->|
 
```

## Server channel pipeline 详解
**Netty channel pipeline 一般分为三部分：**
1. LengthFieldBasedFrameDecoder ：获取完整一帧数据
2. MsgDecoder ：将byte[]消息解码成模型对象
3. ServerHandler ：使用模型对象进一步处理业务逻辑

**MsgDecoder**
变量 ByteBuf in  ，可称为原始ByteBuf，由 netty channel pipeline 自动管理，所以不需要我们自己来进行release操作。
所以在  decode 函数中，一般不推荐创建新的 ByteBuf ， 而是通过获取原始ByteBuf的逻辑切片，来执行具体地解析流程。（否则就需要我们自己release 新的 ByteBuf）

最佳实践点：
1. 推荐使用 ByteBuf.slice() 函数，基于原始ByteBuf来创建切片。因为该函数不会创建新的ByteBuf对象，不会改变RefCnt，所以也不需要负责管理ByteBuf对象的释放
2. 创建切片后，要设置原始 ByteBuf 跳过指定长度, 否则会重复读取这部分消息
3. 解析数据时推荐分两步：先获取切片，然后根据此切片解析出数据


具体实现请参考 MsgDecoder.class 和 ByteBufUtils.class