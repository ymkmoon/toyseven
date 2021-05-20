package com.toyseven.ymk.voc.answer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.voc.dto.request.VocAnswerRequest;
import com.toyseven.ymk.voc.question.VocQuestion;

@Repository
public interface VocAnswerRepository extends JpaRepository<VocAnswer, Long>{
	void save(VocAnswerRequest vocAnswerRequest);

	Optional<List<VocAnswer>> findByQuestionId(Optional<VocQuestion> vocQuestion);
}
