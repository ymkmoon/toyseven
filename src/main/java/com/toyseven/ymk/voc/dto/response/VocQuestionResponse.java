package com.toyseven.ymk.voc.dto.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocQuestionResponse {
	private long id;
	@NotBlank private String category;
	@NotBlank private String title;
	@NotBlank private String content;
	@NotBlank private String email;
	@NotBlank private String username;
	@NotBlank private String stationId;
	@NotNull private LocalDateTime createdAt;
	@NotNull private int needReply;
}
