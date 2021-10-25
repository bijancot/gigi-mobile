package com.outven.bmtchallange.models.report.response;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("report")
	private Report report;

	public void setReport(Report report){
		this.report = report;
	}

	public Report getReport(){
		return report;
	}
}