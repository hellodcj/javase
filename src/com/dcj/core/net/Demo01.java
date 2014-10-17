package com.dcj.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.net.SocketServer;

public class Demo01 {
	private ServerSocket serverSocket;
	
	public void service() throws IOException{
		while(true){
			Socket socket = null;
			socket = serverSocket.accept();
			Thread workThread = new Thread(new Handler(socket));
			workThread.start();
		}
	}
}


class Handler implements Runnable{
	private Socket socket;
	
	public Handler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg =null;
			while ((msg=br.readLine())!= null) {
				if (msg.equals("bye")) break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
