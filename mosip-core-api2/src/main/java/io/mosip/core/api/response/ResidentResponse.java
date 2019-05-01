package io.mosip.core.api.response;

import io.mosip.core.api.model.Resident;

public class ResidentResponse 
{
	private Resident resident;
	private String status;
	private String message;
	private String timestamp;
	public ResidentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResidentResponse(Resident resident, String status, String message, String timestamp) {
		super();
		this.resident = resident;
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}
	public Resident getResident() {
		return resident;
	}
	public void setResident(Resident resident) {
		this.resident = resident;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "ResidentResponse [resident=" + resident + ", status=" + status + ", message=" + message + ", timestamp="
				+ timestamp + "]";
	}
	
	

}
