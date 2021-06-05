package com.toyseven.ymk.voc.dto.request;

import com.toyseven.ymk.admin.AdminEntity;
import com.toyseven.ymk.voc.answer.VocAnswerEntity;
import com.toyseven.ymk.voc.question.VocQuestionEntity;

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
