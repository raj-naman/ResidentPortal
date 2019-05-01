package io.mosip.core.api.response;

import java.util.Date;

public class VINRevoke {
	private String status;
	private String message;
	private String timestamp;
	
	public VINRevoke() {
		
	}
	
	
	public VINRevoke(String status, String message, String timestamp) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
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
		return "VINGeneration [status=" + status + ", message=" + message + ", timestamp=" + timestamp
				+ "]";
	}
	
	
	
}
