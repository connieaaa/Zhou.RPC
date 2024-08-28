package com.keyi.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.keyi.netty.handler.param.ServerRequest;
import com.keyi.netty.medium.Media;
import com.keyi.netty.util.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//ctx.channel().writeAndFlush("is ok \r\n");
		
	    ServerRequest request = JSONObject.parseObject(msg.toString(),ServerRequest.class);
	    
	    // 根据request做业务逻辑处理 + 获得响应
	    
	    Media media = Media.newInstance();
	    Response response = media.process(request);
	    
	    // 发送响应
	    ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
	    ctx.channel().writeAndFlush("\r\n");

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state().equals(IdleState.READER_IDLE)) {
				System.out.println(System.currentTimeMillis() + "：读空闲～～～～");
				ctx.channel().close();
			} else if (event.state().equals(IdleState.WRITER_IDLE)) {
				System.out.println(System.currentTimeMillis() + "：写空闲～～～～");
			} else if (event.state().equals(IdleState.ALL_IDLE)) {
				System.out.println(System.currentTimeMillis() + "：读写空闲～～～～");
				ctx.channel().writeAndFlush("ping\r\n");
			}

		}

	}

}
