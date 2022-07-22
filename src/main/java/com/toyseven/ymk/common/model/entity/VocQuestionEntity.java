package com.toyseven.ymk.common.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import com.toyseven.ymk.common.dto.VocQuestionDto;
import com.toyseven.ymk.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Proxy(lazy = false)
@Entity(name="voc_question")
public class VocQuestionEntity extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private Long id;
	
//	@Column(name = "category", nullable = false, updatable = true, insertable = true)
//	@OneToOne(fetch = FetchType.LAZY)
	@OneToOne(fetch = FetchType.LAZY, targetEntity = VocCategoryEntity.class)
	@JoinColumn(name="category", referencedColumnName = "id", nullable = false)
	private VocCategoryEntity category;
	
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "content", nullable = false)
	private String content;
	@Column(name = "username")
	private String username;
	@Column(name = "email")
	private String email;
	
	@OneToOne(targetEntity = StationInformationEntity.class)
	@JoinColumn(name="station_id", referencedColumnName = "station_id", nullable = false)
	private StationInformationEntity stationId;
	
	
	@Column(name = "need_reply", nullable = false)
	private int needReply;
	
	public VocQuestionEntity(Long id) {
		this.id = id;
	}
	
	@Builder
	public VocQuestionEntity(VocCategoryEntity category, String title, String content, String username, String email, StationInformationEntity stationId,
			int needReply) {
		this.category = category;
		this.title = title;
		this.content = content;
		this.username = username;
		this.email = email;
		this.stationId = stationId;
		this.needReply = needReply;
	}
	
	public VocQuestionDto.Response toVocQuestionResponse() {
		return VocQuestionDto.Response.builder()
				.id(id)
				.category(category.getDisplayName())
				.title(title)
				.content(content)
				.email(email)
				.username(username)
				.stationId(stationId.getStationId())
				.needReply(needReply)
				.createdAt(getCreatedAt())
				.updatedAt(getUpdatedAt())
				.build();
	}
	
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
