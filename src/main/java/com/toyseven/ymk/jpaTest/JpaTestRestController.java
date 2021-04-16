package com.toyseven.ymk.jpaTest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.toyseven.ymk.model.AdminItem;

@RestController
@RequestMapping("JpaTest")
public class JpaTestRestController {
	
	@Autowired
	JpaTestService jpaTestService;
	
	// 모든 회원 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<AdminItem>> getAllMembers() {
		List<AdminItem> adminItem = jpaTestService.findAll();
		return new ResponseEntity<List<AdminItem>>(adminItem, HttpStatus.OK);
	}
	
	// 회원번호로 한명의 회원 조회
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AdminItem> getMember(@PathVariable("id") Long id) {
		Optional<AdminItem> adminItem = jpaTestService.findById(id);
		return new ResponseEntity<AdminItem>(adminItem.get(), HttpStatus.OK);
	}
	
	
	// 회원번호로 회원 삭제
	@DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
		jpaTestService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
	// 회원번호로 회원 수정(id로 회원을 찾아 Member 객체의 id, name 수정)
	@PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AdminItem> updateMember(@PathVariable("id") Long id, AdminItem adminItem) {
		jpaTestService.updateById(id, adminItem);
		return new ResponseEntity<AdminItem>(adminItem, HttpStatus.OK);
	}
	
	// 회원 입력
	@PostMapping
	public ResponseEntity<AdminItem> save(AdminItem adminItem) {
		return new ResponseEntity<AdminItem>(jpaTestService.save(adminItem), HttpStatus.OK);
	}
	
	// 회원 입력
	@RequestMapping(value="/saveMember", method = RequestMethod.GET)
	public ResponseEntity<AdminItem> save(HttpServletRequest req, AdminItem adminItem) {
		return new ResponseEntity<AdminItem>(jpaTestService.save(adminItem), HttpStatus.OK);
	}

}
