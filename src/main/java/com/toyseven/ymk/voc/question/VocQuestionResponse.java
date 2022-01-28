package com.toyseven.ymk.voc.question;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.toyseven.ymk.common.model.BaseTimeEntity;
import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.model.entity.VocCategoryEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocQuestionResponse extends BaseTimeEntity {
	private long id;
	@NotBlank private VocCategoryEntity category;
	@NotBlank private String title;
	@NotBlank private String content;
	@NotBlank private String email;
	@NotBlank private String username;
	@NotBlank private StationInformationEntity stationId;
	@NotNull private int needReply;
	@NotBlank private LocalDateTime createdAt;
	@NotBlank private LocalDateTime updatedAt;
}
