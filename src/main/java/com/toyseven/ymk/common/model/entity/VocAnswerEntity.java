package com.toyseven.ymk.common.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

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
	private boolean active;
	
	@Builder
	public VocAnswerEntity(VocQuestionEntity questionId, String content, AdminEntity adminId, boolean active) {
		this.questionId = questionId;
		this.content = content;
		this.adminId = adminId;
		this.active = active;
	}
	
	public VocAnswerDto.Response toVocAnswerResponse() {
		return VocAnswerDto.Response.builder()
				.id(id)
				.content(content)
				.questionId(questionId.getId())
				.adminName(adminId.getNickname())
				.createdAt(getCreatedAt())
				.updatedAt(getUpdatedAt())
				.active(active)
				.build();
	}
	
	public void update(String content, boolean active) {
		this.content = content;
		this.active = active;
	}
}
