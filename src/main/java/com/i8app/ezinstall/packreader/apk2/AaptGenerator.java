package com.i8app.ezinstall.packreader.apk2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Logger;

import com.i8app.ezinstall.packreader.Util;

public class AaptGenerator {
	
	private static Logger log = Logger.getLogger(AaptGenerator.class.getName());
	
	private static String 
		TMP 	= System.getProperty("java.io.tmpdir"),
		OS 		= System.getProperty("os.name").toLowerCase();
	
	static {
		if(!TMP.endsWith(File.separator))	{
			TMP += File.separator;
		}
	}
	
	private AaptGenerator()	{}
	
	private static AaptGenerator instance;
	
	public static synchronized AaptGenerator getInstance()	{
		if(instance == null)
			instance = new AaptGenerator();
		return instance;
	}
	
	/**
	 * 返回已有aapt, 如不存在, 则创建
	 * @return
	 */
	public File generate()	{
		if(OS.startsWith("win"))	{
			return forWin();
		} else if(OS.startsWith("linux"))	{	
			return forLinux();
		} else	{
			throw new RuntimeException("不支持的操作系统:" + OS);
		}
	}
	
	
	private File forWin()	{
		File aaptFile = new File(TMP + "aapt.exe"); 
		if(!aaptFile.exists())	{
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("aapt.exe");
			try {
				Util.copyFile(is, aaptFile);
				log.info("已将AAPT解析工具复制到本地:" + aaptFile);
			} catch (IOException e) {
				throw new RuntimeException("复制aapt失败, 目标文件夹:" + TMP, e);
			}
		}
		return aaptFile;
	}
	
	private File forLinux()	{
		File aaptFile = new File(TMP + "aapt"); 
		if(!aaptFile.canExecute())	{
			doForLinux(aaptFile);
		}
		
		if(!aaptFile.canExecute())	{
			aaptFile = new File(TMP + "aapt_" + UUID.randomUUID().toString());
			doForLinux(aaptFile);
		} 
		return aaptFile;
	}
	
	private void doForLinux(File aaptFile)	{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("aapt");
		try {
			Util.copyFile(is, aaptFile);
			log.info("已将AAPT解析工具复制到本地:" + aaptFile);
			Util.cmd("chmod", "+x", aaptFile.getAbsolutePath());	//添加可执行权限
		} catch (IOException e) {
			throw new RuntimeException("复制aapt失败, 目标文件:" + TMP, e);
		}
	}
	
	

}
