package com.i8app.ezinstall.packreader.ipa;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.i8app.ezinstall.packreader.PackReadException;

/**
 * ipa文件就是一个zip压缩包, 其中包含一个iTunesMetadata.plist或Info.plist, 即应用的元数据
 * ipa的几种异常情况: 
 * 1. 核心文件不在payload包内,在payload包相邻的文件夹内.
 * 2. Info.plist未加密
 * 3. itunesmetadata.plist的编码格式各不同.
 * 4. ipa包用zip打开失败, 原因不明
 * 
 * 找不到itunesmetadata时,读取Payload/xxx/Info.pplist的4个属性 
 * @author JingYing
 */
public class IpaReader  {
    public static final String ITUNESMETADATA = "iTunesMetadata.plist";

    /**
     * 依次读取itunesmetadata.plist, info.plist, 得到最终的itunesmetadata;
     * @param file ipa文件
     * @return
     * @throws PackReadException
     */
    public ItunesMetadata read(File file) throws PackReadException {
        ZipFile zip = null;
        ItunesMetadata metadata = null;
        ZipEntry[] plists = new ZipEntry[3];	//定义三个变量, 分别存放itunesmetadata, payload/info.plist, app/info.plist;
        Exception exc = null;
        try	{
	        zip = new ZipFile(file);
	        Enumeration<? extends ZipEntry> entries = zip.entries();
	        while (entries.hasMoreElements()) {
	            ZipEntry entry = entries.nextElement();
	            if (entry.getName().equals(ITUNESMETADATA)) {
	            	plists[2] = entry;
	            } else if(entry.getName().matches("Payload/[^/]+[.]app/Info.plist")){	//必须是Payload子目录根下Info.plist
	            	plists[1] = entry;
	            } else if(entry.getName().matches("[^/]+[.]app/Info.plist"))	{//*.app下的Info.plist
	            	plists[0] = entry;
	            }
	        }

	        metadata = new ItunesMetadata();
	        metadata.setFileLength(file.length());
	        for(ZipEntry z : plists)	{
	        	if(z != null)	{
	        		try {
	        			metadata = this.parsePlist(zip.getInputStream(z), metadata);
	        		} catch (Exception e) {
	        			exc = e;	//出现异常时,先存到exc变量中,继续解析
	        		}
	        	} 
	        }
        } catch(Exception e)	{
        	throw new PackReadException(e);
        } finally	{
        	try	{
        		if(zip != null)	zip.close();	//释放zip占用的文件
        	} catch(Exception e)	{
        		e.printStackTrace();
        	}
        }

        if(metadata == null 
        		|| metadata.getBundleId() == null)		{
        	if(exc != null)	{
        		//没有读到bundleId时, 看有没有出现过异常
        		throw new PackReadException(exc);	
        	} else	{
        		//没有读到bundleid时, 该安装包为要定义为无效安装包
        		throw new PackReadException("no iTunesMetadata.plist or Info.plist or bundleId in ipa");
        	}
        }
        return metadata;
    }
    
    /**
     * 解析*.plist文件
     * @param is plist文件
     * @param metadata
     * @return
     * @throws Exception
     */
    public ItunesMetadata parsePlist(InputStream is, ItunesMetadata metadata) throws Exception	{
		NSObject ns = PropertyListParser.parse(is);	//使用dd-plist.jar解析plist加密文件
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		// 关闭解析XML文件时, 通过DTD验证XML的功能, 因为如果网络不通会导致解析失败
		db.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(new StringReader(""));	// 如果直接return null仍然需要联网获取DTD来验证
			}
		});
		Document doc = db.parse(new ByteArrayInputStream(ns.toXMLPropertyList().getBytes("UTF-8")));
		NodeList nodeList = doc.getElementsByTagName("*");
		for (int i = 0, length = nodeList.getLength(); i < length; i++) {	
			Node node = nodeList.item(i);
			if (!node.getNodeName().equals("key")) {
			    continue;
			}
			
			String key = node.getTextContent();
			String value = nodeList.item(i + 1).getTextContent();
			
			if (key.equals("itemId")) {
			    metadata.setItemId(value);
			} else if (key.equals("itemName")) {
			    metadata.setItemName(value);
			} else if (key.equals("softwareIcon57x57URL")) {
			    metadata.setIconUrl(value);
			} else if (key.equals("genre")) {
			    metadata.setGenre(value);
			} else if (key.equals("bundleVersion")) {
			    metadata.setBundleVersion(value);
			} else if (key.equals("releaseDate")) {
			    metadata.setReleaseDate(value);
			} else if (key.equals("appleId")) {
			    metadata.setAppleId(value);
			} else if (key.equals("purchaseDate")) {
			    metadata.setPurchaseDate(value);
			} else if (key.equals("softwareVersionBundleId"))	{
				metadata.setBundleId(value);
			} else if (key.equals("softwareVersionExternalIdentifier"))	{
				try {
					metadata.setSoftwareVersionExternalIdentifier(Integer.parseInt(value));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (key.equals("bundleShortVersionString"))	{
				metadata.setBundleShortVersionString(value);
			} else if (key.equals("bundleDisplayName"))	{
				metadata.setBundleDisplayName(value);
			} else if (key.equals("artistName"))	{
				metadata.setArtistName(value);
				
			} else if (key.equals("CFBundleName")) {
				metadata.setItemName(value);
			} else if (key.equals("CFBundleVersion")) {
				metadata.setBundleVersion(value);
			} else if (key.equals("CFBundleIdentifier")) {
				metadata.setBundleId(value);
			} else if (key.equals("MinimumOSVersion")) {
					metadata.setMinOsVersion(value);
			}
		}
		return metadata;
	}
}
