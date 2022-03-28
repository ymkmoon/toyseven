package com.toyseven.ymk.common.dto.voc;

import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.VocAnswerEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VocAnswerRequest {
	private long id;
	private VocQuestionEntity questionId;
	private String content;
	private AdminEntity adminId;
	
	public VocAnswerEntity toEntity() {
		return VocAnswerEntity.builder()
				.questionId(questionId)
				.content(content)
				.adminId(adminId)
				.build();
	}
}
