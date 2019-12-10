package com.io.demo.bio;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo {

	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = new ServerSocket(8080);
		while (true) {
			Socket client = serverSocket.accept();
			System.out.println("client connection ...");
			String string = IOUtils.toString(client.getInputStream());
			System.out.println(string);
		}
	}
}
