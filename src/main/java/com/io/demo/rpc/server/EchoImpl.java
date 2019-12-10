package com.io.demo.rpc.server;

/**
 * @author
 */
public class EchoImpl implements EchoInterface {
	@Override
	public String echo(String str) {
		return "rpc echo: "+str;
	}
}
