package com.toyseven.ymk.voc.category;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.VocCategoryDto;
import com.toyseven.ymk.common.model.entity.VocCategoryEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VocCategoryServiceImpl implements VocCategoryService {
	
	private final VocCategoryRepository vocCategoryRepository;
	
	@Override
	public List<VocCategoryDto.Response> getVocCategory(Pageable pageable) {
		Page<VocCategoryEntity> category = vocCategoryRepository.findAll(pageable);
		return category.stream().map(VocCategoryEntity::toVocCategoryResponse).collect(toList());
	}
	
}
