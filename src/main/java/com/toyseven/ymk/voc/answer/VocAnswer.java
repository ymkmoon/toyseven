package com.toyseven.ymk.voc.answer;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import com.toyseven.ymk.admin.Admin;
import com.toyseven.ymk.common.model.BaseTimeEntity;
import com.toyseven.ymk.voc.question.VocQuestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Proxy(lazy = false)
@Entity(name="voc_answer")
public class VocAnswer extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = VocQuestion.class)
	@JoinColumn(name="question_id", referencedColumnName = "id", nullable = false)
	private VocQuestion questionId;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = Admin.class)
	@JoinColumn(name="admin_id", referencedColumnName = "id", nullable = false)
	private Admin adminId;
	
	public VocAnswer() {}

	@Builder
	public VocAnswer(VocQuestion questionId, String content, Admin adminId, LocalDateTime createAt) {
		this.questionId = questionId;
		this.content = content;
		this.adminId = adminId;
	}
}
