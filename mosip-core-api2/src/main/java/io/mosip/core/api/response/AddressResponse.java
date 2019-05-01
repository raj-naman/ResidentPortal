package io.mosip.core.api.response;

public class AddressResponse {
	
	
	private String uin;
	private String status;
	private String message;
	private String timestamp;
	
	
	public AddressResponse() {}
	
	

	@Override
	public String toString() {
		return "AddressResponse [uin=" + uin + ", status=" + status + ", message=" + message + ", timestamp="
				+ timestamp + "]";
	}



	public AddressResponse(String uin, String status, String message, String timestamp) {
		super();
		this.uin = uin;
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}



	public String getUin() {
		return uin;
	}


	public void setUin(String string) {
		this.uin = string;
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
	
	

}
