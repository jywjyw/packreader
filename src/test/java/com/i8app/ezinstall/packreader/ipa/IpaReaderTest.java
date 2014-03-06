package com.i8app.ezinstall.packreader.ipa;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.i8app.ezinstall.packreader.PackReadException;
import com.i8app.ezinstall.packreader.ipa.IpaReader;
import com.i8app.ezinstall.packreader.ipa.ItunesMetadata;
import com.i8app.ezinstall.packreader.ipa.NSObject;
import com.i8app.ezinstall.packreader.ipa.PropertyListParser;

public class IpaReaderTest {

    @Test
    public void testGetMetadataFromItunesplist() throws Exception {
    	URL url = this.getClass().getResource("Air_Video_2.4.9.ipa");
    	File ipa = new File(url.getFile());
        ItunesMetadata metadata = new IpaReader().read(ipa);
        Assert.assertEquals(metadata.getItemId(), "306550020");
        Assert.assertEquals(metadata.getBundleId(), "com.inmethod.AirVideo");
        Assert.assertEquals(metadata.getGenre(), "工具");
        Assert.assertEquals(metadata.getBundleVersion(), "2.4.9");
        Assert.assertEquals(metadata.getReleaseDate(), "2009-03-04T04:16:03Z");
        Assert.assertEquals(metadata.getAppleId(), "aya_iiii@yahoo.cn");
        Assert.assertEquals(metadata.getPurchaseDate(), "2011-10-05T03:19:06Z");
        System.out.println(metadata);
    }

    @Test
    public void testGetMetadataFromInfoplist() throws Exception {
    	URL url = this.getClass().getResource("111.ipa");
    	File ipa = new File(url.getFile());
    	ItunesMetadata meta = new IpaReader().read(ipa);
    	
    	Assert.assertEquals(meta.getBundleVersion(), "2.4.9");
    	Assert.assertEquals(meta.getBundleId(), "com.inmethod.AirVideo");
    	Assert.assertEquals(meta.getMinOsVersion(), "3.1");
    	Assert.assertEquals(meta.getItemName(), "AirVideo");
    	System.out.println(meta);
    }
    
    @Test
	public void test2()	throws Exception	{
		BufferedInputStream bis = new BufferedInputStream(
				this.getClass().getResourceAsStream("Info-encrypt.plist"));
		ItunesMetadata meta = new ItunesMetadata();
		System.out.println(new IpaReader().parsePlist(bis, meta));
		bis.close();
	}
	
	@Test
	public void test3() throws Exception	{
		BufferedInputStream bis = new BufferedInputStream(
				this.getClass().getResourceAsStream("Info-clear.plist"));
		ItunesMetadata meta = new ItunesMetadata();
		System.out.println(new IpaReader().parsePlist(bis, meta));
		bis.close();
	}
	
    @Test
    public void testxx() throws PackReadException	{
//    	File ipa = new File("D:\\Users\\jing\\Desktop\\新建文件夹\\YOKA时装-全球发行量第一的时装杂志，街拍，搭配，明星时尚超好看！_2.6.0.ipa");
//    	ItunesMetadata meta = new IpaReader().read(ipa);
//    	System.out.println(meta);
    }
    
}
