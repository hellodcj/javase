package com.dcj.core.basictype;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

/**
 * 从文件中读取name-value对，并以此来创建对象，存放到hashmap中
 * @author dcj
 *
 */
public class ObjectPoolFacotry {
	
	private Map<String,Object> objectPool = new HashMap();
	
	
	public void initPool (String filename) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			Properties pro = new Properties();
			//从输入流中获取配置文件
			pro.load(fis);
			for (String name : pro.stringPropertyNames()){
				String classname = pro.getProperty(name);
				Object o =this.createObject(classname);
				objectPool.put(name, o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fis!=null) fis.close();
		}
	}


	//根据类的名称创建实例
	private Object createObject(String classname) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class clazz = Class.forName(classname);
		return clazz.newInstance();
	}
	
	@Test
	public void test() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
		initPool("E:\\workspace02\\javase\\src\\com\\dcj\\core\\basictype\\obj.txt");
		System.out.println(objectPool.get("b"));
	}
}
