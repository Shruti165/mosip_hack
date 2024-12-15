package com.saarthi.utils;

import com.saarthi.commons.DocType;
import com.saarthi.commons.Document;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.saarthi.commons.GenericResponse;

@RestController
@RequestMapping("util")
public class CommonController {
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> upload(@RequestParam("file") MultipartFile file
						   /*@RequestParam("phoneNo") String phoneNo, @RequestParam("docType") String docType*/) throws Exception {
		String fileName = null;

		if (file != null) {
			fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
			fileName = fileName.substring(0, fileName.indexOf(".png")) + "_" + new Date().getTime() + "_.png";
		}

		Document doc = new Document();
		doc.setDocType(DocType.CROP_EXCHANGE);
		doc.setImageUrl(fileName);
		return new ResponseEntity<GenericResponse>(new GenericResponse(doc), HttpStatus.OK);
	}

}
