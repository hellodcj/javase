package com.dcj.serverclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阻塞方式的服务器  一个请求一个线程
 * @author chengjun
 *
 */
public class BlockingServer {
	private int port = 8000;
	private ServerSocketChannel serverSocketChannel = null;
	private ExecutorService executorService = null;
	private static final int POOL_SIZE=5;
	
	public BlockingServer() throws IOException {
		//1.创建线程池
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
		//2.创建ServerSocketChannel对象
		serverSocketChannel = ServerSocketChannel.open();
		//3.使得服务器关闭后重启的时候可以重用相同的端口
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("服务器启动成功");
	}


	/**
	 * 处理客户的链接
	 * @throws IOException 
	 */
	public void service() throws IOException{
		while (true){
			SocketChannel socketChannel = null;
			socketChannel = serverSocketChannel.accept();
			executorService.execute(new Handler(socketChannel));
		}
	}
}

/**
 * 线程处理类
 * @author chengjun
 *
 */
class Handler implements Runnable{
	private SocketChannel channel = null;

	public Handler(SocketChannel channel) {
		super();
		this.channel = channel;
	}

	@Override
	public void run() {
		try {
			handler(channel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handler(SocketChannel channel) throws IOException{
		//1.获得与SocketChannel连接的socket对象
		Socket socket = channel.socket();
		System.out.println("收到来自于客户端的链接"+socket.getInetAddress()+socket.getPort());
		
		BufferedReader br = getReader(socket);
		PrintWriter pw = getWriter(socket);
		
		String msg = null;
		while((msg=br.readLine())!=null){
			System.out.println(msg);
			pw.println(echo(msg));
			//判断结束条件
			if (msg.equals("bye")) break;
		}
	}

	private String echo(String msg) {
		return "echo"+msg;
	}

	/**
	 * 获得socket的printwriter
	 * @param socket
	 * @return
	 * @throws IOException 
	 */
	private PrintWriter getWriter(Socket socket) throws IOException {
		
		OutputStream out = socket.getOutputStream();
		return new PrintWriter(out);
	}

	/**
	 * 获得socket的bufferedReader
	 * @param socket
	 * @return
	 */
	private BufferedReader getReader(Socket socket) {
		try {
			InputStream in =socket.getInputStream();
			return new BufferedReader(new InputStreamReader(in));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		new BlockingServer().service();
	}
}