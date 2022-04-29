package com.toyseven.ymk.voc.category;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.VocCategoryDto;

@Transactional
public interface VocCategoryService {
	List<VocCategoryDto.Response> getVocCategory();
}
