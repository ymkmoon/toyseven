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

import com.toyseven.ymk.common.dto.VocAnswerDto;
import com.toyseven.ymk.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Proxy(lazy = false)
@Entity(name="voc_answer")
public class VocAnswerEntity extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = VocQuestionEntity.class)
	@JoinColumn(name="question_id", referencedColumnName = "id", nullable = false)
	private VocQuestionEntity questionId;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = AdminEntity.class)
	@JoinColumn(name="admin_id", referencedColumnName = "id", nullable = false)
	private AdminEntity adminId;
	
	@Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
	private boolean isActive;
	
	@Builder
	public VocAnswerEntity(VocQuestionEntity questionId, String content, AdminEntity adminId, boolean isActive) {
		this.questionId = questionId;
		this.content = content;
		this.adminId = adminId;
		this.isActive = isActive;
	}
	
	public VocAnswerDto.Response toVocAnswerResponse() {
		return VocAnswerDto.Response.builder()
				.id(id)
				.content(content)
				.questionId(questionId.getId())
				.adminName(adminId.getNickname())
				.createdAt(getCreatedAt())
				.updatedAt(getUpdatedAt())
				.isActive(isActive)
				.build();
	}
	
	public void update(String content) {
		this.content = content;
	}
}
