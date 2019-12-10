package com.f.io.bio;

import java.io.IOException;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("127.0.0.1", 8080);
		socket.setSendBufferSize(1);
		System.out.println(socket.getLocalPort());
		System.out.println(socket.getPort());
		socket.getOutputStream().write("hello world!".getBytes());
	}
}
