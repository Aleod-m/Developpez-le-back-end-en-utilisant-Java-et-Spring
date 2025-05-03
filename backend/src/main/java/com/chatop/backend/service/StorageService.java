package com.chatop.backend.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.backend.domain.Rental;

import jakarta.annotation.PostConstruct;

@Service
public class StorageService {

	private final Path rootLocation;

	public StorageService(@Value("${estate.uploadDir}") String uploadDir) {
		this.rootLocation = Path.of(uploadDir);
	}

	@PostConstruct
	public void init() {
		try {
			if (!Files.exists(rootLocation)) {
				Files.createDirectory(rootLocation);
			}
		}
		catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
	}

	public Optional<Rental> store(Rental rental, MultipartFile file) {
		if (file.isEmpty()) {
			return Optional.empty();
		}
		Path destinationFile = this.rootLocation
			.resolve(Paths.get(rental.getId().toString(), file.getOriginalFilename()));

		try (InputStream inputStream = file.getInputStream()) {
			if (!Files.exists(destinationFile.getParent())) {
				Files.createDirectory(destinationFile.getParent());
			}
			Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			return Optional.empty();
		}

		rental.setPicture("/api/files/rentalpicture/" + rental.getId().toString());
		return Optional.of(rental);
	}

	public Optional<File> load(Long rentalId) {
		try {
			return Files.list(Paths.get(this.rootLocation.toString(), rentalId.toString()))
				.findAny()
				.map(path -> path.toFile());
		}
		catch (Exception e) {
			return Optional.empty();
		}
	}

}
