package com.dcj.core.nio;

import java.nio.CharBuffer;

import org.junit.Test;


public class CharBufferDemo01 {
	@Test
	public void test(){
		//构造一个容量为8的charbuffer
		CharBuffer cb = CharBuffer.allocate(8);
		System.out.println("position-->"+cb.position()+";limit-->"+cb.limit()+";capacity-->"+cb.capacity());
		cb.put('a');
		cb.put('b');
		cb.put('c');
		System.out.println("position-->"+cb.position()+";limit-->"+cb.limit()+";capacity-->"+cb.capacity());
		cb.flip();
		System.out.println("position-->"+cb.position()+";limit-->"+cb.limit()+";capacity-->"+cb.capacity());
		char c = cb.get();
		System.out.println("取的数据位:"+c);
		cb.clear();
		System.out.println("position-->"+cb.position()+";limit-->"+cb.limit()+";capacity-->"+cb.capacity());
		cb.put('x');
		cb.put('y');
		cb.put('z');
		System.out.println("position-->"+cb.position()+";limit-->"+cb.limit()+";capacity-->"+cb.capacity());
		for (int i = 0; i < cb.limit(); i++) {
			System.out.println("取的数据位:"+cb.get(i));
		}
		
	}
}
