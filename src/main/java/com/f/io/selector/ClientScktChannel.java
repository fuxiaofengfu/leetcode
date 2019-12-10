package com.f.io.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientScktChannel {

	public static void main(String[] args) throws IOException {

		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8082));
		socketChannel.configureBlocking(false);

		Selector selector = Selector.open();

		socketChannel.register(selector, SelectionKey.OP_READ);
		socketChannel.register(selector, SelectionKey.OP_WRITE);

		System.out.println(SelectionKey.OP_READ);
		System.out.println(SelectionKey.OP_WRITE);
		System.out.println(SelectionKey.OP_CONNECT);
		System.out.println(SelectionKey.OP_ACCEPT);

		while (true) {
			int select = selector.select();
			if (select >= 1) {
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					iterator.remove();
					boolean writable = key.isWritable();
					if (writable) {
						socketChannel.write(ByteBuffer.wrap("hello selector".getBytes()));
						key.interestOps(SelectionKey.OP_READ);
					}
					boolean readable = key.isReadable();
					if (readable) {
						ByteBuffer allocate = ByteBuffer.allocate(1024);
						socketChannel.read(allocate);
						System.out.println(new String(allocate.array(), allocate.arrayOffset(), allocate.position()));
						allocate.flip();
						key.interestOps(SelectionKey.OP_WRITE);
					}
				}
			}
		}
	}
}
