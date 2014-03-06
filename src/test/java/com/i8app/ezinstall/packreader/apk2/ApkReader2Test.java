package com.i8app.ezinstall.packreader.apk2;

import java.io.File;

import org.junit.Test;

import com.i8app.ezinstall.packreader.PackReadException;


public class ApkReader2Test {
	
	@Test
	public void testUseDefaultAapt() throws PackReadException	{
		File f = new File("c:/users/jing/desktop/leshi.apk");
//		File f = new File("d:/users/jing/downloads/20130818214145198.apk");
		ApkInfo ai = new ApkReader2().read(f, false);
		System.out.println("---  " + ai.chooseChineseLabel());
		System.out.println("---  " + ai.getPackageName());
		System.out.println("---  " + ai.getVersionName());
		System.out.println("---  " + ai.getVersionCode());
		System.out.println(ai);
	}

	@Test
	public void test2UseAssignedAapt() throws PackReadException	{
//		File f = new File("d:/users/jing/downloads/20130818214145198.apk");
//		File aapt = new File("d:/users/jing/desktop/aapt_123.exe");
//		ApkInfo ai = new ApkReader2(aapt).read(f, false);
//		System.out.println(ai);
	}
	
}
