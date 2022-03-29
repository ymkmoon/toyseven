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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Proxy(lazy = false)
@Entity(name="voc_answer")
public class VocAnswerEntity extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = VocQuestionEntity.class)
	@JoinColumn(name="question_id", referencedColumnName = "id", nullable = false)
	private VocQuestionEntity questionId;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = AdminEntity.class)
	@JoinColumn(name="admin_id", referencedColumnName = "id", nullable = false)
	private AdminEntity adminId;
	
	public VocAnswerEntity() {}

	@Builder
	public VocAnswerEntity(VocQuestionEntity questionId, String content, AdminEntity adminId) {
		this.questionId = questionId;
		this.content = content;
		this.adminId = adminId;
	}
	
	public VocAnswerDto.Response toVocAnswerResponse() {
		return VocAnswerDto.Response.builder()
				.id(id)
				.content(content)
				.questionId(questionId.getId())
				.adminName(adminId.getName())
				.createdAt(getCreatedAt())
				.updatedAt(getUpdatedAt())
				.build();
	}
}
