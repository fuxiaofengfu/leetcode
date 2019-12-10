package com.f.io.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author
 */
public class ServerScktChannel {

	public static void main(String[] args) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8082));

		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			int keys = selector.select();
			if (keys >= 1) {
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
				while (selectionKeyIterator.hasNext()) {
					SelectionKey selectionKey = selectionKeyIterator.next();
					selectionKeyIterator.remove();
					if (selectionKey.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
						SocketChannel accept = server.accept();
						if (null != accept) {
							accept.configureBlocking(false);
							accept.register(selector, SelectionKey.OP_READ);
							accept.register(selector, SelectionKey.OP_WRITE);
						}
					}
					boolean readable = selectionKey.isReadable();
					if (readable) {
						SocketChannel client = (SocketChannel) selectionKey.channel();
						client.read(buffer);
						System.out.println(new String(buffer.array(), buffer.arrayOffset(), buffer.position()));
						buffer.flip();
						selectionKey.interestOps(SelectionKey.OP_WRITE);
					}

					boolean writable = selectionKey.isWritable();
					if (writable) {
						SocketChannel client = (SocketChannel) selectionKey.channel();
						client.write(ByteBuffer.wrap("server echo".getBytes()));
						selectionKey.interestOps(SelectionKey.OP_READ);
					}
				}
			}
		}

	}
}
