## Netty 学习

---

### [Writing a Discard Server 写个抛弃服务](netty-chapter01)

### [Writing a Echo Server 写个应答服务](netty-chapter02)

### [Writing a Time Server 写个时间服务](netty-chapter03)
* 不接受任何请求时发送一个含32位的整数消息，发送完立即关闭

### [Speaking in POJO instead of ByteBuf 用POJO代替ByteBuf](netty-chapter04)
* 之前的例子都是使用的ByteBuf，这里将使用POJO
* server 端 TimeEncoder，client 端 TimeDecoder

### [Factorial - 阶乘计算Server](netty-chapter05)


### 处理基于流的传输
> 基于流（stream-based）的传输，如TCP/IP，接收的数据存储在socket的接收buffer，但是这个buffer缓冲不是数据包的队列，而是字节队列，也就是说即使你用两个独立的数据包发送两个消息，操作系统不会将其作为两个消息对待，而是作为一堆的字节来处理。

> TCP/IP 通信传输的数据都是基于流传输的（stream-based），单位是数据包（Packet），Packet包含了发送者、接收者的地址信息，这些包沿着不同的路径在一个或多个网络中，经过中继站，最终在目的地重新组合。如果对于一个中继站数据包太大，改数据包就会被分片，被分片成多个小的数据包，规定上在中间节点上不允许对小数据包拼装组合。


#### 主要参考
* http://netty.io/wiki/user-guide-for-4.x.html
* https://github.com/netty/netty/tree/4.0/example/src/main/java/io/netty/example
* https://github.com/waylau/netty-4-user-guide