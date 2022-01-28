package com.toyseven.ymk.voc.question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.toyseven.ymk.common.model.entity.StationInformationEntity;
import com.toyseven.ymk.common.model.entity.VocCategoryEntity;
import com.toyseven.ymk.common.model.entity.VocQuestionEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VocQuestionRequest {
	private long id;
	@NotBlank private VocCategoryEntity category;
	@NotBlank private String title;
	@NotBlank private String content;
	@NotBlank private String email;
	@NotBlank private String username;
	@NotBlank private StationInformationEntity stationId;
	@NotNull private int needReply;
	
	public VocQuestionEntity toEntity() {
        return VocQuestionEntity.builder()
        		.category(category)
        		.title(title)
        		.content(content)
                .email(email)
                .username(username)
                .stationId(stationId)
                .needReply(needReply)
                .build();
    }
}
