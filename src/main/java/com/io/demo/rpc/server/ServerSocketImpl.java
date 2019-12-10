package com.io.demo.rpc.server;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketImpl {

	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		ServerSocketChannel server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress(8080));
		ByteBuffer buffer = ByteBuffer.allocate(10240);
		while (true) {
			SocketChannel accept = server.accept();
			accept.read(buffer);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer.array());
			buffer.clear();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			String methodName = objectInputStream.readUTF();
			Class<?>[] argTypes = (Class<?>[]) objectInputStream.readObject();
			Object[] margs = (Object[]) objectInputStream.readObject();

			EchoImpl echo = new EchoImpl();
			Method method = echo.getClass().getMethod(methodName, argTypes);
			Object result = method.invoke(echo, margs);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(result);
			accept.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
		}
	}
}
