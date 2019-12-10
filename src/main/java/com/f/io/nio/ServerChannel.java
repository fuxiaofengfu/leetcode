package com.f.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class ServerChannel {

	public static void main(String[] args) throws IOException, InterruptedException {
		ByteBuffer allocate = ByteBuffer.allocate(1024);
		ServerSocketChannel server = ServerSocketChannel.open();
		//server.configureBlocking(false);
		server.bind(new InetSocketAddress("127.0.0.1", 8081));
		while (true) {
			SocketChannel accept = server.accept();
			if (null != accept) {
				System.out.println(String.format("new connection %s", accept));
				TimeUnit.SECONDS.sleep(5);
				accept.read(allocate);
				System.out.println(new String(allocate.array(), allocate.arrayOffset(), allocate.position()));
				allocate.clear();
			}
		}
	}
}
