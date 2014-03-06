package com.i8app.ezinstall.packreader;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Util {
	
	/**
	 * 关闭多个流
	 * @param closeable
	 */
	public static void closeStream(Closeable...closeable)	{
		for(Closeable c : closeable)	{
			try {
				if(c != null)	c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String cmd(String...cmd) throws IOException	{
		Process process = null;
		BufferedReader br = null;
		ProcessBuilder mBuilder = new ProcessBuilder();
		mBuilder.redirectErrorStream(true);
		try {
			process = mBuilder.command(cmd).start();
			InputStream is = process.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, "utf8"));
			StringBuilder sb = new StringBuilder();
			String s;
			while((s=br.readLine()) != null)
				sb.append(s).append("\n");
			return sb.toString();
		} finally	{
			if(process != null)	process.destroy();
			Util.closeStream(br);
		}
		
	}
	
	public static void copyFile(InputStream is, File target) throws IOException	{
		FileChannel outChan = null;
		try {
			outChan = new FileOutputStream(target).getChannel();
			ReadableByteChannel inChan = Channels.newChannel(is);
			ByteBuffer buf = ByteBuffer.allocate(1024);
			while(true)	{
				int len = inChan.read(buf);
				if(len == -1)	break;
				buf.flip();
				outChan.write(buf);
				buf.clear();
			}
		} finally	{
			Util.closeStream(is, outChan);
		}
	}

}
