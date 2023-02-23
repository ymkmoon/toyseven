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
	
	private String categoryId;
    private String title;
    private String username;
    private String email;
    private String stationId;
    private Boolean active;
    
    private String startAt;
    private String endAt;
}
