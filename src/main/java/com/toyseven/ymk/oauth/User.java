package com.toyseven.ymk.oauth;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Entity
@Table(name = "Oauth2_User")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@Column(name = "name", nullable = false, updatable = true, insertable = true)
	private String name;

	@Column(name = "email", nullable = false, updatable = true, insertable = true)
	private String email;

//	@Column
//	private String picture;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Builder
	public User(String name, String email, LocalDateTime createAt, LocalDateTime updateAt, Role role) {
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public User update(String name) {
		this.name = name;
		return this;
	}

	public String getRoleKey() {
		return this.role.getKey();
	}
	
	
}
