package nelon.arrive.dreamshop.controller;

import lombok.RequiredArgsConstructor;
import nelon.arrive.dreamshop.dto.ImageDto;
import nelon.arrive.dreamshop.model.Image;
import nelon.arrive.dreamshop.response.ApiResponse;
import nelon.arrive.dreamshop.service.image.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
	private final IImageService imageService;
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(
		@RequestParam List<MultipartFile> files,
		@RequestParam Long productId
	) {
		try {
			List<ImageDto> imageDtos = imageService.saveImages(files, productId);
			return ResponseEntity.ok(new ApiResponse("Upload successful!", imageDtos));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR)
				.body(new ApiResponse("Upload failed!", e.getMessage()));
		}
	}
	
	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<Resource> downloadImage(
		@PathVariable Long imageId
	) throws SQLException {
		Image image = imageService.getImageById(imageId);
		ByteArrayResource resource =
			new ByteArrayResource(image.getImage().getBytes(1,
				(int) image.getImage().length()));
		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(image.getFileType()))
			.header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + image.getFileName() + "\"").body(resource);
	}
	
	public ResponseEntity<ApiResponse> updateImage(
		@PathVariable Long imageId,
		@RequestBody MultipartFile file
	) {
	
	}
}