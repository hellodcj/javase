package com.dcj.core.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

import com.dcj.core.collection.MapEntryDemo;

public class FileChannelDemo01 {
	
	@Test
	public void test(){
		FileChannel inchannel = null;
		FileChannel outchannel = null;
		File f = new File("filetestin.txt");
		try {
			//通过文件输入流，获得通道
			inchannel = new FileInputStream (f).getChannel();
			//将inchannel中的数据，全部映射到buffer中
			MappedByteBuffer buffer = inchannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
