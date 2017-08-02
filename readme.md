## Netty 学习

---

### [Writing a Discard Server 写个抛弃服务](netty-chapter01)

### [Writing a Echo Server 写个应答服务](netty-chapter02)

### [Writing a Time Server 写个时间服务](netty-chapter03)
* 不接受任何请求时发送一个含32位的整数消息，发送完立即关闭

### [Speaking in POJO instead of ByteBuf 用POJO代替ByteBuf](netty-chapter04)
* 之前的例子都是使用的ByteBuf，这里将使用POJO
* server 端 TimeEncoder，client 端 TimeDecoder

#### 主要参考
* https://github.com/waylau/netty-4-user-guide