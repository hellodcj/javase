package com.dcj.serverclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class BlockingClient {
	private SocketChannel socketChannel= null;

	public BlockingClient() throws IOException {
		socketChannel = SocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
		//在阻塞模式下，将等到连接成功才会返回
		socketChannel.connect(isa);
		System.out.println("与服务器建立连接成功");
	}
	
	public void talk() throws IOException{
		try {
			BufferedReader br = getReader(socketChannel.socket().getInputStream());
			PrintWriter pw = getWriter(socketChannel.socket().getOutputStream());
			BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			while ((msg=localReader.readLine())!=null){
				pw.print(msg);
				if (msg.equals("bye")){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			socketChannel.close();
		}
	}

	private PrintWriter getWriter(OutputStream outputStream) {
		return new PrintWriter(outputStream);
	}

	private BufferedReader getReader(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream));
	}
	
}
