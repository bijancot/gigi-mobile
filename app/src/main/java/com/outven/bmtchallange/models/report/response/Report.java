package com.outven.bmtchallange.models.report.response;

import com.google.gson.annotations.SerializedName;

public class Report{

	@SerializedName("report_id")
	private String reportId;

	@SerializedName("entry")
	private String entry;

	@SerializedName("time")
	private String time;

	@SerializedName("day")
	private String day;

	@SerializedName("status")
	private String status;

	public void setReportId(String reportId){
		this.reportId = reportId;
	}

	public String getReportId(){
		return reportId;
	}

	public void setDay(String day){
		this.day = day;
	}

	public String getDay(){
		return day;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}