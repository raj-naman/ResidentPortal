package io.mosip.core.api.response;

public class BiometricsResponse 
{
	private String status;
	private String uin;
	private String biometrics_status;
	private String timestamp;
	public BiometricsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BiometricsResponse(String status, String uin, String biometrics_status, String timestamp) {
		super();
		this.status = status;
		this.uin = uin;
		this.biometrics_status = biometrics_status;
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUin() {
		return uin;
	}
	public void setUin(String uin) {
		this.uin = uin;
	}
	public String getBiometrics_status() {
		return biometrics_status;
	}
	public void setBiometrics_status(String biometrics_status) {
		this.biometrics_status = biometrics_status;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "BiometricsResponse [status=" + status + ", uin=" + uin + ", biometrics_status=" + biometrics_status
				+ ", timestamp=" + timestamp + "]";
	}
	

}
