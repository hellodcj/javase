package com.dcj.serverclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class UnblockingClient {
	private SocketChannel socketChannel = null;
	private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
	private ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
	private Charset charset = Charset.forName("GBK");
	private Selector selector ;
	
	public UnblockingClient() throws IOException {
		socketChannel = SocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(),8000);
		socketChannel.connect(isa);
		socketChannel.configureBlocking(false);//设置为非阻塞模式
		selector = Selector.open();
		System.out.println("与服务器建立连接成功");
	}
	
	/**
	 * 负责接收和发送数据
	 * @throws Exception
	 */
	public void talk() throws Exception{
		socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		while (selector.select()>0){
			Set keys = selector.keys();
			Iterator it = keys.iterator();
			while (it.hasNext()){
				SelectionKey k = null;
				try {
					k = (SelectionKey) it.next();
					it.remove();   //这里为啥要remove呢
					if (k.isReadable()){
						receive(k);
					}
					if (k.isWritable()){
						send(k);
					}
				} catch (Exception e) {
					if (k!=null){
						k.cancel();
						k.channel().close();
					}
				}
			}
		}
	}

	private void send(SelectionKey k) throws IOException {
		//发送sendbuffer中的数据
		SocketChannel socketchannel = (SocketChannel) k.channel();
		
		synchronized (sendBuffer) {
			sendBuffer.flip();
			socketchannel.write(sendBuffer);
			sendBuffer.compact();
		}
	}

	/**
	 * 接受server端发送的数据，存在receiveBuffer中
	 * @param k
	 * @throws IOException 
	 */
	private void receive(SelectionKey k) throws IOException {
		SocketChannel sc = (SocketChannel) k.channel();
		socketChannel.read(receiveBuffer);// 将数据读入receiveBuffer中
		receiveBuffer.flip();
		String receiveData = charset.decode(receiveBuffer).toString();
		if (receiveData.indexOf("\n")==-1) return;
		String outputData = receiveData.substring(0,receiveData.indexOf("\n")+1);
		if (outputData.equals("echo:bye\r\n")){
			k.cancel();
			sc.close();
			selector.close();
		}
		ByteBuffer temp = charset.encode(outputData);
		receiveBuffer.position(temp.limit());
		receiveBuffer.compact(); //删除已经输出过的内容
	}
	
	//从控制台输入数据，存放到sendbuffer中，由于talk函数中的send方法，会对sendbuffer进行操作，因此需要同步
	public void readFromUser() throws IOException{
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String msg = null;
		while((msg=bf.readLine())!=null){
			synchronized (sendBuffer) {
				sendBuffer.put(charset.encode(msg+"\r\n"));
			}
			if (msg.equals("bye"))  break;
		}
	}
	
	public void startup() throws Exception{
		final UnblockingClient client = new UnblockingClient();
		Thread receiveThread = new Thread(new Runnable() {
			public void run() {
				try {
					client.readFromUser();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		receiveThread.start();
		client.talk();
	}
}
