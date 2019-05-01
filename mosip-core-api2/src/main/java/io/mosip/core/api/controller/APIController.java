package io.mosip.core.api.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.mosip.core.api.dao.ResidentBiometricsRepo;
import io.mosip.core.api.dao.ResidentRepo;
import io.mosip.core.api.dao.VINRepo;
import io.mosip.core.api.model.Resident;
import io.mosip.core.api.model.ResidentBiometrics;
import io.mosip.core.api.response.AddressResponse;
import io.mosip.core.api.response.BiometricsResponse;
import io.mosip.core.api.response.ResidentResponse;
import io.mosip.core.api.response.UINReprintResponse;
import io.mosip.core.api.response.VINGeneration;
import io.mosip.core.api.response.VINRevoke;
import io.mosip.core.api.service.NotificationService;
import io.mosip.core.api.utility.GenerateResidentReport;

@RestController
@RequestMapping("/mosip/core/v1.0/api")
public class APIController {
	
	@Autowired
	VINRepo vinrepo;
	
	@Autowired
	ResidentRepo residentrepo;
	
	@Autowired
	NotificationService notificationservice;
	
	@Autowired
	ResidentBiometricsRepo residentbiometricsrepo;
	
	
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	@RequestMapping("/test")
	public String test() {
		return "Working";
	}
	
	/*------  Generate OTP for UIN   -------*/
	@RequestMapping(value="/generate/otp" ,method=RequestMethod.GET)
	public ResponseEntity<VINGeneration> sendOTP(@RequestParam(value="uin", defaultValue="NA") String uin) {
		if(uin==null||uin.equalsIgnoreCase("NA")) {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new VINGeneration(uin , 0L,"Failed","Invalid UIN OTP Failed",sdf.format(new Date(System.currentTimeMillis()))));
		}else {
			Resident res = residentrepo.findById(uin).orElse(null);
			if(res != null) {
				long vin=System.currentTimeMillis();
				VINGeneration vingen = new VINGeneration(uin ,2020L,"OK","OTP SENT",sdf.format(new Date(System.currentTimeMillis())));
				//vinrepo.save(vingen);
				String email=res.getEmail();
				notificationservice.sendOTP(vin, email, "2020");
				 return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(vingen);
				//return vingen;			
			}
			else {
				return ResponseEntity.ok()
						.header("Access-Control-Allow-Origin", "*")
				        .body(new VINGeneration(uin , 0L,"Failed","No UIN Exists",sdf.format(new Date(System.currentTimeMillis()))));
			}
		
		}
		
	}
	
	/*------  Generate VID for UIN   -------*/
	
	@RequestMapping(value="/vin/generate/do" ,method=RequestMethod.GET)
	public ResponseEntity<VINGeneration> genVINGET(@RequestParam(value="uin", defaultValue="NA") String uin,
			@RequestParam(value="sentOTP", defaultValue="NA") String sentOTP,@RequestParam(value="enterOTP", defaultValue="NA") String enterOTP) {
		if(uin==null||uin.equalsIgnoreCase("NA")||sentOTP==null||sentOTP.equalsIgnoreCase("NA")||enterOTP==null||enterOTP.equalsIgnoreCase("NA")) {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new VINGeneration(uin , 0L,"Failed","Invalid INPUT",sdf.format(new Date(System.currentTimeMillis()))));
		}else {
			Resident res = residentrepo.findById(uin).orElse(null);
			if(res != null) {
				if(sentOTP.equals(enterOTP)) {
				long vin=System.currentTimeMillis();
				VINGeneration vingen = new VINGeneration(uin , vin,"OK","Accepted",sdf.format(new Date(System.currentTimeMillis())));
				vinrepo.save(vingen);
				String email=res.getEmail();
				notificationservice.sendNotification(vin,email);
				 return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(vingen);
				//return vingen;
			}else {
				//otp mismatch
				VINGeneration vingen = new VINGeneration(uin , 0L,"Failed","OTP Validation Failed",sdf.format(new Date(System.currentTimeMillis())));
				 return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(vingen);
				
			}
			}
			else {
				return ResponseEntity.ok()
						.header("Access-Control-Allow-Origin", "*")
				        .body(new VINGeneration(uin , 0L,"Failed","No UIN Exists",sdf.format(new Date(System.currentTimeMillis()))));
			}
		
		}
		
	}
	
	/*------  Revoke VID for UIN   -------*/
	
	@RequestMapping(value="/vin/revoke/do" ,method=RequestMethod.GET)
	public ResponseEntity<VINRevoke> revVINGET(@RequestParam(value="uin", defaultValue="NA") String uin,
			@RequestParam(value="sentOTP", defaultValue="NA") String sentOTP,@RequestParam(value="enterOTP", defaultValue="NA") String enterOTP) {
		if(uin==null||uin.equalsIgnoreCase("NA")||sentOTP==null||sentOTP.equalsIgnoreCase("NA")||enterOTP==null||enterOTP.equalsIgnoreCase("NA")) {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new VINRevoke("Failed","Invalid Input",sdf.format(new Date(System.currentTimeMillis()))));
		}else {
			VINGeneration vingen = vinrepo.findById(uin).orElse(null);
			Resident res = residentrepo.findById(uin).orElse(null);
			if(vingen != null) {
				if(sentOTP.equals(enterOTP)) {
					vinrepo.deleteById(uin);
				    VINRevoke vinrev = new VINRevoke("OK","VIN Revoked Successfully",sdf.format(new Date(System.currentTimeMillis())));
				 return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(vinrev);
				//return vingen;
			}else {
				//otp mismatch
			    VINRevoke vinrev = new VINRevoke("Failed","OTP Validation Failed",sdf.format(new Date(System.currentTimeMillis())));
				 return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(vinrev);
				
			}
			}
			else {
				return ResponseEntity.ok()
						.header("Access-Control-Allow-Origin", "*")
				        .body(new VINRevoke("Failed","No VIN Exixts",sdf.format(new Date(System.currentTimeMillis()))));
			}
		
		}
		
	}
	
	/*------  Lock Biometrics for UIN   -------*/
	
	@RequestMapping(value="/uin/lockBiometrics" ,method=RequestMethod.GET)
	public ResponseEntity<BiometricsResponse> lockBioGET(@RequestParam(value="uin", defaultValue="NA") String uin,
			@RequestParam(value="sentOTP", defaultValue="NA") String sentOTP,@RequestParam(value="enterOTP", defaultValue="NA") String enterOTP) {
		if(uin==null||uin.equalsIgnoreCase("NA")||sentOTP==null||sentOTP.equalsIgnoreCase("NA")||enterOTP==null||enterOTP.equalsIgnoreCase("NA")) {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new BiometricsResponse("Failed","Invalid Input","No Status" ,sdf.format(new Date(System.currentTimeMillis()))));
		}else {
			ResidentBiometrics res = residentbiometricsrepo.findById(uin).orElse(null);
			if(res != null){
				if(sentOTP.equals(enterOTP)) {
					if(res.getBiometrics_status().equals("unlocked")) {	
						res.setBiometrics_status("locked");
						residentbiometricsrepo.save(res);
						BiometricsResponse bioresponse = new BiometricsResponse("OK", uin ,"Biometrics Locked", sdf.format(new Date(System.currentTimeMillis())));			
					
						return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(bioresponse);
					}
					else {
						BiometricsResponse bioresponse = new BiometricsResponse("OK", uin ,"Biometrics Already Locked", sdf.format(new Date(System.currentTimeMillis())));			
						
						return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(bioresponse);
					}
				//return vingen;
			 }
			else {
			//otp mismatch
			 BiometricsResponse bioresponse = new BiometricsResponse("OK", uin ,"OTP Validation Failed", sdf.format(new Date(System.currentTimeMillis())));			
			 return ResponseEntity.ok()
				        .header("Access-Control-Allow-Origin", "*")
				        .body(bioresponse);
			
			}
		}
		else {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new BiometricsResponse("OK", uin ,"No UIN Exists", sdf.format(new Date(System.currentTimeMillis()))));	
		}
		
		}
		
		
	}
	
	/*------  Unlock Biometrics for UIN   -------*/
	
	@RequestMapping(value="/uin/unlockBiometrics" ,method=RequestMethod.GET)
	public ResponseEntity<BiometricsResponse> unlockBioGET(@RequestParam(value="uin", defaultValue="NA") String uin,
			@RequestParam(value="sentOTP", defaultValue="NA") String sentOTP,@RequestParam(value="enterOTP", defaultValue="NA") String enterOTP) {
		if(uin==null||uin.equalsIgnoreCase("NA")||sentOTP==null||sentOTP.equalsIgnoreCase("NA")||enterOTP==null||enterOTP.equalsIgnoreCase("NA")) {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new BiometricsResponse("Failed","Invalid Input","No Status" ,sdf.format(new Date(System.currentTimeMillis()))));
		}else {
			ResidentBiometrics res = residentbiometricsrepo.findById(uin).orElse(null);
			if(res != null){
				if(sentOTP.equals(enterOTP)) {
					if(res.getBiometrics_status().equals("locked")) {	
						res.setBiometrics_status("unlocked");
						residentbiometricsrepo.save(res);
						BiometricsResponse bioresponse = new BiometricsResponse("OK", uin ,"Biometrics Unocked", sdf.format(new Date(System.currentTimeMillis())));			
					
						return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(bioresponse);
					}
					else {
						BiometricsResponse bioresponse = new BiometricsResponse("OK", uin ,"Biometrics Already Unlocked", sdf.format(new Date(System.currentTimeMillis())));			
						
						return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(bioresponse);
					}
				//return vingen;
			 }
			else {
			//otp mismatch
			 BiometricsResponse bioresponse = new BiometricsResponse("OK", uin ,"OTP Validation Failed", sdf.format(new Date(System.currentTimeMillis())));			
			 return ResponseEntity.ok()
				        .header("Access-Control-Allow-Origin", "*")
				        .body(bioresponse);
			
			}
		}
		else {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new BiometricsResponse("OK", uin ,"No UIN Exists", sdf.format(new Date(System.currentTimeMillis()))));	
		}
		
		}
		
		
	}
	
	/*--------    UIN Reprint Success   ----------*/
	
	@RequestMapping(value="/uin/reprint/success" ,method=RequestMethod.GET)
	public ResponseEntity<UINReprintResponse> uinreprintGET(@RequestParam(value="uin", defaultValue="NA") String uin,
			@RequestParam(value="sentOTP", defaultValue="NA") String sentOTP,@RequestParam(value="enterOTP", defaultValue="NA") String enterOTP) {
		if(uin==null||uin.equalsIgnoreCase("NA")||sentOTP==null||sentOTP.equalsIgnoreCase("NA")||enterOTP==null||enterOTP.equalsIgnoreCase("NA")) {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new UINReprintResponse(null,"Failed","Invalid UIN",sdf.format(new Date(System.currentTimeMillis()))));
		}else {
			Resident res = residentrepo.findById(uin).orElse(null);
			if(res != null){
				if(sentOTP.equals(enterOTP)) {
		
						UINReprintResponse uinresponse = new UINReprintResponse(null,"OK","Valid UIN",sdf.format(new Date(System.currentTimeMillis())));		
					
						return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(uinresponse);
					
				//return vingen;
				}
				else {
				//otp mismatch
					UINReprintResponse uinresponse = new UINReprintResponse(null,"Failed","OTP Validation Failed",sdf.format(new Date(System.currentTimeMillis())));		
					return ResponseEntity.ok()
					        .header("Access-Control-Allow-Origin", "*")
					        .body(uinresponse);
				
				}
			}
			else {
				return ResponseEntity.ok()
						.header("Access-Control-Allow-Origin", "*")
				        .body(new UINReprintResponse(null,"Failed","NO UIN exixts",sdf.format(new Date(System.currentTimeMillis()))));	
			}
		
		}
		
		
	}
	
	/*--------    Print UIN Card   ----------*/

	
	@RequestMapping(value = "/uin/reprint", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> residentReport(@RequestParam(value="uin", defaultValue="NA") String uin) throws IOException {
		
		Resident res = residentrepo.findById(uin).orElse(null);

		ByteArrayInputStream bis = GenerateResidentReport.residentReport(res);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=residentreport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
	
	
	@RequestMapping(value="/resident" ,method=RequestMethod.GET)
	public ResponseEntity<ResidentResponse> residentGET(@RequestParam(value="uin", defaultValue="NA") String uin) {
		if(uin==null||uin.equalsIgnoreCase("NA")) {
			return ResponseEntity.ok()
					.header("Access-Control-Allow-Origin", "*")
			        .body(new ResidentResponse(null , "Failed","Invalid Input" ,sdf.format(new Date(System.currentTimeMillis()))));
		}
		else {
			Resident res = residentrepo.findById(uin).orElse(null);
			if(res != null){
				
				ResidentResponse resresponse = new ResidentResponse(res , "OK", "Resident Fetched", sdf.format(new Date(System.currentTimeMillis())));			
					
				return ResponseEntity.ok()
			        .header("Access-Control-Allow-Origin", "*")
					.body(resresponse);
			 }
			
			else {
				return ResponseEntity.ok()
						.header("Access-Control-Allow-Origin", "*")
				        .body(new ResidentResponse(null , "Failed","No UIN Exists" ,sdf.format(new Date(System.currentTimeMillis()))));
			}
		
		}
		
		
	}
	
	

//
//	@PostMapping(value="/uin/update" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	public ResponseEntity<AddressResponse> updateRequest(Resident resident) {
//		AddressResponse resp=validateData(resident);
//		if(resp.getStatus().equalsIgnoreCase("Failed")) {
//			return ResponseEntity.ok()
//					.header("Access-Control-Allow-Origin", "*")
//			        .body(new AddressResponse(resident.getUin() , "Failed","Error in Updating" ,sdf.format(new Date(System.currentTimeMillis()))));
//		}
//		else {
//			residentrepo.save(resident);
//			return ResponseEntity.ok()
//					.header("Access-Control-Allow-Origin", "*")
//			        .body(new AddressResponse(resident.getUin() , "OK","Address Updated" ,sdf.format(new Date(System.currentTimeMillis()))));
//		}
//		
//	}
	
	@PostMapping(value="/uin/update" ,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public AddressResponse updateRequest(Resident resident) {
		Resident res = residentrepo.findById(resident.getUin()).orElse(null);
		System.out.println(res);
		if(res != null) {	
			AddressResponse resp=validateData(resident);
			if(resp.getStatus().equalsIgnoreCase("Failed")) {
				return resp;
			}
			else {
				residentrepo.save(resident);
				return resp;
			}	
		}
		else {
			return new AddressResponse(resident.getUin(),"Failed","UIN Not Exists",sdf.format(new Date(System.currentTimeMillis())));
		}
		
		
	}

	public AddressResponse validateData(Resident resident) {
		AddressResponse resp=new AddressResponse();
		resp.setUin(resident.getUin());
		resp.setTimestamp(sdf.format(new Date(System.currentTimeMillis())));
		if(resident.getCity()==null||resident.getCity().length()<=0) {
			resp.setMessage("Invalid City");
			resp.setStatus("Failed");
		}else if(resident.getDist()==null||resident.getDist().length()<=0) {
			resp.setMessage("Invalid District");
			resp.setStatus("Failed");
		}else if(resident.getStreet()== null ||resident.getStreet().length()<=0) {
			resp.setMessage("Invalid Street");
			resp.setStatus("Failed");
		}else if(resident.getState()== null ||resident.getState().length()<=0) {
			resp.setMessage("Invalid State");
			resp.setStatus("Failed");
		}else if(resident.getHouse_no()== null ||resident.getHouse_no().length()<=0) {
			resp.setMessage("Invalid House No.");
			resp.setStatus("Failed");
		}else {
			resp.setMessage("Updated");
			resp.setStatus("Success");
		}
		return resp;
	}

}
