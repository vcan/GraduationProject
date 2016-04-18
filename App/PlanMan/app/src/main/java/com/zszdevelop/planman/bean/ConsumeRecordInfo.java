package com.zszdevelop.planman.bean;

import com.zszdevelop.planman.base.BaseBean;

public class ConsumeRecordInfo extends BaseBean{

	private int consumeRecordId;
	private long consumeRecordTime;
	private int consumeCC;
	private int consumeRecordType;
	private String consumeRecordContent;
	public int getConsumeRecordId() {
		return consumeRecordId;
	}
	public void setConsumeRecordId(int consumeRecordId) {
		this.consumeRecordId = consumeRecordId;
	}

	public int getConsumeCC() {
		return consumeCC;
	}
	public void setConsumeCC(int consumeCC) {
		this.consumeCC = consumeCC;
	}
	public int getConsumeRecordType() {
		return consumeRecordType;
	}

	public long getConsumeRecordTime() {
		return consumeRecordTime;
	}

	public void setConsumeRecordTime(long consumeRecordTime) {
		this.consumeRecordTime = consumeRecordTime;
	}

	public void setConsumeRecordType(int consumeRecordType) {
		this.consumeRecordType = consumeRecordType;
	}
	public String getConsumeRecordContent() {
		return consumeRecordContent;
	}
	public void setConsumeRecordContent(String consumeRecordContent) {
		this.consumeRecordContent = consumeRecordContent;
	}
	
	
}
