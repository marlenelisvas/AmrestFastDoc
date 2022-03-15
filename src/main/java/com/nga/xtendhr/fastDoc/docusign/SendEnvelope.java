package com.nga.xtendhr.fastDoc.docusign;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.CarbonCopy;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.Recipients;
import com.docusign.esign.model.SignHere;
import com.docusign.esign.model.Signer;
import com.docusign.esign.model.Tabs;
import com.nga.xtendhr.fastDoc.utility.CommonVariables;

public class SendEnvelope extends ExampleBase {

	
	public SendEnvelope(ApiClient apiClient) throws IOException {
		super(apiClient);
	}

	/**
	 * method show the usage of
	 * 
	 * @return
	 * @throws ApiException
	 * @throws IOException
	 */
	public EnvelopeSummary sendEnvelope(String base64, String signer_Email1, String signer_Name1, String templateID)
			throws ApiException, IOException {

		this.checkToken();

		EnvelopeDefinition envelopeDefinition = new EnvelopeDefinition();
		envelopeDefinition.setEmailSubject("Please sign this document sent from Fast Doc Application");

		Document doc1 = new Document();
		doc1.setDocumentBase64(base64);
		doc1.setName("Contract");
		doc1.setFileExtension("pdf");
		doc1.setDocumentId("1");

//        Document doc2 = new Document();
//        doc2.setDocumentBase64(new String(Base64.encode(DSHelper.readContent(DOC_2_DOCX))));
//        doc2.setName("Battle Plan");
//        doc2.setFileExtension("docx");
//        doc2.setDocumentId("2");

		// The order in the docs array determines the order in the envelope
		envelopeDefinition.setDocuments(Arrays.asList(doc1)); // doc2, doc3
		// create a signer recipient to sign the document, identified by name and email
		// We're setting the parameters via the object creation
		
		/*
		 * Signer signer2 = new Signer(); signer2.setEmail(DSConfig.SIGNER_EMAIL2);
		 * signer2.setName(DSConfig.SIGNER_NAME2); signer2.setRecipientId("2");
		 * signer2.setRoutingOrder("1");
		 */
		// routingOrder (lower means earlier) determines the order of deliveries
		// to the recipients. Parallel routing order is supported by using the
		// same integer as the order for two or more recipients.

		// create a cc recipient to receive a copy of the documents, identified by name
		// and email
		// We're setting the parameters via setters
		/*
		 * CarbonCopy cc1 = new CarbonCopy(); 
		 * cc1.setEmail(DSConfig.CC_EMAIL);
		 * cc1.setName(DSConfig.CC_NAME); 
		 * cc1.setRoutingOrder("2");
		 * cc1.setRecipientId("2");
		 */
		// Create signHere fields (also known as tabs) on the documents,
		// We're using anchor (autoPlace) positioning
		//
		// The DocuSign platform seaches throughout your envelope's
		// documents for matching anchor strings. So the
		// sign_here_2 tab will be used in both document 2 and 3 since they
		// use the same anchor string for their "signer 1" tabs.

		
		/* java.util.List<SignHere> signHereTabs2 = new ArrayList<SignHere>(); */
		

		/*
		 * SignHere signHereCoapp = new SignHere(); signHereCoapp.setDocumentId("1");
		 * signHereCoapp.setRecipientId("2"); signHereCoapp.setPageNumber("8");
		 * signHereCoapp.setTabLabel("SignHereTab"); signHereCoapp.setXPosition("80");
		 * signHereCoapp.setYPosition("420"); signHereTabs2.add(signHereCoapp);
		 */

		
		

		/*
		 * Tabs tabs2 = new Tabs(); tabs2.setSignHereTabs(signHereTabs2);
		 * signer2.setTabs(tabs2);
		 */

		Recipients recipients = new Recipients();
				
		
		/* Set the Signers only for the Full-Time Contract with Signature*/
		if(templateID.equalsIgnoreCase(CommonVariables.FULL_TIME_CONTRACT)){
			
			/* signers.add(signer2); */
			
			Signer signer1 = new Signer();
			signer1.setEmail(signer_Email1);
			signer1.setName(signer_Name1);
			signer1.setRecipientId("1");
			signer1.setRoutingOrder("1");
			
			java.util.List<SignHere> signHereTabs1 = new ArrayList<SignHere>();
			SignHere signHereApp = new SignHere();
			signHereApp.setDocumentId("1");
			signHereApp.setRecipientId("1");
			signHereApp.setPageNumber("8");
			signHereApp.setTabLabel("SignHereTab");
			signHereApp.setXPosition("300");
			signHereApp.setYPosition("430");
			signHereTabs1.add(signHereApp);
			
			
			Tabs tabs1 = new Tabs();
			tabs1.setSignHereTabs(signHereTabs1);
			signer1.setTabs(tabs1);
			
			List<Signer> signers = new ArrayList<Signer>();
			signers.add(signer1);
			recipients.setSigners(signers);
					
		}
		else{
			CarbonCopy cc1 = new CarbonCopy();
			cc1.setEmail(signer_Email1);
			cc1.setName(signer_Name1);
			cc1.setRecipientId("1");
			cc1.setRoutingOrder("1");
			
			List<CarbonCopy> ccList = new ArrayList<CarbonCopy>();
			ccList.add(cc1);
			recipients.setCarbonCopies(ccList);
		}
		
		
		
		// recipients.setCarbonCopies(Arrays.asList(cc1));
		envelopeDefinition.setRecipients(recipients);
		// Request that the envelope be sent by setting |status| to "sent".
		// To request that the envelope be created as a draft, set to "created"
		envelopeDefinition.setStatus("sent");

		EnvelopesApi envelopeApi = new EnvelopesApi(this.apiClient);
		EnvelopeSummary results = envelopeApi.createEnvelope(this.getAccountId(), envelopeDefinition);

		return results;
	}
}
