package com.toyseven.ymk.voc.question;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import com.toyseven.ymk.common.model.BaseTimeEntity;
import com.toyseven.ymk.station.StationInformation;
import com.toyseven.ymk.voc.VocCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Proxy(lazy = false)
@Entity(name="voc_question")
public class VocQuestion extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private long id;
	
//	@Column(name = "category", nullable = false, updatable = true, insertable = true)
//	@OneToOne(fetch = FetchType.LAZY)
	@OneToOne(fetch = FetchType.LAZY, targetEntity = VocCategory.class)
	@JoinColumn(name="category", referencedColumnName = "id", nullable = false)
	private VocCategory category;
	
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "content", nullable = false)
	private String content;
	@Column(name = "username")
	private String username;
	@Column(name = "email")
	private String email;
	
	@OneToOne(targetEntity = StationInformation.class)
	@JoinColumn(name="station_id", referencedColumnName = "station_id", nullable = false)
	private StationInformation stationId;
	
	
	@Column(name = "need_reply", nullable = false)
	private int needReply;
	
	public VocQuestion() {}
	
	public VocQuestion(long id) {
		this.id = id;
	}
	
	@Builder
	public VocQuestion(VocCategory category, String title, String content, String username, String email, StationInformation stationId,
			int needReply, LocalDateTime createdAt) {
		this.category = category;
		this.title = title;
		this.content = content;
		this.username = username;
		this.email = email;
		this.stationId = stationId;
		this.needReply = needReply;
	}
	
}
