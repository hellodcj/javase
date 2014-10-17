package com.dcj.core.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
	private int port = 9080;
	private ServerSocket serverSocket;
	public Server() throws IOException {
		serverSocket = new ServerSocket(port,5); //链接请求的队列长度为3
		System.out.println("server start ----");
	}
	
	public void service (){
		while(true){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (socket != null ){
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		//server.service();
	}
}