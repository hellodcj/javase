package com.dcj.serverclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.CharSet;

/**
 * 非阻塞服务器
 * @author chengjun
 *
 */
public class UnBlockingServer {
	private int port = 8000;
	private ServerSocketChannel serverSocketChannel = null;
	private Selector selector = null;
	private static final int POOL_SIZE=5;
	private Charset charSet = Charset.forName("GBK");
	
	public UnBlockingServer() throws IOException {
		//1.创建Selector对象
		selector = Selector.open();
		//2.创建ServerSocketChannel对象
		serverSocketChannel = ServerSocketChannel.open();
		//3.使得服务器关闭后重启的时候可以重用相同的端口
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("服务器启动成功");
	}
	
	public void service() throws IOException {
		//由serverSocketChannel向selector注册接受连接就绪事件，如果就绪，则将SelectionKey对象加入到Selected-keys集合中
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(selector.select()>0){
			Set readyKeys = selector.selectedKeys();
			Iterator it = readyKeys.iterator();
			while (it.hasNext()) {
				SelectionKey selectionKey = null;
				selectionKey = (SelectionKey) it.next();
				it.remove();
				if (selectionKey.isAcceptable()){
					
				}
				if (selectionKey.isReadable()){
					
				}
				if(selectionKey.isWritable()){
					ByteBuffer bf = (ByteBuffer) selectionKey.attachment();
					SocketChannel sc = (SocketChannel) selectionKey.channel();
					bf.flip();  //将position位置设置为0
					CharBuffer cb = charSet.decode(bf);
					String data = cb.toString();
					if (data.indexOf("\r\n")==-1) return;
					String outputData = data.substring(0,data.indexOf("\n")+1);
					ByteBuffer outputBuffer = charSet.encode("echo"+outputData);
					while(outputBuffer.hasRemaining()){
						sc.write(outputBuffer);
					}
					
					ByteBuffer temp = charSet.encode(outputData);
					bf.position(temp.limit());
					bf.compact(); //删除已经处理的字符串
					
					if (outputData.equals("bye\r\n")){
						selectionKey.cancel();
						sc.close();
						System.out.println("关闭与客户端的连接");
					}
				}
			}
		}
	}
}
