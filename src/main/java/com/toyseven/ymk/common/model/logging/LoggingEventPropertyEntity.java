package com.toyseven.ymk.common.model.logging;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity(name="logging_event_property")
public class LoggingEventPropertyEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -151889225488994760L;

	@Id
	@ManyToOne(targetEntity = LoggingEventEntity.class)
	@JoinColumn(name="event_id", referencedColumnName = "event_id", nullable = false)
	private String eventId;
	
	@Id
	@Column(name = "mapped_key", nullable = false)
	private String mappedKey;
	
	@Column(name = "mapped_value", nullable = true)
	private String mappedValue;
}
