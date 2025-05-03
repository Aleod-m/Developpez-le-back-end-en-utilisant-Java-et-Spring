package com.chatop.backend.constroller;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.service.RentalService;
import com.chatop.backend.service.StorageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/files")
public class FilesController extends AbstractController {

	private final RentalService rentalSrvc;

	private final StorageService storageSrvc;

	public FilesController(RentalService rentalSrvc, StorageService storageSrvc) {
		this.rentalSrvc = rentalSrvc;
		this.storageSrvc = storageSrvc;
	}

	@Operation(summary = "Get the picture linked to a rental.")
	@GetMapping("/rentalpicture/{id}")
	public ResponseEntity<FileSystemResource> getRentalPicture(@PathVariable Long id) {
		return responseFromOptional(
				rentalSrvc.findById(id).flatMap(r -> storageSrvc.load(r.getId())).map(f -> new FileSystemResource(f)));
	}

}
