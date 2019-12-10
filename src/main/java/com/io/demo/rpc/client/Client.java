package com.io.demo.rpc.client;

import com.io.demo.rpc.server.EchoInterface;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {


	public static void main(String[] args) throws IOException {

		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

		ByteBuffer allocate = ByteBuffer.allocate(10240);

		EchoInterface instance = (EchoInterface) Proxy.newProxyInstance(Client.class.getClassLoader(),
				new Class<?>[]{EchoInterface.class},
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10240);
						ObjectOutputStream objectInputStream = new ObjectOutputStream(byteArrayOutputStream);
						objectInputStream.writeUTF(method.getName());
						objectInputStream.writeObject(method.getParameterTypes());
						objectInputStream.writeObject(args);
						socketChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
						socketChannel.read(allocate);

						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(allocate.array());
						//BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
						ObjectInputStream result = new ObjectInputStream(byteArrayInputStream);
						allocate.flip();
						return result.readObject();
					}
				});
		System.out.println(instance.echo("a hello world!"));


	}
}
