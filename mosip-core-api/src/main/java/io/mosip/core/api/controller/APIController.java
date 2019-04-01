package io.mosip.core.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.mosip.core.api.dao.ResidentRepo;
import io.mosip.core.api.dao.VINRepo;
import io.mosip.core.api.model.Resident;
import io.mosip.core.api.response.AddressResponse;
import io.mosip.core.api.response.UINReprintResponse;
import io.mosip.core.api.response.VINGeneration;
import io.mosip.core.api.response.VINRevoke;

@RestController
@RequestMapping("/mosip/core/v1.0/api")
public class APIController {
	
	@Autowired
	VINRepo vinrepo;
	
	@Autowired
	ResidentRepo residentrepo;
	
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	@RequestMapping("/test")
	public String test() {
		return "Working";
	}
	
	
	@RequestMapping(value="/vin/generate" ,method=RequestMethod.POST)
	public VINGeneration genVINPOST(@RequestParam(value="uin", defaultValue="NA") String uin) {
		if(uin==null||uin.equalsIgnoreCase("NA")) {
			return new VINGeneration(uin , 0L,"Failed","Invalid UIN",sdf.format(new Date(System.currentTimeMillis())));
		}else {
			Resident res = residentrepo.findById(uin).orElse(null);
			if(res != null) {
				VINGeneration vingen = new VINGeneration(uin , System.currentTimeMillis(),"OK","Accepted",sdf.format(new Date(System.currentTimeMillis())));
				vinrepo.save(vingen);
				return vingen;			
			}
			else {
				return new VINGeneration(uin , 0L,"Failed","No UIN Exists",sdf.format(new Date(System.currentTimeMillis())));
			}
		
		}
	}
	

	@RequestMapping(value="/vin/generate" ,method=RequestMethod.GET)
	public VINGeneration genVINGET(@RequestParam(value="uin", defaultValue="NA") String uin) {
		if(uin==null||uin.equalsIgnoreCase("NA")) {
			return new VINGeneration(uin , 0L,"Failed","Invalid UIN",sdf.format(new Date(System.currentTimeMillis())));
			
		}else {
			Resident res = residentrepo.findById(uin).orElse(null);
			if(res != null) {
				VINGeneration vingen = new VINGeneration(uin , System.currentTimeMillis(),"OK","Accepted",sdf.format(new Date(System.currentTimeMillis())));
				vinrepo.save(vingen);
				return vingen;			
			}
			else {
				return new VINGeneration(uin , 0L,"Failed","No UIN Exists",sdf.format(new Date(System.currentTimeMillis())));
			}
			
		}
		
	}
	
	
	@RequestMapping(value="/vin/revoke" ,method=RequestMethod.POST)
	public VINRevoke revVINPOST(@RequestParam(value="vin", defaultValue="0") Long vin) {
		if(vin==null||vin.longValue()<=0) {
			return new VINRevoke("Failed","Invalid UIN",sdf.format(new Date(System.currentTimeMillis())));
		}else {
		return new VINRevoke("OK","VIN Revoked Successfully",sdf.format(new Date(System.currentTimeMillis())));
		}
	}
	

	@RequestMapping(value="/vin/revoke" ,method=RequestMethod.GET)
	public VINRevoke revVINGET(@RequestParam(value="uin", defaultValue="NA") String uin) {
		if(uin==null||uin.equalsIgnoreCase("NA")) {
			return new VINRevoke("Failed","Invalid vin",sdf.format(new Date(System.currentTimeMillis())));
		}else {
			VINGeneration vingen = vinrepo.findById(uin).orElse(null);
			if(vingen != null) {
				vinrepo.deleteById(uin);
			    return new VINRevoke("OK","VIN Revoked Successfully",sdf.format(new Date(System.currentTimeMillis())));
			}
			else {
			    return new VINRevoke("Failed","No VIN Generated",sdf.format(new Date(System.currentTimeMillis())));

			}
			
		}
	}
	
	
	
	@RequestMapping(value="/uin/reprint" ,method=RequestMethod.POST)
	public UINReprintResponse requestReprintPOST(@RequestParam(value="uin", defaultValue="NA") String uin) {
		if(uin==null||uin.equalsIgnoreCase("NA")) {
			return new UINReprintResponse(null,"Failed","Invalid UIN",sdf.format(new Date(System.currentTimeMillis())));
		}
		else {
			Resident res = residentrepo.findById(uin).orElse(null);
			if(res != null) {	
				return new UINReprintResponse(res,"OK","Reprint Successfully",sdf.format(new Date(System.currentTimeMillis())));			
			}
			else {
				return new UINReprintResponse(res,"Failed","UIN Not Exists",sdf.format(new Date(System.currentTimeMillis())));
			}
			
		}
	}
	

	@RequestMapping(value="/uin/reprint" ,method=RequestMethod.GET)
	public UINReprintResponse requestReprintGET(@RequestParam(value="uin", defaultValue="NA") String uin) {
		if(uin==null||uin.equalsIgnoreCase("NA")) {
			return new UINReprintResponse(null,"Failed","Invalid UIN",sdf.format(new Date(System.currentTimeMillis())));
		}
		else {
			Resident res = residentrepo.findById(uin).orElse(null);
			if(res != null) {	
				return new UINReprintResponse(res,"OK","Reprint Successfully",sdf.format(new Date(System.currentTimeMillis())));			
			}
			else {
				return new UINReprintResponse(res,"Failed","UIN Not Exists",sdf.format(new Date(System.currentTimeMillis())));
			}
			
		}
	}
	
	@RequestMapping(value="/uin/update" ,method=RequestMethod.POST)
	public AddressResponse updateRequest(@RequestBody Resident resident) {
		AddressResponse resp=validateData(resident);
		if(resp.getStatus().equalsIgnoreCase("Failed"))return resp;
		else {
			residentrepo.save(resident);
			return resp;
		}
		
	}
	//
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
			resp.setMessage("Request Recieved");
			resp.setStatus("Success");
		}
		return resp;
	}

}
