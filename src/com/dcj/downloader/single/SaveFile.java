package com.dcj.downloader.single;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import org.junit.Test;

public class SaveFile {
	//下载的人物列表
	private Vector vDownload = new Vector();
	//下载文件名列表
	private Vector vFilename = new Vector();
	//缓存区大小
	private static int BUFFER_SIZE= 8096;
	
	/**
	 * 增加下载列表项 
	 * @param url
	 * @param filename
	 */
    public void addItem(String url, String filename) {  
    	vDownload.add(url);  
        vFilename.add(filename);  
    }  
    
    /**
     * 下载列表中的所有任务
     */
    public void downLoadByList() {  
        String url = null;  
        String filename = null;  
        // 按列表顺序保存资源  
        for (int i = 0; i < vDownload.size(); i++) {  
            url = (String) vDownload.get(i);  
            filename = (String) vFilename.get(i);  
            try {  
                saveToFile(url, filename);  
            } catch (IOException err) {  
                System.out.println("资源[" + url + "]下载失败!!!");  
            }  
        }  
            System.out.println("下载完成!!!");  
    } 
    
    /**
     * 根据url和文件名，将文件存入文件
     * @param destUrl
     * @param fileName
     * @throws IOException 
     */
    public void saveToFile(String destUrl, String fileName) throws IOException{
    	FileOutputStream fos = null;  
        BufferedInputStream bis = null;  
        HttpURLConnection connection = null;  
        URL url = null;  
        byte[] buf = new byte[BUFFER_SIZE]; 
        int size= 0;//用于BufferedInputStream的read方法
        
        //初始化url
        url = new URL(destUrl);
        //打开连接
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        //获取网络输入流  
        bis = new BufferedInputStream(connection.getInputStream());
        //建立文件  
        fos = new FileOutputStream(fileName); 
        while((size=bis.read(buf))!=-1)
        	fos.write(buf,0,size);
        fos.flush();
        //关闭连接
        fos.close();
        bis.close();
        connection.disconnect();
    }
    
    //下载器的主方法入口
    @Test
    public void downloadStarter(){
    	addItem("http://dlc2.pconline.com.cn/filedown_65397_6943182/uIFbHnkV/Kuaizip_Setup_v2.6.68.0.exe", "F:/test.exe");
    	downLoadByList();
    }
}
