package com.toyseven.ymk.voc.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.toyseven.ymk.station.StationInformation;
import com.toyseven.ymk.voc.VocCategory;
import com.toyseven.ymk.voc.question.VocQuestion;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VocQuestionRequest {
	private long id;
	@NotBlank private VocCategory category;
	@NotBlank private String title;
	@NotBlank private String content;
	@NotBlank private String email;
	@NotBlank private String username;
	@NotBlank private StationInformation stationId;
	@NotNull private int needReply;
	
	public VocQuestion toEntity() {
        return VocQuestion.builder()
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
