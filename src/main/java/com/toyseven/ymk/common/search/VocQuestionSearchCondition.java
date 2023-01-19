package com.toyseven.ymk.common.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VocQuestionSearchCondition {
	
	// 추 후 updatedAt 추가
	private String categoryId;
    private String title;
    private String username;
    private String email;
    private String stationId;
}
