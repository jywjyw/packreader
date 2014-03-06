package com.i8app.ezinstall.packreader.ipa;

/**
 * Apple应用元数据模型
 * 
 * @author Sun
 * @version ItunesMetadata.java 2011-11-1 上午10:54:19
 */
public class ItunesMetadata {
	/**
	 * 软件id, 软件名, iconUrl, 类别, 软件版本，软件版本, 发布日期, appId, 购买日期, 软件包名, 最低版本要求
	 */
	private String itemId, itemName, iconUrl, genre, bundleShortVersionString, bundleVersion,
			releaseDate, appleId, purchaseDate, bundleId, minOsVersion,
			bundleDisplayName, //有的使用这个做中文名, 有的用itemName做中文名
			artistName;
	
	private Integer softwareVersionExternalIdentifier; 
	
	private long fileLength;	//文件大小
	
	/**
	 * 挑选名称
	 * @return
	 */
	public String chooseName()	{
		if(bundleDisplayName != null)
			return bundleDisplayName;
		if(itemName != null)
			return itemName;
		return null;
	}

	/**
	 * 挑选版本
	 * @return
	 */
	public String chooseVersion()	{
		if(bundleShortVersionString != null)
			return bundleShortVersionString;
		if(bundleVersion != null)
			return bundleVersion;
		return null;
	}
	
	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getIconUrl() {
		return this.iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getBundleVersion() {
		return this.bundleVersion;
	}

	public void setBundleVersion(String bundleVersion) {
		this.bundleVersion = bundleVersion;
	}

	public String getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAppleId() {
		return this.appleId;
	}

	public void setAppleId(String appleId) {
		this.appleId = appleId;
	}

	public String getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getMinOsVersion() {
		return minOsVersion;
	}

	public void setMinOsVersion(String minOsVersion) {
		this.minOsVersion = minOsVersion;
	}
	
	public Integer getSoftwareVersionExternalIdentifier() {
		return softwareVersionExternalIdentifier;
	}

	public void setSoftwareVersionExternalIdentifier(
			Integer softwareVersionExternalIdentifier) {
		this.softwareVersionExternalIdentifier = softwareVersionExternalIdentifier;
	}

	public String getBundleShortVersionString() {
		return bundleShortVersionString;
	}

	public void setBundleShortVersionString(String bundleShortVersionString) {
		this.bundleShortVersionString = bundleShortVersionString;
	}
	
	public String getBundleDisplayName() {
		return bundleDisplayName;
	}

	public void setBundleDisplayName(String bundleDisplayName) {
		this.bundleDisplayName = bundleDisplayName;
	}
	
	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	@Override
	public String toString() {
		return "ItunesMetadata [itemId=" + itemId + ", itemName=" + itemName
				+ ", iconUrl=" + iconUrl + ", genre=" + genre
				+ ", bundleShortVersionString=" + bundleShortVersionString
				+ ", bundleVersion=" + bundleVersion + ", releaseDate="
				+ releaseDate + ", appleId=" + appleId + ", purchaseDate="
				+ purchaseDate + ", bundleId=" + bundleId + ", minOsVersion="
				+ minOsVersion + ", softwareVersionExternalIdentifier="
				+ softwareVersionExternalIdentifier + "]";
	}
}
