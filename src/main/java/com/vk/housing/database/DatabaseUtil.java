package com.vk.housing.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.vk.housing.database.model.Property;
import com.vk.housing.database.model.PropertyImages;
import com.vk.housing.database.model.PropertySaved;
import com.vk.housing.database.model.User;

public class DatabaseUtil {

	private static SessionFactory sessionFactory;

	private static SessionFactory buildFactory() {

		Configuration configuration = new Configuration().addAnnotatedClass(User.class)
				.addAnnotatedClass(Property.class).addAnnotatedClass(PropertyImages.class)
				.addAnnotatedClass(PropertySaved.class).configure();

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildFactory();
		return sessionFactory;
	}
}
