package com.dcj.core.basictype;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

/**
 * javabean 和 map 的互相转换
 * @author chengjun
 *
 */
public class BeanMapTest {
	
	@Test
	public void test01(){
		Entity e = new Entity(1,"sdf");
		Map<String,Object> m = transBeanToMap(e);
		for(Entry entry : m.entrySet()){
			System.out.println("key:"+entry.getKey()+"value:"+entry.getValue());
		}
		e = new Entity();
		
		try {
			Map<String,Object> c = new HashMap();
			c.put("a",3);
			c.put("b","ccc");
			BeanUtils.populate(e, c);
			for(Entry entry : c.entrySet()){
				System.out.println("key:"+entry.getKey()+"value:"+entry.getValue());
			}
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void test02(){
		Map m = new HashMap ();
		m.put("a", "av");
		m.put("b", "bv");
		Map m2 = new HashMap ();
		m2.put("a", "m2av");
		m2.put("c", "cv");
		m2.putAll(m);
		try {
			BeanUtils.copyProperties(m2, m);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println(m2.toString());
	}
	
	/**
	 * 将一个 JavaBean 对象转化为一个  Map 
	 * @param obj
	 * @return
	 */
	public Map<String,Object> transBeanToMap(Object obj){
		if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors ){
				String key = propertyDescriptor.getName();
				//过滤class属性
				if (! key.equals("class")){
					//得到property的getter方法
					Method method = propertyDescriptor.getReadMethod();
					Object value = method.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return map;
	}
	
	/**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
	public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}

class Entity{
	private int a;
	private String b;
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public Entity(int a, String b) {
		super();
		this.a = a;
		this.b = b;
	}
	public Entity() {
		// TODO Auto-generated constructor stub
	}
}
