package com.pikamachu.services.common.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;

import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;

/**
 * The Class Token Manager.
 */
@Singleton
public class TokenManager {

	/** The log. */
	private static Logger log = Logger.getLogger(TokenManager.class.getName());

	/** The jwe key. */
	private Key jweKey = new AesKey(getConfiguration("jweKey").getBytes());
	
	/** The key management algorithm. */
	private String keyManagementAlgorithm = getConfiguration("keyManagementAlgorithm");
	
	/** The content encryption algorithm. */
	private String contentEncryptionAlgorithm = getConfiguration("contentEncryptionAlgorithm");

	/**
	 * The Auth issuer.
	 */
	private String authIssuer = getConfiguration("authIssuer");

	/**
	 * Generate auth token.
	 *
	 * @param username the username
	 * @param applications the applications
	 * @return the string
	 * @throws Exception the exception
	 */
	public String generateAuthToken(String username, List<String> applications) throws Exception {

		String token = encryptJweToken(username, applications);

		return token;
	}

	/**
	 * Decode auth token.
	 *
	 * @param token the token
	 * @return the jwt claims
	 * @throws Exception the exception
	 */
	public JwtClaims decodeAuthToken(String token) throws Exception {

		JwtClaims claimsJson = decrytJweToken(token);

		return claimsJson;
	}

	/**
	 * Jwe Token management.
	 * @param subject the subject
	 * @param audiences the audiences
	 * @return the string
	 */
	
	private String encryptJweToken(String subject, List<String> audiences) {
		String serializedJwe = null;
		try {
						
	        JwtClaims claims = new JwtClaims();
	        
	        claims.setIssuer(authIssuer);  // who creates the token and signs it
	        claims.setAudience(audiences); // to whom the token is intended to be sent
	        claims.setExpirationTimeMinutesInTheFuture(30); // time when the token will expire (30 minutes from now)
	        claims.setGeneratedJwtId(); // a unique identifier for the token
	        claims.setIssuedAtToNow();  // when the token was issued/created (now)
	        claims.setSubject(subject); // the subject/principal is whom the token is about
	        
	        JsonWebEncryption jwe = new JsonWebEncryption();

			jwe.setPayload(claims.toJson());
			jwe.setAlgorithmHeaderValue(keyManagementAlgorithm);
			jwe.setEncryptionMethodHeaderParameter(contentEncryptionAlgorithm);
			jwe.setKey(jweKey);
			
			serializedJwe = jwe.getCompactSerialization();
		} catch (Exception e) {
			log.log(Level.WARNING, "encryptJweToken exception", e);
		}
		
		return serializedJwe;
	}

	/**
	 * Decryt jwe token.
	 *
	 * @param serializedJwe the serialized jwe
	 * @return the jwt claims
	 */
	private JwtClaims decrytJweToken(String serializedJwe) {
		JwtClaims claims = null;
		try {
			JsonWebEncryption jwe = new JsonWebEncryption();

			jwe = new JsonWebEncryption();
			jwe.setKey(jweKey);
			jwe.setCompactSerialization(serializedJwe);
			
			claims = JwtClaims.parse(jwe.getPayload());
		} catch (Exception e) {
			log.log(Level.WARNING, "decrytJweToken exception", e);
		}

		return claims;
	}

	/**
	 * Gets auth issuer.
	 *
	 * @return the auth issuer
	 */
	public String getAuthIssuer() {
		return authIssuer;
	}	
	
	/**
	 * Properties management.
	 */

	/** The Constant PROPERTIES_FILE_NAME. */
	private static final String PROPERTIES_FILE_NAME = "token-security.properties";

	/** The properties. */
	private static Properties properties;

	/**
	 * Gets properties.
	 *
	 * @return the properties
	 */
	private static synchronized Properties getProperties() {
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
				properties.load(TokenManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
			} catch (IOException ex) {
				log.log(Level.SEVERE, "Unexpected exception: ", ex);
			}
		}
		return properties;
	}

	/**
	 * Gets the configuration.
	 *
	 * @param configKey             the config key
	 * @return the configuration
	 */
	private static String getConfiguration(String configKey) {
		return getProperties().getProperty(configKey);
	}

}
