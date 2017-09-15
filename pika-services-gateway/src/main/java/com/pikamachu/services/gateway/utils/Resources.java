package com.pikamachu.services.gateway.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.pikamachu.services.common.annotations.Config;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence
 * context, to CDI beans.
 *
 * <p>
 * Example injection on a managed bean field:
 * </p>
 *
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class Resources {

	/** The log. */
	private static Logger log = Logger.getLogger(Resources.class.getName());

	/** The properties. */
	private volatile static Properties properties;

	/** The Constant PROPERTIES_FILE_NAME. */
	public static final String PROPERTIES_FILE_NAME = "services-gateway.properties";

	// use @SuppressWarnings to tell IDE to ignore warnings about field not
	// being referenced directly
	///** The em. */
	//@SuppressWarnings("unused")
	//@Produces
	//@PersistenceContext
	//private EntityManager em;

	/**
	 * Produce log.
	 *
	 * @param injectionPoint the injection point
	 * @return the logger
	 */
	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	/**
	 * Gets properties.
	 *
	 * @return the properties
	 */
	private synchronized static Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			try {
				Object userHome = System.getProperty("user.home");
				Object fileSeparator = System.getProperty("file.separator");
				if (userHome != null && fileSeparator != null) {
					String propertiesFileName = "" + userHome + fileSeparator + PROPERTIES_FILE_NAME;
					InputStream resource = new FileInputStream(new File(propertiesFileName));
					if (resource != null) {
						log.info("Loading properties from " + propertiesFileName);
						properties.load(resource);
						resource.close();
						// Si no peta ya hemos acabado
						return properties;
					}
				}
			} catch (Exception e) {
				log.log(Level.WARNING, "Cannot load external " + PROPERTIES_FILE_NAME + " file. Exception " + e.getMessage());
			}
			try {
				log.info("Loading properties from " + PROPERTIES_FILE_NAME);
				properties.load(Resources.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
			} catch (IOException ex) {
				log.log(Level.SEVERE, "Unexpected exception: ", ex);
			}
		}
		return properties;
	}

	/**
	 * Gets the configuration.
	 *
	 * @param configKey the config key
	 * @return the configuration
	 */
	public static String getConfiguration(String configKey) {
		return getProperties().getProperty(configKey);
	}

	/**
	 * Gets the configuration.
	 *
	 * @param p the p
	 * @return the configuration
	 */
	@Produces 
	@Config
	public String getConfiguration(InjectionPoint p) {
		String configKey = p.getMember().getDeclaringClass().getName() + "." + p.getMember().getName();
		Properties config = getProperties();
		if (config.getProperty(configKey) == null) {
			configKey = p.getMember().getDeclaringClass().getSimpleName() + "." + p.getMember().getName();
			if (config.getProperty(configKey) == null) {
				configKey = p.getMember().getName();
			}
		}
		log.log(Level.INFO, "Config key= " + configKey + " value = " + config.getProperty(configKey));
		return config.getProperty(configKey);
	}
}
