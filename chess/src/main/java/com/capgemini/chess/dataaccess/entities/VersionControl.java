package com.capgemini.chess.dataaccess.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class VersionControl {

	@Version
	@Column(name = "version", columnDefinition = "INTEGER DEFAULT 1", nullable = false)
	private Integer version = 1;
	
	@Column(name = "date_creation", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, unique = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	@Column(name = "date_last_modification", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, unique = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastModified;
	
	@PrePersist
	public void setDatesBeforePersist() {
		if (this.dateCreated == null) {
			this.dateCreated = new Date();
		}
		this.dateLastModified = new Date();
	}
	
	@PreUpdate
	public void setDateLastModifiedBeforeUpdate() {
		this.dateLastModified = new Date();
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}
}
