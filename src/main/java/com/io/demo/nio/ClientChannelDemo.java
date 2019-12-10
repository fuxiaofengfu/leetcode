package com.io.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientChannelDemo {

	public static void main(String[] args) throws IOException {

		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));

		socketChannel.write(ByteBuffer.wrap("hello world".getBytes()));

		ByteBuffer allocate = ByteBuffer.allocate(1024);
		socketChannel.read(allocate);
		System.out.println(new String(allocate.array(),allocate.arrayOffset(),allocate.position()));
	}
}
