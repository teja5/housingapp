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

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vk.housing.database.DatabaseUtil;
import com.vk.housing.database.model.PropertyImages;
import com.vk.housing.database.model.User;
import com.vk.housing.util.Util;
import com.vk.housing.util.ValidationMessage;

@RestController
@RequestMapping("user")
public class UserController {

	// test User
	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public String testUser(@RequestParam("test") String test) {

		// DatabaseUtil.getSessionFactory();
		return "userTest" + test;
	}

	// sign_up
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public ResponseEntity<String> signup(@RequestBody User user) {

		User requestTable = user;

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		ValidationMessage message = requestTable.validate(requestTable);
		if (!message.isValue()) {
			errorObect.put("errorStatus", true);
			errorObect.put("errorMessage", message.getMessage());
			errorObect.put("errorId", 2);

			httpStatus = HttpStatus.BAD_REQUEST;
			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		long id = (Long) session.save(requestTable);

		if (id != 0) {
			requestTable.setUser_id(id);
			// String reponseData = gson.toJson(requestTable);
			JSONObject responseObject = new JSONObject();

			responseObject.put("user_id", requestTable.getUser_id());
			responseObject.put("name", requestTable.getName());
			responseObject.put("phone", requestTable.getPhone());
			responseObject.put("email_id", requestTable.getEmail_id());
			responseObject.put("password", requestTable.getPassword());
			responseObject.put("login_type", requestTable.getLogin_type());

			resultObject.put("user", responseObject);
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "User Registered Successfully");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.ACCEPTED;
		} else {
			errorObect.put("errorStatus", true);
			errorObect.put("errorMessage", "Please check the user data");
			errorObect.put("errorId", 1);
			httpStatus = HttpStatus.BAD_REQUEST;
		}

		resultObject.put("status", httpStatus.value());
		resultObject.put("error", errorObect);
		session.getTransaction().commit();
		session.close();
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// login
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ResponseEntity<String> login(@RequestParam("user") String name, @RequestParam("password") String password) {

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		User requestTable;

		System.out.println("Called Login message");
		@SuppressWarnings("unchecked")
		Query<User> queryRequest = session
				.createQuery("from User where email_id = '" + name + "' or phone = '" + name + "'");
		try {
			System.out.println(queryRequest.getResultList().size());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (queryRequest != null && queryRequest.getResultList().size() > 0) {
			requestTable = queryRequest.getResultList().get(0);
		} else {
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Unable to find the user");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.BAD_REQUEST;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		if (requestTable.getUser_id() != 0) {

			JSONObject responseObject = new JSONObject();

			responseObject.put("user_id", requestTable.getUser_id());
			responseObject.put("name", requestTable.getName());
			responseObject.put("phone", requestTable.getPhone());
			responseObject.put("email_id", requestTable.getEmail_id());
			responseObject.put("password", requestTable.getPassword());
			responseObject.put("login_type", requestTable.getLogin_type());

			resultObject.put("user", responseObject);
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "User Retrieved Successfully");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.ACCEPTED;
		} else {
			errorObect.put("errorStatus", true);
			errorObect.put("errorMessage", "Please check the user data");
			errorObect.put("errorId", 1);
			httpStatus = HttpStatus.BAD_REQUEST;
		}

		resultObject.put("status", httpStatus.value());
		resultObject.put("error", errorObect);
		session.getTransaction().commit();
		session.close();
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// Forgot password
	@RequestMapping(method = RequestMethod.GET, value = "/forgot_password")
	public ResponseEntity<String> forgotPassword(@RequestParam("user") String user) {

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		User requestTable;

		@SuppressWarnings("unchecked")
		Query<User> queryRequest = session
				.createQuery("from User where email_id = '" + user + "' or phone = '" + user + "'");
		if (queryRequest.getResultList().size() > 0) {
			requestTable = queryRequest.getResultList().get(0);
		} else {
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Unable to find the user");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.BAD_REQUEST;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		if (requestTable.getUser_id() != 0) {

			JSONObject responseObject = new JSONObject();

			responseObject.put("user_id", requestTable.getUser_id());
			responseObject.put("name", requestTable.getName());
			responseObject.put("phone", requestTable.getPhone());
			responseObject.put("email_id", requestTable.getEmail_id());
			responseObject.put("password", requestTable.getPassword());
			responseObject.put("login_type", requestTable.getLogin_type());

			resultObject.put("user", responseObject);
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "User Retrieved Successfully");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.ACCEPTED;
		} else {
			errorObect.put("errorStatus", true);
			errorObect.put("errorMessage", "Please check the user data");
			errorObect.put("errorId", 1);
			httpStatus = HttpStatus.BAD_REQUEST;
		}

		resultObject.put("status", httpStatus.value());
		resultObject.put("error", errorObect);
		session.getTransaction().commit();
		session.close();
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// update password
	@RequestMapping(method = RequestMethod.PUT, value = "/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestParam String userId, @RequestParam String oldPassword,
			@RequestParam String newPassword) {

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		Query<User> queryRequest = session.createQuery("from User where user_id =" + userId);
		if (queryRequest.getResultList().size() > 0) {
			User userRetrieved = queryRequest.getResultList().get(0);
			if (userRetrieved.getPassword().trim().equals(oldPassword)) {
				userRetrieved.setPassword(newPassword);
			}

			session.update(userRetrieved);

		} else {
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Unable to find the user");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.BAD_REQUEST;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		errorObect.put("errorStatus", false);
		errorObect.put("errorMessage", "Response Updated Successfully");
		errorObect.put("errorId", -1);
		httpStatus = HttpStatus.ACCEPTED;

		resultObject.put("status", httpStatus.value());
		resultObject.put("error", errorObect);
		session.getTransaction().commit();
		session.close();
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// Edit Profile Details
	@RequestMapping(method = RequestMethod.PUT, value = "/editProfile")
	public ResponseEntity<String> editProfile(@RequestParam String userId, @RequestParam String username,
			@RequestParam String mailid, @RequestParam String phone) {

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		Query<User> queryRequest = session.createQuery("from User where user_id =" + userId);
		if (queryRequest.getResultList().size() > 0) {
			User userRetrieved = queryRequest.getResultList().get(0);
			userRetrieved.setEmail_id(mailid);
			userRetrieved.setName(username);
			userRetrieved.setPhone(phone);
			session.update(userRetrieved);

		} else {
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Unable to find the user");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.BAD_REQUEST;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		errorObect.put("errorStatus", false);
		errorObect.put("errorMessage", "Response Updated Successfully");
		errorObect.put("errorId", -1);
		httpStatus = HttpStatus.ACCEPTED;

		resultObject.put("status", httpStatus.value());
		resultObject.put("error", errorObect);
		session.getTransaction().commit();
		session.close();
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// update profileImage
	@RequestMapping(method = RequestMethod.PUT, value = "/uploadProfileImage", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<String> updateProfileImage(@RequestParam String userId,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {

		String fileName = userId + Util.getDateTimeFormat() + multipartFile.getOriginalFilename();

//		String uploadDir = "C:\\Desktop\\profiles";
		String uploadDir = "C:\\Users\\Administrator\\Desktop\\profiles";

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		try {
			saveFile(uploadDir, fileName, multipartFile);
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		@SuppressWarnings("unchecked")
		Query<User> queryRequest = session.createQuery("from User where user_id =" + userId);
		if (queryRequest.getResultList().size() > 0) {
			User userRetrieved = queryRequest.getResultList().get(0);
			userRetrieved.setImage_path(uploadDir + "\\" + fileName);
			session.update(userRetrieved);

		} else {
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Unable to find the user");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.BAD_REQUEST;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		errorObect.put("errorStatus", false);
		errorObect.put("errorMessage", "Response Updated Successfully");
		errorObect.put("errorId", -1);
		httpStatus = HttpStatus.ACCEPTED;

		resultObject.put("status", httpStatus.value());
		resultObject.put("error", errorObect);
		session.getTransaction().commit();
		session.close();
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	@GetMapping(value = "getUserImage", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@RequestParam long userId) {

		System.out.print("CAlled");
		byte[] image = new byte[0];

		// Image image = null;

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		User user = new User();
		@SuppressWarnings("unchecked")
		Query<User> queryRequest = session.createQuery("from User where user_id = " + userId);
		if (queryRequest.getResultList().size() > 0) {
			user = queryRequest.getResultList().get(0);
			// System.out.println(propertyRetrieved.getPi_id() + " " +
			// propertyRetrieved.getPi_img());
			// propertyImages = propertyRetrieved;
			// session.update(propertyImages);

			try {

				File input_file = new File(user.getImage_path());

				// image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
				//
				// // Reading input file
				// image = ImageIO.read(input_file);
				//
				// System.out.println("Reading complete.");

				BufferedImage height = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

				// Reading input file
				height = ImageIO.read(input_file);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(height, "jpg", baos);
				System.out.println("Reading complete.");

				image = baos.toByteArray();

			} catch (IOException e) {
				System.out.println("Error: " + e);
			}

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);

		}
		return null;

	}

	@GetMapping(value = "getProfileImage", produces = MediaType.IMAGE_JPEG_VALUE)
	public Image getImage(@RequestParam String userId) throws IOException {

		Image image = null;

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		User user = new User();
		@SuppressWarnings("unchecked")
		Query<User> queryRequest = session.createQuery("from User where user_id =" + userId);
		if (queryRequest.getResultList().size() > 0) {
			User userRetrieved = queryRequest.getResultList().get(0);
			user = userRetrieved;
			session.update(userRetrieved);
		}

		try {

			File input_file = new File(user.getImage_path());

			image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

			System.out.print(input_file);
			// Reading input file
			image = ImageIO.read(input_file);

			System.out.println("Reading complete.");

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

}
