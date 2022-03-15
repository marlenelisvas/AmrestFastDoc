package com.nga.xtendhr.fastDoc.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nga.xtendhr.fastDoc.SF.DestinationClient;
import com.nga.xtendhr.fastDoc.utility.ConstantManager;

@RestController
@RequestMapping(value = ConstantManager.uploadAPI)
public class AttachmentController {

	private static final Logger logger = LoggerFactory.getLogger(DocuSignController.class);
	public static final String destinationName = "prehiremgrSFTest_Permission";
	
	@PostMapping(value = "/upload")
	public ResponseEntity<?> uploadAttachment(HttpServletRequest httpRequest, @RequestBody Map<String, String> payload)
			throws IOException, NamingException, URISyntaxException {
		httpRequest.getUserPrincipal().getName();
		logger.debug("uploadAttachment : " + payload);

		DestinationClient destClient = new DestinationClient();
		destClient.setDestName(destinationName);
		destClient.setHeaderProvider();
		destClient.setConfiguration();
		destClient.setDestConfiguration();
		destClient.setHeaders(destClient.getDestProperty("Authentication"));

		logger.debug("uploadAttachment Destination Name : " + destinationName);
		/* Get the User Details of the logged In User */
		HttpResponse attachmentResponse = destClient.callDestinationPOST("/Attachment", "", replaceKeys(payload));
		String attachmentResponseJsonString = EntityUtils.toString(attachmentResponse.getEntity(), "UTF-8");
		JSONObject attachmentResponseObject = (JSONObject) JSONValue.parse(attachmentResponseJsonString);
		attachmentResponseObject = (JSONObject) attachmentResponseObject.get("d");

		logger.debug("Upload Attachment JSONObject : " + attachmentResponseObject);

		return ResponseEntity.status(HttpStatus.OK).body("Attachment is uploaded successfully.");
	}
	
	@SuppressWarnings("unchecked")
	private String replaceKeys(Map<String, String> payload) {
		JSONObject obj = new JSONObject();

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uri", "Attachment");
		obj.put("__metadata", jsonObj);

		obj.put("userId", payload.get("userId"));
		obj.put("fileName", payload.get("fileName"));
		obj.put("description", payload.get("description"));		
		obj.put("fileContent", payload.get("fileContent"));
		obj.put("viewable", true);
		obj.put("deletable", false);
		obj.put("moduleCategory", ConstantManager.uploadAttachmentModuleCategory);
		obj.put("module", ConstantManager.uploadAttachmentModule);
		

		logger.error("Attachment Object: " + obj.toJSONString());
		return obj.toJSONString();
	}

}
