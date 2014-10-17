package com.dcj.core.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

class Client{
	//模拟请求
	public void doRequest() throws Exception{
		final int length = 100;
		String host = "localhost";
		int port =9080;
		Socket[] socket = new Socket[length];
		for (int i =0;i<length;i++){
			try {
				socket[i] = new Socket(host,port); //试图建立100次链接
				System.out.println(i+"次链接成功");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Thread.sleep(1000);
		
		//断开链接
		for (int i =0;i<length;i++){
				socket[i].close();
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		Client c = new Client ();
		c.doRequest();
	}
}

