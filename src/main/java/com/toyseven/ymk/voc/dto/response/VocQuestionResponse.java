package com.toyseven.ymk.voc.dto.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.toyseven.ymk.common.model.BaseTimeEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocQuestionResponse extends BaseTimeEntity {
	private long id;
	@NotBlank private String category;
	@NotBlank private String title;
	@NotBlank private String content;
	@NotBlank private String email;
	@NotBlank private String username;
	@NotBlank private String stationId;
	@NotNull private int needReply;
}
