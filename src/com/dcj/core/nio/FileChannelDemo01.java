package com.dcj.core.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FileChannelDemo01 {
	Logger log = Logger.getLogger(getClass());
	@Test
	public void test(){
		FileChannel inchannel = null;
		FileChannel outchannel = null;
		File f = new File("f:/filetestin.txt");
		try {
			//通过文件输入流，获得通道
			inchannel = new FileInputStream (f).getChannel();
			//将inchannel中的数据，全部映射到buffer中
			log.info("文件长度ss为"+f.length());
			System.out.println("文件长度为"+f.length());
			MappedByteBuffer buffer = inchannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
			File of = new File("f:/out.txt");
			if (!of.exists()){
				of.createNewFile();
			}
			outchannel = new FileOutputStream(of).getChannel();
			outchannel.write(buffer);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				inchannel.close();
				outchannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将文件本身的内容追加到文件的末尾
	 */
	@Test
	public void test02(){
		/**
		 * 1.获取File，包装秤RandomAccessFile
		 * 2.获取RandomAccessFile的channel
		 * 3.将channel映射到buffer中
		 * 4.将channel的position指向channel的最后的位置，然后将buffer写入
		 */
		
		FileChannel randomChannel = null;
		File f = new File("f:/filetestin.txt");
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "rw");
			//获取RandomAccessFile对应的channel
			randomChannel = raf.getChannel();
			//将channel中的数据，映射到map中
			ByteBuffer bf = randomChannel.map(FileChannel.MapMode.READ_WRITE, 0, f.length());
			randomChannel.position(f.length());
			bf.put("12345\n".getBytes(), 0, "12345\n".getBytes().length);
			bf.position(0);
			randomChannel.write(bf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
