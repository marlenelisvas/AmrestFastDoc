package com.nga.xtendhr.fastDoc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.nga.xtendhr.fastDoc.config.DBConfiguration;

/*
 * AppName: DocGen
 * TableName: DGEN_DOC_TEMPLATE_DETAILS
 * 
 * @author	:	Manish Gupta  
 * @email	:	manish.g@ngahr.com
 * @version	:	0.0.1
 */
@Entity
@Table(name = DBConfiguration.DOC_TEMPLATE_DETAILS, schema = DBConfiguration.SCHEMA_NAME)
@NamedQueries({
		@NamedQuery(name = "DocTemplateDetails.findById", query = "SELECT DTD FROM DocTemplateDetails DTD WHERE DTD.docTemplateId = :docTemplateId"),
		@NamedQuery(name = "DocTemplateDetails.findByName", query = "SELECT DTD FROM DocTemplateDetails DTD WHERE DTD.name = :name"),
		@NamedQuery(name = "DocTemplateDetails.selectAll", query = "SELECT DTD FROM DocTemplateDetails DTD") })
public class DocTemplateDetails {
	@Id
	@Column(name = "\"DOC_TEMPLATE.ID\"", columnDefinition = "VARCHAR(32)")
	private String docTemplateId;

	@Column(name = "\"NAME\"", columnDefinition = "VARCHAR(64)")
	private String name;

	@Column(name = "\"DESCRIPTION\"", columnDefinition = "VARCHAR(64)")
	private String description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "docTemplateDetails", targetEntity = MapGroupTemplates.class, fetch = FetchType.LAZY)
	private List<MapGroupTemplates> mapGroupTemplates;

	public List<MapGroupTemplates> getMapGroupTemplates() {
		return mapGroupTemplates;
	}

	public String getDocTemplateId() {
		return docTemplateId;
	}

	public void setDocTemplateId(String docTemplateId) {
		this.docTemplateId = docTemplateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
