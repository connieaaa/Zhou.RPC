package com.keyi.client.core;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;

import com.alibaba.fastjson.JSONObject;
import com.keyi.client.constants.Constants;
import com.keyi.client.handler.SimpleClientHandler;
import com.keyi.client.param.ClientRequest;
import com.keyi.client.param.Response;
import com.keyi.client.zk.ZookeeperFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TcpClient {
	static final Bootstrap b = new Bootstrap();
	static ChannelFuture f = null;

	static {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		b.group(workerGroup); // (2)
		b.channel(NioSocketChannel.class); // (3)
		b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
		b.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new SimpleClientHandler());
				ch.pipeline().addLast(new StringEncoder());
			}
		});

		CuratorFramework client = ZookeeperFactory.create();
		String host = "localhost";
		int port = 8080;
		try {

			// 加上zk监听服务器变化
			CuratorWatcher watcher = new ServerWatcher();
			client.getChildren().usingWatcher(watcher).forPath(Constants.SERVER_PATH);

			List<String> serverPaths = client.getChildren().forPath(Constants.SERVER_PATH);

			for (String serverPath : serverPaths) {
				String[] strings = serverPath.split("#");
				ChannelManager.realServerPath.add(strings[0] + "#" + strings[1]);
				ChannelFuture channelFuture = TcpClient.b.connect(strings[0], Integer.valueOf(strings[1]));
				ChannelManager.addChannel(channelFuture);
			}
			if (ChannelManager.realServerPath.size() > 0) {
				String[] hostAndPort = ChannelManager.realServerPath.toArray()[0].toString().split("#");
				host = hostAndPort[0];
				port = Integer.valueOf(hostAndPort[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			f = b.connect(host, port).sync();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	// 注意：每一个请求都是同一个连接，考虑并发问题
	// 发送数据
	public static Response send(ClientRequest request) {
		f = ChannelManager.get(ChannelManager.position);
		f.channel().writeAndFlush(JSONObject.toJSONString(request));
		f.channel().writeAndFlush("\r\n");

		DefaultFuture df = new DefaultFuture(request);

		return df.get();
	}

}
