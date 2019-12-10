package com.f.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author ff
 */
public class ClientChannel {

	public static void main(String[] args) throws IOException, InterruptedException {
		while (true) {
			TimeUnit.SECONDS.sleep(2);
			SocketChannel client = SocketChannel.open();
			boolean connect = client.connect(new InetSocketAddress("127.0.0.1", 8081));
			String randomStr = String.format("hello worldaa %s",String.valueOf(new Random().nextInt()));
			System.out.println(randomStr);
			int write = client.write(ByteBuffer.wrap(randomStr.getBytes()));
			//client.close();
		}
	}
}
