package com.i8app.ezinstall.packreader.apk1;

import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class ApkReaderTest {
	
	@Test
	public void test() throws Exception	{
		URL url = this.getClass().getResource("111.apk");
		File f  = new File(url.getFile());
		ApkMetaData meta = new ApkReader().read(f);
		Assert.assertEquals(new Integer(1), meta.getVersionCode());
		Assert.assertEquals("1.0.0", meta.getVersionName());
		Assert.assertEquals("viva.reader7242", meta.getPackageName());
		System.out.println(meta);
	}
	
	@Test
	public void test1() throws Exception	{
//		File f  = new File("C:\\Users\\jing\\desktop\\baiduyinle.apk");
//		ApkMetaData meta = new ApkReader().read(f);
////		Assert.assertEquals(new Integer(1), meta.getVersionCode());
////		Assert.assertEquals("1.0.0", meta.getVersionName());
////		Assert.assertEquals("viva.reader7242", meta.getPackageName());
//		System.out.println(meta);
//
////		File f1  = new File("C:\\Users\\jing\\desktop\\沃动上海.apk");
////		ApkMetaData meta1 = new ApkReader().read(f1);
//////		Assert.assertEquals(new Integer(1), meta.getVersionCode());
//////		Assert.assertEquals("1.0.0", meta.getVersionName());
//////		Assert.assertEquals("viva.reader7242", meta.getPackageName());
////		System.out.println(meta1);
	}
	
	

}
