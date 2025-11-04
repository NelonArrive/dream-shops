package nelon.arrive.dreamshop.service.image;

import nelon.arrive.dreamshop.dto.ImageDto;
import nelon.arrive.dreamshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
	Image getImageById(Long id);
	List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
	void deleteImageById(Long id);
	void updateImage(MultipartFile file, Long imageId);
}
