package com.toyseven.ymk.voc.dto.request;

import com.toyseven.ymk.admin.Admin;
import com.toyseven.ymk.voc.answer.VocAnswer;
import com.toyseven.ymk.voc.question.VocQuestion;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VocAnswerRequest {
	private long id;
	private VocQuestion questionId;
	private String content;
	private Admin adminId;
	
	public VocAnswer toEntity() {
		return VocAnswer.builder()
				.questionId(questionId)
				.content(content)
				.adminId(adminId)
				.build();
	}
}
