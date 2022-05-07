package com.example.demo.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义Handler需要继承netty规定好的某个HandlerAdapter(规范)
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送的数据
     *
     * @param ctx 上下文对象, 含有通道channel，管道pipeline
     * @param msg 就是客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        //Channel channel = ctx.channel();
        //ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链接, 出站入站
        //将 msg 转成一个 ByteBuf，类似NIO 的 ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕处理方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("HelloClient", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    /**public class NettyClient {
     public static void main(String[] args) throws Exception {
     //客户端需要一个事件循环组
     EventLoopGroup group = new NioEventLoopGroup();
     try {
     //创建客户端启动对象
     //注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
     Bootstrap bootstrap = new Bootstrap();
     //设置相关参数
     bootstrap.group(group) //设置线程组
     .channel(NioSocketChannel.class) // 使用 NioSocketChannel 作为客户端的通道实现
     .handler(new ChannelInitializer<SocketChannel>() {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
    //加入处理器
    channel.pipeline().addLast(new NettyClientHandler());
    }
    });
     System.out.println("netty client start");
     //启动客户端去连接服务器端
     ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
     //对关闭通道进行监听
     channelFuture.channel().closeFuture().sync();
     } finally {
     group.shutdownGracefully();
     }
     }
     }
     * 处理异常, 一般是需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}