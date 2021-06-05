package com.toyseven.ymk.voc.dto.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocAnswerResponse {
	private long id;
	@NotNull private long questionId;
	@NotBlank private String content;
	@NotNull private long adminId;
	@NotNull private LocalDateTime createdAt;
}
