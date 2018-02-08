package com.bornsoft.pub.result.apix;

import java.util.List;

import com.bornsoft.utils.base.BornInfoBase;
import com.google.gson.annotations.SerializedName;

public class ApixLocationBasedResult extends ApixResultBase {

	/**
	 */
	private static final long serialVersionUID = -4022308045278782701L;

	@SerializedName("data")
	private List<LocationInfo> locationList;

	public List<LocationInfo> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<LocationInfo> locationList) {
		this.locationList = locationList;
	}

	public static class LocationInfo extends BornInfoBase {

		private String lon;// 经度
		private String lat;// 纬度
		private String acc;//
		private String addr;// 地址
		private String prov;// 省
		private String city;// 市
		private String dist;// 区
		private String town;//
		private String street;// 路
		private String number;

		public String getLon() {
			return lon;
		}

		public void setLon(String lon) {
			this.lon = lon;
		}

		public String getLat() {
			return lat;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public String getAcc() {
			return acc;
		}

		public void setAcc(String acc) {
			this.acc = acc;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getProv() {
			return prov;
		}

		public void setProv(String prov) {
			this.prov = prov;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getDist() {
			return dist;
		}

		public void setDist(String dist) {
			this.dist = dist;
		}

		public String getTown() {
			return town;
		}

		public void setTown(String town) {
			this.town = town;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

	}

}
