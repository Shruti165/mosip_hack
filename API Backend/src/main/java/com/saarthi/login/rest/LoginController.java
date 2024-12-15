package com.saarthi.login.rest;

import java.net.URLDecoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saarthi.commons.DocType;
import com.saarthi.commons.Document;
import com.saarthi.commons.GenericResponse;
import com.saarthi.login.LoginRequest;
import com.saarthi.login.service.LoginService;

@RestController
@RequestMapping("login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/farmer", method = RequestMethod.POST)
	public ResponseEntity<?> farmerLogin(@RequestBody LoginRequest request) throws Exception {
		try {
			return new ResponseEntity<GenericResponse>(new GenericResponse(loginService.farmerLogin(request)), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/warehouseOwner", method = RequestMethod.POST)
	public ResponseEntity<?> warehouseOwnerLogin(@RequestBody LoginRequest request) throws Exception {
		try {
			return new ResponseEntity<GenericResponse>(new GenericResponse(loginService.warehouseOwnerLogin(request)), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/bank", method = RequestMethod.POST)
	public ResponseEntity<?> bankLogin(@RequestBody LoginRequest request) throws Exception {
		try {
			return new ResponseEntity<GenericResponse>(new GenericResponse(loginService.bankLogin(request)), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
