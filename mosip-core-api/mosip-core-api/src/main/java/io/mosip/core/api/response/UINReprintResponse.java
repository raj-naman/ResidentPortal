package io.mosip.core.api.response;

import io.mosip.core.api.model.Resident;

public class UINReprintResponse {
	Resident resident;
	private String status;
	private String message;
	private String timestamp;
	
	public UINReprintResponse() {
		
	}

	public UINReprintResponse(Resident resident, String status, String message, String timestamp) {
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
		return "UINReprintResponse [resident=" + resident + ", status=" + status + ", message=" + message
				+ ", timestamp=" + timestamp + "]";
	}

	
	
}
