package com.i8app.ezinstall.packreader.apk2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;
import java.util.zip.ZipFile;

import com.i8app.ezinstall.packreader.PackReadException;
import com.i8app.ezinstall.packreader.Util;


/**
 * 使用google的aapt工具解析apk.
 * 缺点,需要使用aapt, 64位linux上aapt需要使用32位库, 安装复杂.
 * 
 * 安装包必须包含整数的versioncode, 和合法的versionName, 否则视为无效安装包
 */
public class ApkReader2 {
	private static Logger log = Logger.getLogger(ApkReader2.class.getName());
	private File aaptFile;
	
	/**
	 * 使用默认的aapt文件
	 */
	public ApkReader2()	{
		this.aaptFile = AaptGenerator.getInstance().generate();
	}
	
	/**
	 * 使用指定的aapt
	 * @param aapt
	 */
	public ApkReader2(File aapt)	{
		this.aaptFile = aapt;
	}
	
	/**
	 * 主方法
	 * @param apk
	 * @return
	 * @throws PackReadException
	 */
	public ApkInfo read(File apk) throws PackReadException {
		return read(apk, false);
	}
	
	
	/**
	 * 主方法
	 * @param apk
	 * @param readIconToBytes 是否将最大的icon读入到apkInfo.icon中
	 * @return
	 * @throws PackReadException
	 */
	public ApkInfo read(File apk, boolean readIconToBytes) throws PackReadException {
		if(!apk.isFile() || !apk.exists())		{
			throw new PackReadException(new FileNotFoundException("无法找到apk文件:" + apk.getAbsolutePath()));
		}
		BufferedReader br = null;
		try {
			String result = Util.cmd(aaptFile.getAbsolutePath().replace(".exe", ""),
								"d", "badging", apk.getAbsolutePath());
			br = new BufferedReader(new StringReader(result));
			String tmp = br.readLine();
			
			if(tmp.contains("error while loading shared libraries:"))	{
				throw new UnsupportedOperationException("aapt运行失败,请检查linux系统是否已安装aapt依赖库:" + tmp);
			}
			if(tmp.contains("no version information available"))	{
				tmp = br.readLine();	//linux上32位库安装不完整时, 会提示警告信息. 跳过
			}
			
			if (tmp == null || !tmp.startsWith("package")) {
				throw new PackReadException("解析apk失败:" + tmp + "...");
			}
			ApkInfo apkInfo = new ApkInfo();
			apkInfo.setFileLength(apk.length());
			do{
				setApkInfoProperty(apkInfo, tmp);	//把原先读的一行先解析出来
			} while ((tmp = br.readLine()) != null);
			
			if(readIconToBytes)	{
				apkInfo.setIcon(readFile(apk, apkInfo.chooseLargestIcon()));
			}
			return postHandle(apkInfo);
		} catch (Exception e) {
			throw new PackReadException(e.getMessage(), e);
		} finally	{
			Util.closeStream(br);
		}
	}
	
	/**
	 * 从apk中读取文件至byte[]
	 * @param apk
	 * @param fileName
	 * @return 文件的byte[]
	 * @throws IOException
	 */
	public byte[] readFile(File apk, String fileName) throws IOException	{
		BufferedInputStream bis = null;
		ZipFile zip = null;
		try {
			zip = new ZipFile(apk);
			bis = new BufferedInputStream(zip.getInputStream(zip.getEntry(fileName)));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			while(bis.read(b) != -1){
				baos.write(b);
			}
			return baos.toByteArray();
		} finally	{
			try	{
				if(zip != null)	zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Util.closeStream(bis);
		}
	}
	
	/**
	 * 设置APK的属性信息。
	 * 
	 * @param apkInfo
	 * @param source
	 */
	private void setApkInfoProperty(ApkInfo apkInfo, String source) {
		if (source.startsWith("package")) {
			splitPackageInfo(apkInfo, source);
		} else if(source.startsWith("launchable-activity")){
			apkInfo.setLaunchableActivity(getPropertyInQuote(source));
		} else if (source.startsWith("sdkVersion")) {
			apkInfo.setSdkVersion(getPropertyInQuote(source));
		} else if (source.startsWith("targetSdkVersion")) {
			apkInfo.setTargetSdkVersion(getPropertyInQuote(source));
		} else if (source.startsWith("uses-permission")) {
			apkInfo.addToUsesPermissions(getPropertyInQuote(source));
		} else if(source.startsWith("application-label-zh_CN:"))	{
			apkInfo.setApplicationLabel_zh_CN(getPropertyInQuote(source));
		} else if(source.startsWith("application-label-zh:"))	{
			apkInfo.setApplicationLabel_zh(getPropertyInQuote(source));
		} else if (source.startsWith("application-label:")) {
			apkInfo.setApplicationLabel(getPropertyInQuote(source));
		} else if (source.startsWith("application-icon-120:")) {
			apkInfo.setApplication_icon_120(getPropertyInQuote(source));
		} else if (source.startsWith("application-icon-160:")) {
			apkInfo.setApplication_icon_160(getPropertyInQuote(source));
		} else if (source.startsWith("application-icon-240:")) {
			apkInfo.setApplication_icon_240(getPropertyInQuote(source));
		} else if (source.startsWith("application-icon-320:")) {
			apkInfo.setApplication_icon_320(getPropertyInQuote(source));
		} else if (source.startsWith("application-icon-65535:")) {
			apkInfo.setApplication_icon_65535(getPropertyInQuote(source));
		} else if (source.startsWith("application:")) {
			String[] rs = source.split("( icon=')|'");
			apkInfo.setApplicationIcon(rs[rs.length - 1]);
		} else if (source.startsWith("uses-feature")) {
			apkInfo.addToFeatures(getPropertyInQuote(source));
		} else if (source.startsWith("uses-implied-feature")) {
			apkInfo.addToImpliedFeatures(getFeature(source));
		} 
	}

	private ImpliedFeature getFeature(String source) {
		String[] result = source.split("(:')|(',')|'");
		ImpliedFeature impliedFeature = new ImpliedFeature(result[1], result[2]);
		return impliedFeature;
	}

	/**
	 * 返回出格式为name: 'value'中的value内容。
	 * @param source
	 * @return
	 */
	private String getPropertyInQuote(String source) {
		int index = source.indexOf("'") + 1;
		return source.substring(index, source.indexOf('\'', index));
	}

	/**
	 * 分离出包名、版本等信息。
	 * 
	 * @param apkInfo
	 * @param packageSource
	 */
	private void splitPackageInfo(ApkInfo apkInfo, String packageSource) {
		String[] packageInfo = packageSource.split("(: )|(=')|(' )|'");
		apkInfo.setPackageName(packageInfo[2]);
		apkInfo.setVersionCode(Integer.parseInt(packageInfo[4]));
		apkInfo.setVersionName(packageInfo[6]);
	}
	
	/**
	 * 对apkInfo最终属性进行校验
	 * @param a
	 * @return
	 */
	private ApkInfo postHandle(ApkInfo a)	{
		if(a.chooseChineseLabel() == null)	//有的apk没有任何名称
			a.setApplicationLabel("");
		return a;
	}
	
	/**
	 * 使用maven打包后, 整个可执行jar的入口
	 * @param args 被测试的apk地址
	 * @throws PackReadException
	 */
	public static void main(String[] args) throws PackReadException {
		ApkInfo ai = new ApkReader2().read(new File(args[0]));
		System.out.println(ai);
	}
	
}
