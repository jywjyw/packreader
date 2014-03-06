package com.i8app.ezinstall.packreader.apk1;

public class ApkMetaData {
	protected Integer versionCode;
	protected String versionName, packageName;
	protected long fileLength;	//文件长度

	public ApkMetaData() {
		super();
	}

	public ApkMetaData(Integer versionCode, String versionName,
			String packageName, long fileLength) {
		super();
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.packageName = packageName;
		this.fileLength = fileLength;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	@Override
	public String toString() {
		return "ApkMetaData [versionCode=" + versionCode + ", versionName="
				+ versionName + ", packageName=" + packageName + "]";
	}

}
