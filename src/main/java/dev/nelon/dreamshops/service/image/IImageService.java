package dev.nelon.dreamshops.service.image;

import dev.nelon.dreamshops.dto.ImageDto;
import dev.nelon.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
	Image getImageById(Long id);
	
	List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
	
	void deleteImageById(Long id);
	
	void updateImage(MultipartFile file, Long imageId);
}
