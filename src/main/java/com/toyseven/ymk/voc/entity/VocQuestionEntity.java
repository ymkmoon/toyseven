package com.toyseven.ymk.voc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.toyseven.ymk.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EnableJpaRepositories
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="voc_question")
public class VocQuestionEntity extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank private String category;
	@NotBlank private String title;
	@NotBlank private String content;
	@NotBlank private String email;
	@NotBlank private String username;
	@Column(name = "station_id")
	@NotBlank private String stationId;
	@Column(name = "need_reply")
	@NotNull private int needReply;
}
