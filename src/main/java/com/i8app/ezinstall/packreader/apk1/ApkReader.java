package com.i8app.ezinstall.packreader.apk1;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.i8app.ezinstall.packreader.PackReadException;

/**
 * 使用纯java代码解析androidManifest.xml.
 * 缺点, 不能读icon, 部分versionCode不能读
 */
public class ApkReader {
	
	public ApkMetaData read(File file) throws PackReadException	{
		if(!file.exists() || !file.isFile())
			throw new PackReadException(file.getAbsolutePath() + ":找到文件或不是apk文件");
		
		ZipFile zip = null;
		InputStream is = null;
		try {
			zip = new ZipFile(file);
			is = zip.getInputStream(zip.getEntry("AndroidManifest.xml"));
			Document dm = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(new ByteArrayInputStream(AXMLPrinter2.parse(is).getBytes("UTF-8")));
			Element el = (Element)dm.getElementsByTagName("manifest").item(0);
			return new ApkMetaData(Integer.parseInt(el.getAttribute("android:versionCode")), //可能为空串
					el.getAttribute("android:versionName"), 
					el.getAttribute("package"), 
					file.length());
		} catch(Exception e)	{
			throw new PackReadException("无法解析apk文件:" + file.getAbsolutePath(),e);
		} finally	{
			try {
				if(is!=null)	is.close();	//释放zip文件流
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(zip != null)	zip.close();	//释放zip文件流
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public static void main(String[] args) throws PackReadException {
		ApkMetaData a = new ApkReader().read(new File("c:/users/jing/desktop/apkipa/zhihuiyun.apk"));
		System.out.println(a);
	}
}
