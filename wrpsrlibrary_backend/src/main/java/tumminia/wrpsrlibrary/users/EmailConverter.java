package tumminia.wrpsrlibrary.users;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;

@Component
public class EmailConverter implements AttributeConverter<String, String> {

	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

	@Value("${ATTRIBUTE_CONVERTER_SECRET}") // IL SEGRETO è INSERITO NELL'ENV.PROP
	private String secret;

	@Override // POLITICA DI ENCRYPT
	public String convertToDatabaseColumn(String email) {

		try {
			Key key = new SecretKeySpec(secret.getBytes(), "AES");
			Cipher c = Cipher.getInstance(ALGORITHM);

			c.init(Cipher.ENCRYPT_MODE, key);

			return Base64.getEncoder().encodeToString(c.doFinal(email.getBytes()));

		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	@Override // POLITICA DI DECRYPT
	public String convertToEntityAttribute(String encryptedEmail) {

		try {
			Key key = new SecretKeySpec(secret.getBytes(), "AES");
			Cipher c = Cipher.getInstance(ALGORITHM);

			c.init(Cipher.DECRYPT_MODE, key);

			return new String(c.doFinal(Base64.getDecoder().decode(encryptedEmail)));

		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

}
