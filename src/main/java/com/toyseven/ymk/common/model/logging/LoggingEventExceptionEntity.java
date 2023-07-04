package com.toyseven.ymk.common.model.logging;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity(name="logging_event_exception")
public class LoggingEventExceptionEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5806272008624683171L;

	@Id
	@ManyToOne(targetEntity = LoggingEventEntity.class)
	@JoinColumn(name="event_id", referencedColumnName = "event_id", nullable = false)
	private String eventId;
	
	@Id
	@Column(name = "i", nullable = false)
	private int i;
	
	@Column(name = "trace_line", nullable = false)
	private String traceLine;
}
