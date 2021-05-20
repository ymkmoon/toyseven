package com.toyseven.ymk.voc.dto.request;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.toyseven.ymk.common.model.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EnableJpaRepositories
@Entity
@NoArgsConstructor
@Inheritance
@Table(name="voc_answer")
public class VocAnswerRequest extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long question_id;
	private String content;
	private long admin_id;
	
}
