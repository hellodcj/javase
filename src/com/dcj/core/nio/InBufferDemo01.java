package com.dcj.core.nio;

import java.nio.IntBuffer;

import org.junit.Test;

public class InBufferDemo01 {
	@Test
	public void test(){
		IntBuffer buf = IntBuffer.allocate(10); //准备10个大小的缓冲区
		System.out.println("position:"+buf.position()+";limit:"+buf.limit()+"capacity"+buf.capacity());
		int temp[]={3,4,6}; //定义一个int数组
		buf.put(2);
		buf.put(temp);
		System.out.println("position:"+buf.position()+";limit:"+buf.limit()+"capacity"+buf.capacity());
		buf.flip();
		System.out.println("position:"+buf.position()+";limit:"+buf.limit()+"capacity"+buf.capacity());
		System.out.println("缓冲区中的内容为：");
		while(buf.hasRemaining()){
			int x =buf.get();
			System.out.print(x+"、");
		}
	}
	
	/**
	 * 在缓冲区中定义子缓冲区
	 */
	@Test
	public void test02(){
		IntBuffer buf = IntBuffer.allocate(10);
		IntBuffer sub = null; //子缓冲区
		for (int i =0;i<10;i++){
			buf.put(i*2+1); //放入10个奇数，从1开始
		}
		buf.position(2); //主缓冲区指向第3个元素
		buf.limit(6);
		sub = buf.slice(); //开辟子缓冲区
		for (int i =0;i<sub.capacity();i++){
			int temp = sub.get(i); 
			sub.put(temp-1);
		}
		buf.flip();
		buf.limit(buf.capacity());
		System.out.println("主缓冲区内的内容为");
		while (buf.hasRemaining()){
			int x = buf.get();
			System.out.print(x+"、");
		}
	}
}
