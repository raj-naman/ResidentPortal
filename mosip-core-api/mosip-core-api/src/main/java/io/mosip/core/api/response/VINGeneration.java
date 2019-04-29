package io.mosip.core.api.response;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VINGeneration {
	@Id
	private String uin;
	private long vin;
	private String status;
	private String message;
	private String timestamp;
	
	public VINGeneration() {
		
	}
	
	
	public VINGeneration(String uin , long vin, String status, String message, String timestamp) {
		super();
		this.uin = uin;
		this.vin = vin;
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getUin() {
		return uin;
	}


	public void setUin(String uin) {
		this.uin = uin;
	}


	public long getVin() {
		return vin;
	}

	public void setVin(long vin) {
		this.vin = vin;
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
		return "VINGeneration [uin=" + uin + ", vin=" + vin + ", status=" + status + ", message=" + message
				+ ", timestamp=" + timestamp + "]";
	}

	
	
	
	
}
