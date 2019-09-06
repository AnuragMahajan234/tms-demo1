package org.yash.rms.report.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class PLReport {

	List<ResignedResourcePLReport> resignedResourcePLReportList;
	List<BUResourcesPLReport> bUResourcesPLReportList;
	List<ContractResourcePLReport> contractResourcePLReportList;
	List<BorrowedResourcesPLReport> borrowedResourcesPLReportList;

	public List<ResignedResourcePLReport> getResignedResourcePLReportList() {
		return resignedResourcePLReportList;
	}

	public void setResignedResourcePLReportList(List<ResignedResourcePLReport> resignedResourcePLReportList) {
		this.resignedResourcePLReportList = resignedResourcePLReportList;
	}

	public List<BUResourcesPLReport> getbUResourcesPLReportList() {
		return bUResourcesPLReportList;
	}

	public void setbUResourcesPLReportList(List<BUResourcesPLReport> bUResourcesPLReportList) {
		this.bUResourcesPLReportList = bUResourcesPLReportList;
	}

	public List<ContractResourcePLReport> getContractResourcePLReportList() {
		return contractResourcePLReportList;
	}

	public void setContractResourcePLReportList(List<ContractResourcePLReport> contractResourcePLReportList) {
		this.contractResourcePLReportList = contractResourcePLReportList;
	}

	public List<BorrowedResourcesPLReport> getBorrowedResourcesPLReportList() {
		return borrowedResourcesPLReportList;
	}

	public void setBorrowedResourcesPLReportList(List<BorrowedResourcesPLReport> borrowedResourcesPLReportList) {
		this.borrowedResourcesPLReportList = borrowedResourcesPLReportList;
	}

	public static String toJsonArray(Map<String, Object> report) {

		return new JSONSerializer().include("resignedResourcePLReportList", "contractResourcePLReportList", "borrowedResourcesPLReportList", "bUResourcesPLReportList").exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(report);
	}

}
