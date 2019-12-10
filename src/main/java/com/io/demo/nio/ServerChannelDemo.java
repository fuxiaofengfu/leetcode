package com.io.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerChannelDemo {


	public static void main(String[] args) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8080));
		//serverSocketChannel.configureBlocking(false);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		while(true){
			SocketChannel accept = serverSocketChannel.accept();

			System.out.println(accept.validOps());

			System.out.println("new client connection ... ");

			int read = accept.read(byteBuffer);
			System.out.println(new String(byteBuffer.array(),byteBuffer.arrayOffset(),byteBuffer.position()));
			accept.write(ByteBuffer.wrap("hhhhhh".getBytes()));
			System.out.println(accept.validOps());

		}

	}
}
