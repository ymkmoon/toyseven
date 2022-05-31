package com.toyseven.ymk.cognito;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cognito")
public class CognitoController {

  @GetMapping("/1")
  public ResponseEntity<String> message(Principal principal) {
	  return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
  }
}
