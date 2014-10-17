package com.dcj.core.basictype;

import org.junit.Test;

public class ByteEemo {

	@Test
	public void test01(){
		byte a =5;
		a += 5;
	}
	
	@Test
	public void test02(){
		System.out.println(4<<3);
	}
	
	/**
	 * 十进制和十六进制的相互转换
	 */
	@Test
	public void test03(){
		byte b = 'c';
		System.out.println(b & 0xff);
		System.out.println(Integer.toHexString(b & 0xff));
		System.out.println(Integer.parseInt("21", 16));
	}
}
