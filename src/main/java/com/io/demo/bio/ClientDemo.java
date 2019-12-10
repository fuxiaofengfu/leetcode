package com.io.demo.bio;

import java.io.IOException;
import java.net.Socket;

public class ClientDemo {

	public static void main(String[] args) throws IOException, InterruptedException {

		while(true){
			Socket socket = new Socket("127.0.0.1", 8080);

			socket.getOutputStream().write("hello world".getBytes());
			
		}
	}
}
