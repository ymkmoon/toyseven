package com.toyseven.ymk.azure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("azure")
public class AzureController {
	
	private final AzureStorageComponent azureStorageComponent;
	
	@GetMapping("/table-storage")
	public ResponseEntity<Object> azureTableStorage() {
		
		azureStorageComponent.selectTableList();
		azureStorageComponent.createTable("ymktable");
		azureStorageComponent.insertData("ymktable", "test", "test");
		azureStorageComponent.selectOne("ymktable", "test", "test");
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
