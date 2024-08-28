package com.keyi.client.core;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

import com.keyi.client.zk.ZookeeperFactory;

import io.netty.channel.ChannelFuture;

public class ServerWatcher implements CuratorWatcher {

	@Override
	public void process(WatchedEvent event) throws Exception {
		CuratorFramework client = ZookeeperFactory.create();
		String path = event.getPath();
		client.getChildren().usingWatcher(this).forPath(path);
		List<String> serverPaths = client.getChildren().forPath(path);
		ChannelManager.realServerPath.clear();
		
		for (String serverPath : serverPaths) {
			String[] strings = serverPath.split("#");
			ChannelManager.realServerPath.add(strings[0]+"#"+strings[1]);
			
			//ChannelFuture channelFuture= TcpClient.b.connect(strings[0],Integer.valueOf(strings[1]));
			//ChannelManager.addChannel(channelFuture);
			
		}
		ChannelManager.clear();
		for(String realServer:ChannelManager.realServerPath) {
			String[] strings = realServer.split("#");
			try {
				ChannelFuture channelFuture= TcpClient.b.connect(strings[0],Integer.valueOf(strings[1]));
				ChannelManager.addChannel(channelFuture);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		

	}

}
