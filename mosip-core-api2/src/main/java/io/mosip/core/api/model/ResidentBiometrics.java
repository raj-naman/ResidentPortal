package io.mosip.core.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ResidentBiometrics 
{
	@Id
	private String uin;
	private String fingerprint;
	private String iris;
	private String biometrics_status;
	
	public ResidentBiometrics() {
		super();
	}

	public ResidentBiometrics(String uin, String fingerprint, String iris, String biometrics_status) {
		super();
		this.uin = uin;
		this.fingerprint = fingerprint;
		this.iris = iris;
		this.biometrics_status = biometrics_status;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getIris() {
		return iris;
	}

	public void setIris(String iris) {
		this.iris = iris;
	}

	public String getBiometrics_status() {
		return biometrics_status;
	}

	public void setBiometrics_status(String biometrics_status) {
		this.biometrics_status = biometrics_status;
	}

	
	

}
