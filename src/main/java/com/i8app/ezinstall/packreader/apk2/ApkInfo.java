/*
 * @(#)ApkInfo.java		       version: 1.0 
 * Date:2012-1-10
 *
 * Copyright (c) 2011 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 */
package com.i8app.ezinstall.packreader.apk2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.i8app.ezinstall.packreader.apk1.ApkMetaData;

/**
 * <B>ApkInfo</B>
 * <p>
 * 该类封装了一个Apk的信息。包括版本号，支持平台，图标，名称，权限，所需设备特性等。
 * </p>
 * 
 * @author CFuture.Geek_Soledad(66704238@51uc.com)
 */
public class ApkInfo extends ApkMetaData{
	
	/**
	 * 支持的android平台最低版本号
	 */
	private String 
		minSdkVersion,	//支持的android平台最低版本号
		sdkVersion,	//支持的SDK版本。
		targetSdkVersion,	//建议的SDK版本
		applicationLabel,	//应用名称
		applicationLabel_zh_CN,	//简体中文应用名
		applicationLabel_zh,	//中文应用名
		applicationIcon,	//图标名
		application_icon_120,
		application_icon_160,
		application_icon_240,
		application_icon_320,
		application_icon_65535,
		launchableActivity;	//启动界面
	
	
	private List<String> 
		usesPermissions,	//apk所需要的权限
		features;	//所需设备特性。
	
	/**
	 * 暗指的特性。
	 */
	private List<ImpliedFeature> impliedFeatures;
	
	private byte[] icon;

	public ApkInfo() {
		this.usesPermissions = new ArrayList<String>();
		this.impliedFeatures = new ArrayList<ImpliedFeature>();
		this.features = new ArrayList<String>();
	}
	
	/**
	 * 选择分辨率最大的图标名
	 */
	public String chooseLargestIcon()	{
		if(application_icon_65535 != null)	return application_icon_65535;
		if(application_icon_320 != null)	return application_icon_320;
		if(application_icon_240 != null)	return application_icon_240;
		if(application_icon_160 != null)	return application_icon_160;
		if(application_icon_120 != null)	return application_icon_120;
		return applicationIcon;
	}
	
	/**
	 * 选择中文标签名
	 */
	public String chooseChineseLabel()	{
		if(this.applicationLabel_zh_CN != null)
			return applicationLabel_zh_CN;
		if(this.applicationLabel_zh != null)
			return applicationLabel_zh;
		return applicationLabel;
	}

	public void addToUsesPermissions(String usesPermission) {
		this.usesPermissions.add(usesPermission);
	}

	public void addToImpliedFeatures(ImpliedFeature impliedFeature) {
		this.impliedFeatures.add(impliedFeature);
	}

	public void addToFeatures(String feature) {
		this.features.add(feature);
	}

	
	
	
	
	

	public String getMinSdkVersion() {
		return minSdkVersion;
	}

	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}

	public List<String> getUsesPermissions() {
		return usesPermissions;
	}

	public void setUsesPermissions(List<String> usesPermissions) {
		this.usesPermissions = usesPermissions;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getTargetSdkVersion() {
		return targetSdkVersion;
	}

	public void setTargetSdkVersion(String targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}

	public String getApplicationLabel() {
		return applicationLabel;
	}

	public void setApplicationLabel(String applicationLabel) {
		this.applicationLabel = applicationLabel;
	}

	public String getApplicationIcon() {
		return applicationIcon;
	}

	public void setApplicationIcon(String applicationIcon) {
		this.applicationIcon = applicationIcon;
	}

	public List<ImpliedFeature> getImpliedFeatures() {
		return impliedFeatures;
	}

	public void setImpliedFeatures(List<ImpliedFeature> impliedFeatures) {
		this.impliedFeatures = impliedFeatures;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public String getLaunchableActivity() {
		return launchableActivity;
	}

	public void setLaunchableActivity(String launchableActivity) {
		this.launchableActivity = launchableActivity;
	}
	
	public String getApplicationLabel_zh_CN() {
		return applicationLabel_zh_CN;
	}

	public void setApplicationLabel_zh_CN(String applicationLabel_zh_CN) {
		this.applicationLabel_zh_CN = applicationLabel_zh_CN;
	}
	
	public String getApplicationLabel_zh() {
		return applicationLabel_zh;
	}

	public void setApplicationLabel_zh(String applicationLabel_zh) {
		this.applicationLabel_zh = applicationLabel_zh;
	}

	public String getApplication_icon_120() {
		return application_icon_120;
	}

	public void setApplication_icon_120(String application_icon_120) {
		this.application_icon_120 = application_icon_120;
	}

	public String getApplication_icon_160() {
		return application_icon_160;
	}

	public void setApplication_icon_160(String application_icon_160) {
		this.application_icon_160 = application_icon_160;
	}

	public String getApplication_icon_240() {
		return application_icon_240;
	}

	public void setApplication_icon_240(String application_icon_240) {
		this.application_icon_240 = application_icon_240;
	}

	public String getApplication_icon_320() {
		return application_icon_320;
	}

	public void setApplication_icon_320(String application_icon_320) {
		this.application_icon_320 = application_icon_320;
	}

	public String getApplication_icon_65535() {
		return application_icon_65535;
	}

	public void setApplication_icon_65535(String application_icon_65535) {
		this.application_icon_65535 = application_icon_65535;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "ApkInfo [minSdkVersion=" + minSdkVersion + ", sdkVersion="
				+ sdkVersion + ", targetSdkVersion=" + targetSdkVersion
				+ ", applicationLabel=" + applicationLabel
				+ ", applicationLabel_zh_CN=" + applicationLabel_zh_CN
				+ ", applicationLabel_zh=" + applicationLabel_zh
				+ ", applicationIcon=" + applicationIcon
				+ ", application_icon_120=" + application_icon_120
				+ ", application_icon_160=" + application_icon_160
				+ ", application_icon_240=" + application_icon_240
				+ ", application_icon_320=" + application_icon_320
				+ ", application_icon_65535=" + application_icon_65535
				+ ", launchableActivity=" + launchableActivity
				+ ", usesPermissions=" + usesPermissions + ", features="
				+ features + ", impliedFeatures=" + impliedFeatures + ", icon="
				+ Arrays.toString(icon) + "]";
	}
}
