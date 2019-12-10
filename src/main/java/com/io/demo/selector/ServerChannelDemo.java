package com.io.demo.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerChannelDemo {

	public static void main(String[] args) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8080));
		serverSocketChannel.configureBlocking(false);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while (true) {
			int select = selector.select();
			if (select >= 1) {
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey next = iterator.next();
					if (next.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) next.channel();
						SocketChannel client = server.accept();
						if (null != client) {
							client.configureBlocking(false);
							client.register(selector, SelectionKey.OP_READ);
							client.register(selector, SelectionKey.OP_WRITE);
						}
					}
					if (next.isWritable()) {
						SocketChannel client = (SocketChannel) next.channel();
						client.write(ByteBuffer.wrap("hhhhhh".getBytes()));
						next.interestOps(SelectionKey.OP_READ);
					}
					if (next.isReadable()) {
						SocketChannel client = (SocketChannel) next.channel();
						client.read(byteBuffer);
						System.out.println(new String(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.position()));
						next.interestOps(SelectionKey.OP_WRITE);
					}
					next.cancel();
				}

			}


		}

	}
}
