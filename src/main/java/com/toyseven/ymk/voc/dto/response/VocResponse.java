package com.toyseven.ymk.voc.dto.response;

import java.util.List;
import java.util.Optional;

import com.toyseven.ymk.voc.answer.VocAnswer;
import com.toyseven.ymk.voc.question.VocQuestion;

import lombok.Data;

@Data
public class VocResponse {
	public Optional<List<VocAnswer>> vocAnswer;
	public Optional<VocQuestion> vocQuestion;
}
