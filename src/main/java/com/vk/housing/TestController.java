package com.vk.housing;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vk.housing.database.DatabaseUtil;

@RestController
@RequestMapping("test")
public class TestController {

	// test Brand
	@RequestMapping(value = "/test")
	public String testBrand() {

		DatabaseUtil.getSessionFactory();
		return "passUpdate";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/testok")
	public String getTest(@RequestParam("ok") String test) {

		System.out.println(test);
		// DatabaseUtil.getSessionFactory();
		return "getTest" + test;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/sorry")
	public String saySorry() {

		return "Sorry Aruna.... Please forgive me";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/testok/{ok}")
	public String getTestPost(@PathVariable("ok") String test) {

		System.out.println(test);
		// DatabaseUtil.getSessionFactory();
		return "getTestPost" + test;
	}

	@PostMapping("/photo")
	public String saveUser(@RequestParam("image") MultipartFile multipartFile) throws IOException {

		String fileName = multipartFile.getOriginalFilename();

		// String uploadDir = "user-photos/" + savedUser.getId();
		//
		// .saveFile(uploadDir, fileName, multipartFile);

		return "Success" + fileName;
	}

	@GetMapping("/getImage")
	@ResponseBody
	public String getImageDynamicType() {
		// MediaType contentType = MediaType.IMAGE_JPEG;
		// InputStream in = getClass().getResourceAsStream("/Downloads/IMG_1797.JPEG");
		// // getClass().getResourceAsStream("/com/baeldung/produceimage/image.png");
		// return ResponseEntity.ok().contentType(contentType).body(new
		// InputStreamResource(in));
		return "just check";
	}

	@PostMapping("/users/save")
	public String saveUsers(@RequestParam("image") MultipartFile multipartFile) throws IOException {

		String fileName = multipartFile.getOriginalFilename();

		String uploadDir = "C:\\Desktop\\profiles";

		// try {
		//
		// BufferedImage image = null;
		//
		// image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		//
		// // Reading input file
		// image = ImageIO.read(multipartFile.getInputStream());
		// // Output file path
		// File output_file = new File("C:\\Desktop\\gfg.jpg");
		// if (!output_file.exists()) {
		// output_file.createNewFile();
		// }
		//
		// // Writing to file taking type and path as
		// ImageIO.write(image, "jpg", output_file);
		//
		// System.out.println("Writing complete.");
		// } catch (IOException e) {
		// System.out.println("Error: " + e);
		// }
		saveFile(uploadDir, fileName, multipartFile);

		// retriveFile(uploadDir, fileName, multipartFile);

		return "view";
	}

	@GetMapping(value = "geturlImage", produces = MediaType.IMAGE_JPEG_VALUE)
	public Image getImage() throws IOException {

		// // BufferedImage img = null;
		// Image image = null;
		// try {
		// // img = ImageIO.read(new File("user-photos\\add\\aadhar_front.jpg"));
		//
		// image = ImageIO.read(new File("user-photos\\add\\aadhar_front.jpg"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		Image image = null;
		try {
			File input_file = new File("C:\\Pics\\leh\\IMG_20190720_054250.jpg");

			// image file path create an object of
			// BufferedImage type and pass as parameter the
			// width, height and image int
			// type. TYPE_INT_ARGB means that we are
			// representing the Alpha , Red, Green and Blue
			// component of the image pixel using 8 bit
			// integer value.

			image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

			// Reading input file
			image = ImageIO.read(input_file);

			System.out.println("Reading complete.");

			// ByteArrayOutputStream os = new ByteArrayOutputStream();
			// ImageIO.write((RenderedImage) image, "png", os);
			// fis = new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}

		return image;
	}

	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			System.out.print(filePath);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	public static void retriveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			System.out.println(filePath + " " + Files.isReadable(filePath));

		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	@GetMapping("/imagegeturl")
	public ResponseEntity<byte[]> getImagesd() {
		byte[] image = new byte[0];
		System.out.print("hello");

		try {

			File input_file = new File("C:\\Users\\teja4\\OneDrive\\Desktop\\93614382_2033608020137076_706364293028773888_o.jpg");

			// image file path create an object of
			// BufferedImage type and pass as parameter the
			// width, height and image int
			// type. TYPE_INT_ARGB means that we are
			// representing the Alpha , Red, Green and Blue
			// component of the image pixel using 8 bit
			// integer value.

			BufferedImage height = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

			// Reading input file
			height = ImageIO.read(input_file);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(height, "jpg", baos);
			System.out.println("Reading complete.");

			image = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	}

}
