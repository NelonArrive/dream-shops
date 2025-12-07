package dev.nelon.dreamshops.service.image;

import lombok.RequiredArgsConstructor;
import dev.nelon.dreamshops.dto.ImageDto;
import dev.nelon.dreamshops.model.Image;
import dev.nelon.dreamshops.model.Product;
import dev.nelon.dreamshops.repository.ImageRepository;
import dev.nelon.dreamshops.repository.ProductRepository;
import dev.nelon.dreamshops.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.lang.module.ResolutionException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
	private final ImageRepository imageRepository;
	private final ProductRepository productRepository;
	private final ProductService productService;
	
	@Override
	public Image getImageById(Long id) {
		return imageRepository.findById(id).orElseThrow(
			() -> new ResolutionException("No image found with id: " + id)
		);
	}
	
	@Override
	public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
		Product product = productService.getProductById(productId);
		
		List<ImageDto> savedImageDto = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileName(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);
				
				String buildDownload  = "/image/download/";
				String downloadUrl = buildDownload + image.getId();
				image.setDownloadUrl(downloadUrl);
				Image savedImage = imageRepository.save(image);
				
				savedImage.setDownloadUrl(buildDownload + savedImage.getId());
				imageRepository.save(image);
				
				ImageDto imageDto = new ImageDto();
				imageDto.setImageId(savedImage.getId());
				imageDto.setImageName(savedImage.getFileName());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());
				savedImageDto.add(imageDto);
			} catch (IOException | SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return savedImageDto;
	}
	
	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository::save,
			() -> {
				throw new ResolutionException("No image found with id: " + id);
			});
	}
	
	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
