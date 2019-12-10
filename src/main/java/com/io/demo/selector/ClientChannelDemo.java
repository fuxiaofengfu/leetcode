package com.io.demo.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientChannelDemo {

	public static void main(String[] args) throws IOException, InterruptedException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

		Selector selector = Selector.open();

		socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);

		while (true) {

			int select = selector.select();

			if (select >= 1) {

				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey next = iterator.next();

					SocketChannel channel = (SocketChannel) next.channel();
					System.out.println(111);
					socketChannel.write(ByteBuffer.wrap("hello world".getBytes()));
					ByteBuffer allocate = ByteBuffer.allocate(1024);
					socketChannel.read(allocate);
					System.out.println(new String(allocate.array(), allocate.arrayOffset(), allocate.position()));

				}

			}
		}
	}
}
