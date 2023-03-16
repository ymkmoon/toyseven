package com.toyseven.ymk.common.model.logging;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="logging_event")
public class LoggingEventEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="event_id", nullable = false, updatable = false, insertable = false)
	private Long eventId;
	
	@Column(name = "formatted_message", nullable = false)
	private String formattedMessage;
	
	@Column(name = "logger_name", nullable = false)
	private String loggerName;
	
	@Column(name = "level_string", nullable = false)
	private String levelString;
	
	@Column(name = "thread_name", nullable = true)
	private String threadName;
	
	@Column(name = "reference_flag", nullable = true)
	private int referenceFlag;
	
	@Column(name = "arg0", nullable = true)
	private String arg0;
	
	@Column(name = "arg1", nullable = true)
	private String arg1;
	
	@Column(name = "arg2", nullable = true)
	private String arg2;
	
	@Column(name = "arg3", nullable = true)
	private String arg3;
	
	@Column(name = "caller_filename", nullable = false)
	private String callerFilename;
	
	@Column(name = "caller_class", nullable = false)
	private String callerClass;
	
	@Column(name = "caller_method", nullable = false)
	private String callerMethod;
	
	@Column(name = "caller_line", nullable = false)
	private String callerLine;
	
	@Column(name = "timestmp", nullable = false)
	private Long timeStmp;
}
