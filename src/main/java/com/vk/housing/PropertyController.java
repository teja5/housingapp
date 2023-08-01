package com.vk.housing;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vk.housing.database.DatabaseUtil;
import com.vk.housing.database.model.Property;
import com.vk.housing.database.model.PropertyImages;
import com.vk.housing.database.model.PropertySaved;
import com.vk.housing.util.Util;
import com.vk.housing.util.ValidationMessage;

@RestController
@RequestMapping("property")
public class PropertyController {

	// add property
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public ResponseEntity<String> addProperty(@RequestBody Property property) {

		Property requestTable = property;

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
			requestTable.setProperty_id(id);
			// String reponseData = gson.toJson(requestTable);
			JSONObject responseObject = new JSONObject();

			responseObject.put("property_id", requestTable.getProperty_id());
			responseObject.put("p_address", requestTable.getP_address());
			responseObject.put("p_phone", requestTable.getP_phone());
			responseObject.put("property_for", requestTable.getProperty_for());
			responseObject.put("building_type", requestTable.getBuilding_type());
			responseObject.put("property_type", requestTable.getProperty_type());
			responseObject.put("construction_status", requestTable.getConstruction_status());
			responseObject.put("age_of_property", requestTable.getAge_of_property());
			responseObject.put("bhk", requestTable.getBhk());
			responseObject.put("bath_room", requestTable.getBath_room());
			responseObject.put("balcony", requestTable.getBalcony());
			responseObject.put("furnish_type", requestTable.getFurnish_type());
			responseObject.put("parking", requestTable.getParking());
			responseObject.put("avalable_from", requestTable.getAvalable_from());
			responseObject.put("price", requestTable.getPrice());
			responseObject.put("security_deposit", requestTable.getSecurity_deposit());
			responseObject.put("maintanace_charge", requestTable.getMaintanace_charge());
			responseObject.put("built_area", requestTable.getBuilt_area());
			responseObject.put("carpet_area", requestTable.getCarpet_area());
			responseObject.put("offers_any", requestTable.getOffers_any());
			responseObject.put("city", requestTable.getCity());
			responseObject.put("locality", requestTable.getLocality());

			resultObject.put("property", responseObject);
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Property Added Successfully");
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

	// update Property
	@PutMapping(value = "/update")
	public ResponseEntity<String> updateProperty(@RequestBody Property property) {

		// Gson gson = new Gson();
		// String jsonRequest = transaction;
		Property requestTable = property;
		// TODO Not to modify the createdDate

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

		session.update(requestTable);

		JSONObject responseObject = new JSONObject();
		responseObject.put("property_id", requestTable.getProperty_id());
		responseObject.put("p_address", requestTable.getP_address());
		responseObject.put("p_phone", requestTable.getP_phone());
		responseObject.put("property_for", requestTable.getProperty_for());
		responseObject.put("building_type", requestTable.getBuilding_type());
		responseObject.put("property_type", requestTable.getProperty_type());
		responseObject.put("construction_status", requestTable.getConstruction_status());
		responseObject.put("age_of_property", requestTable.getAge_of_property());
		responseObject.put("bhk", requestTable.getBhk());
		responseObject.put("bath_room", requestTable.getBath_room());
		responseObject.put("balcony", requestTable.getBalcony());
		responseObject.put("furnish_type", requestTable.getFurnish_type());
		responseObject.put("parking", requestTable.getParking());
		responseObject.put("avalable_from", requestTable.getAvalable_from());
		responseObject.put("price", requestTable.getPrice());
		responseObject.put("security_deposit", requestTable.getSecurity_deposit());
		responseObject.put("maintanace_charge", requestTable.getMaintanace_charge());
		responseObject.put("built_area", requestTable.getBuilt_area());
		responseObject.put("carpet_area", requestTable.getCarpet_area());
		responseObject.put("offers_any", requestTable.getOffers_any());
		responseObject.put("city", requestTable.getCity());
		responseObject.put("locality", requestTable.getLocality());

		resultObject.put("property", responseObject);
		// resultObject.put("staff", staffData);
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

	// Delete Property
	@RequestMapping(value = "/delete")
	public ResponseEntity<String> deleteProperty(@RequestParam("property_id") String property_id) {

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Property requestTable;

		@SuppressWarnings("unchecked")
		Query<Property> queryRequest = session.createQuery("from Property where property_id =" + property_id + "");
		if (queryRequest.getResultList().size() > 0) {
			requestTable = queryRequest.getResultList().get(0);
		} else {
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Unable to find property");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.BAD_REQUEST;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		session.delete(requestTable);

		// if (id != 0) {
		// resultObject.put("customer", customerData);
		errorObect.put("errorStatus", false);
		errorObect.put("errorMessage", "Deleted Successfully");
		errorObect.put("errorId", -1);
		httpStatus = HttpStatus.ACCEPTED;
		// } else {
		// errorObect.put("errorStatus", true);
		// errorObect.put("errorMessage", "Please check the valid User");
		// errorObect.put("errorId", 1);
		// httpStatus = HttpStatus.BAD_REQUEST;
		// }
		resultObject.put("status", httpStatus.value());
		resultObject.put("error", errorObect);
		session.getTransaction().commit();
		session.close();
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// getpropertiesList
	@RequestMapping(value = "/listProperty")
	public ResponseEntity<String> getPropertyList(@RequestParam("firstResult") int firstResult,
			@RequestParam("max") int maxResult) {
		JSONObject resultObject = new JSONObject();
		JSONObject errorObject = new JSONObject();
		HttpStatus httpStatus;

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		Query<Property> queryResponse = session.createQuery("from Property");
		queryResponse.setFirstResult(firstResult);
		queryResponse.setMaxResults(maxResult);

		JSONArray responseArray = new JSONArray();

		for (@SuppressWarnings("rawtypes")
		Iterator iterator = queryResponse.getResultList().iterator(); iterator.hasNext();) {
			Property responseTable = (Property) iterator.next();
			JSONObject responseObject = new JSONObject(responseTable);

			// Retrieve Images

			JSONArray imagesArray = new JSONArray();
			@SuppressWarnings("unchecked")
			Query<PropertyImages> queryRequest = session
					.createQuery("from PropertyImages where ppi_id =" + responseTable.getProperty_id());
			for (@SuppressWarnings("rawtypes")
			Iterator imageIterator = queryRequest.getResultList().iterator(); imageIterator.hasNext();) {
				PropertyImages propertyImages = (PropertyImages) imageIterator.next();
				imagesArray.put(new JSONObject(propertyImages));
			}

			responseObject.append("images", imagesArray);

			responseArray.put(responseObject);

		}

		errorObject.put("errorStatus", false);
		errorObject.put("errorMessage", "Valid Property");
		errorObject.put("errorId", -1);
		httpStatus = HttpStatus.ACCEPTED;
		resultObject.put("propertyList", responseArray);
		resultObject.put("error", errorObject);
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// get Property by Id
	@RequestMapping(value = "/getPropertyById")
	public ResponseEntity<String> getPropertyByList(@RequestParam("property_id") int property_id) {
		JSONObject resultObject = new JSONObject();
		JSONObject errorObject = new JSONObject();
		HttpStatus httpStatus;
		JSONArray responseArray = new JSONArray();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		try {

			@SuppressWarnings("unchecked")
			Query<Property> queryRequest = session.createQuery("from Property where property_id =" + property_id + "");
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = queryRequest.getResultList().iterator(); iterator.hasNext();) {
				Property requestTable = (Property) iterator.next();
				JSONObject requestObject = new JSONObject(requestTable);

				// Retrieve Images

				JSONArray imagesArray = new JSONArray();
				@SuppressWarnings("unchecked")
				Query<PropertyImages> imageRequest = session
						.createQuery("from PropertyImages where ppi_id =" + requestTable.getProperty_id());
				for (@SuppressWarnings("rawtypes")
				Iterator imageIterator = imageRequest.getResultList().iterator(); imageIterator.hasNext();) {
					PropertyImages propertyImages = (PropertyImages) imageIterator.next();
					imagesArray.put(new JSONObject(propertyImages));
				}

				requestObject.append("images", imagesArray);

				responseArray.put(requestObject);
			}

			errorObject = Util.errorJsonObject("Retrieved Successfully", false, -1);
			httpStatus = HttpStatus.ACCEPTED;

		} catch (HibernateException e) {
			errorObject = Util.errorJsonObject(e.getMessage(), true, -1);
			httpStatus = HttpStatus.CONFLICT;
		} catch (Exception e) {
			errorObject = Util.errorJsonObject(e.getMessage(), true, -1);
			httpStatus = HttpStatus.CONFLICT;
		}

		resultObject.put("propertyList", responseArray);
		resultObject.put("error", errorObject);
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// add propertyImage
	@RequestMapping(method = RequestMethod.PUT, value = "/uploadPropertyImage", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<String> updatePropertyImage(@RequestParam("propertyId") String propertyId,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {

		String fileName = propertyId + Util.getDateTimeFormat() + multipartFile.getOriginalFilename();

		String uploadDir = "C:\\Desktop\\properties";

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		saveFile(uploadDir, fileName, multipartFile);

		@SuppressWarnings("unchecked")
		Query<Property> queryRequest = session.createQuery("from Property where property_id =" + propertyId);
		if (queryRequest.getResultList().size() > 0) {
			Property propertyRetrieved = queryRequest.getResultList().get(0);
			propertyRetrieved.setImage_path(uploadDir + "\\" + fileName);
			session.update(propertyRetrieved);

			String imagePath = uploadDir + "\\" + fileName;

			// TODO Check for duplicate

			// Query<PropertyImages> imageQuery = session.createQuery("from PropertyImages
			// where ppi_id ="+ propertyId + " and pi_img = " + imagePath);
			// if (imageQuery.getResultList().size() > 0) {
			//
			// }else {
			PropertyImages images = new PropertyImages();
			images.setPi_img(imagePath);
			images.setPpi_id(propertyRetrieved.getProperty_id());
			session.save(images);
			// }

		} else {
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Unable to find the property");
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

	// Delete Property Image
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteImage")
	public ResponseEntity<String> deleteImage(@RequestParam("image_id") String image_id) {

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		PropertyImages requestTable;

		@SuppressWarnings("unchecked")
		Query<PropertyImages> queryRequest = session.createQuery("from PropertyImages where pi_id = " + image_id);
		if (queryRequest.getResultList().size() > 0) {
			requestTable = queryRequest.getResultList().get(0);
		} else {
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Unable to find property image");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.BAD_REQUEST;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		File myObj = new File(requestTable.getPi_img());
		if (myObj.delete()) {
			System.out.println("Deleted the file: " + myObj.getName());
		} else {
			System.out.println("Failed to delete the file.");
		}

		session.delete(requestTable);

		// if (id != 0) {
		// resultObject.put("customer", customerData);
		errorObect.put("errorStatus", false);
		errorObect.put("errorMessage", "Deleted Successfully");
		errorObect.put("errorId", -1);
		httpStatus = HttpStatus.ACCEPTED;
		// } else {
		// errorObect.put("errorStatus", true);
		// errorObect.put("errorMessage", "Please check the valid User");
		// errorObect.put("errorId", 1);
		// httpStatus = HttpStatus.BAD_REQUEST;
		// }
		resultObject.put("status", httpStatus.value());
		resultObject.put("error", errorObect);
		session.getTransaction().commit();
		session.close();
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	@GetMapping(value = "getPropertyImage", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@RequestParam long pImageId) {

		byte[] image = new byte[0];

		// Image image = null;

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		PropertyImages propertyImages = new PropertyImages();
		@SuppressWarnings("unchecked")
		Query<PropertyImages> queryRequest = session.createQuery("from PropertyImages where pi_id = " + pImageId);
		if (queryRequest.getResultList().size() > 0) {
			PropertyImages propertyRetrieved = queryRequest.getResultList().get(0);
			System.out.println(propertyRetrieved.getPi_id() + " " + propertyRetrieved.getPi_img());
			propertyImages = propertyRetrieved;
			// session.update(propertyImages);

			try {

				File input_file = new File(propertyImages.getPi_img());

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

	@GetMapping("/getImageById")
	@ResponseBody
	public ByteArrayResource getImageDynamicType(@RequestParam("imageId") int imageId) throws IOException {

		PropertyImages requestTable = new PropertyImages();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		System.out.print("called");
		@SuppressWarnings("unchecked")
		Query<PropertyImages> queryRequest = session.createQuery("from PropertyImages where pi_id = " + imageId);
		if (queryRequest.getResultList().size() > 0) {
			requestTable = queryRequest.getResultList().get(0);
		} else {

			session.getTransaction().commit();
			session.close();
			// return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}

		session.getTransaction().commit();
		session.close();

		System.out.print(requestTable.getPi_img());
		// InputStream in = getClass().getResourceAsStream(requestTable.getPi_img());
		//
		// return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new
		// InputStreamResource(in));

		Path path = null;
		try {
			path = Paths.get(getClass().getResource(requestTable.getPi_img()).toURI());
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
			return resource;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

	// favourite add and remove
	@RequestMapping(method = RequestMethod.POST, value = "/favourite")
	public ResponseEntity<String> favourite(@RequestParam("psp_id") long propertyId,
			@RequestParam("psc_id") long userId, @RequestParam("ps_id") long ps_id) throws IOException {

		HttpStatus httpStatus;

		JSONObject resultObject = new JSONObject();
		JSONObject errorObect = new JSONObject();

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		PropertySaved propertySaved = new PropertySaved();
		propertySaved.setPs_id(ps_id);
		propertySaved.setPsc_id(userId);
		propertySaved.setPsp_id(propertyId);

		@SuppressWarnings("unchecked")
		Query<PropertySaved> queryRequest = session
				.createQuery("from PropertySaved where psp_id = " + propertyId + " and  psc_id = " + userId);
		if (queryRequest.getResultList().size() > 0) {

			propertySaved = queryRequest.getResultList().get(0);

			session.delete(propertySaved);

			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Favourite deleted");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.ACCEPTED;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);

		} else {

			session.save(propertySaved);
			errorObect.put("errorStatus", false);
			errorObect.put("errorMessage", "Favourite Added");
			errorObect.put("errorId", -1);
			httpStatus = HttpStatus.ACCEPTED;

			resultObject.put("status", httpStatus.value());
			resultObject.put("error", errorObect);
			session.getTransaction().commit();
			session.close();
			return new ResponseEntity<String>(resultObject.toString(), httpStatus);
		}
	}

	// TODO Get user
	// getSavedPropertiesByUser
	@RequestMapping(value = "/savedpropertesbyuser")
	public ResponseEntity<String> getSavedPropertyList(@RequestParam("userId") long userid,
			@RequestParam("firstResult") int firstResult, @RequestParam("max") int maxResult) {
		JSONObject resultObject = new JSONObject();
		JSONObject errorObject = new JSONObject();
		HttpStatus httpStatus;

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		JSONArray responseArray = new JSONArray();

		SQLQuery<Object[]> query = session.createSQLQuery(
				"select property_id , (Select ps_id from housing.propertysaved where housing.propertysaved.psp_id = "
						+ userid + ") as id from housing.property");
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			System.out.println("hello started");
			System.out.println(Long.parseLong(row[0].toString()) + " " + row[1].toString());
			Property iterator = new Property();
			iterator.setProperty_id(Long.parseLong(row[0].toString()));
			JSONObject responseObject = new JSONObject(iterator);
			responseArray.put(responseObject);
		}

		// @SuppressWarnings("unchecked")
		// Query<Property> queryResponse = session.createQuery("from Property Join
		// PropertySaved on psc_id = " + userid);
		// queryResponse.setFirstResult(firstResult);
		// queryResponse.setMaxResults(maxResult);

		// for (@SuppressWarnings("rawtypes")
		// Iterator iterator = queryResponse.getResultList().iterator();
		// iterator.hasNext();) {
		// Property responseTable = (Property) iterator.next();
		// JSONObject responseObject = new JSONObject(responseTable);
		// responseArray.put(responseObject);
		// }

		errorObject.put("errorStatus", false);
		errorObject.put("errorMessage", "Valid Property");
		errorObject.put("errorId", -1);
		httpStatus = HttpStatus.ACCEPTED;
		resultObject.put("propertyList", responseArray);
		resultObject.put("error", errorObject);
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

	// TODO GET ListOf users by location
	// getpropertiesList
	@RequestMapping(value = "/listPropertybylocation")
	public ResponseEntity<String> getPropertyListByLocation(@RequestParam("firstResult") int firstResult,
			@RequestParam("max") int maxResult) {
		JSONObject resultObject = new JSONObject();
		JSONObject errorObject = new JSONObject();
		HttpStatus httpStatus;

		Session session = DatabaseUtil.getSessionFactory().openSession();
		session.beginTransaction();

		@SuppressWarnings("unchecked")
		Query<Property> queryResponse = session.createQuery("from Property ");
		queryResponse.setFirstResult(firstResult);
		queryResponse.setMaxResults(maxResult);
		JSONArray responseArray = new JSONArray();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = queryResponse.getResultList().iterator(); iterator.hasNext();) {
			Property responseTable = (Property) iterator.next();
			JSONObject responseObject = new JSONObject(responseTable);
			responseArray.put(responseObject);
		}

		errorObject.put("errorStatus", false);
		errorObject.put("errorMessage", "Valid Property");
		errorObject.put("errorId", -1);
		httpStatus = HttpStatus.ACCEPTED;
		resultObject.put("propertyList", responseArray);
		resultObject.put("error", errorObject);
		return new ResponseEntity<String>(resultObject.toString(), httpStatus);
	}

}
