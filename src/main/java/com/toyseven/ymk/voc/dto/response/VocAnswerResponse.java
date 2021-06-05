package com.toyseven.ymk.voc.dto.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.toyseven.ymk.admin.AdminEntity;
import com.toyseven.ymk.voc.question.VocQuestionEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocAnswerResponse {
	private long id;
	@NotNull private VocQuestionEntity questionId;
	@NotBlank private String content;
	@NotNull private AdminEntity adminId;
	@NotBlank private LocalDateTime createdAt;
	@NotBlank private LocalDateTime updatedAt;
}
