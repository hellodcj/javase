package com.dcj.serverclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
					
				}
			}
		}
	}
}
