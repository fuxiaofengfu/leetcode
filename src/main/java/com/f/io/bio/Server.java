package com.f.io.bio;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080);
		while(true){
			Socket accept = serverSocket.accept();
			System.out.println(accept);
			System.out.println(accept.hashCode());
			InputStream inputStream = accept.getInputStream();
			String s = IOUtils.toString(inputStream);
			System.out.println(s);
		}
	}
}
