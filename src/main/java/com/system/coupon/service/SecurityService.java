package com.system.coupon.service;

import com.system.coupon.rest.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.Security;
import org.json.JSONObject;
import javax.crypto.Cipher;
import java.util.Base64;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityService implements ISecurityService {
    @Value("${encryption.secret}")
    private String key;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String encryptData(Object data) {
        try {
            /* Add BouncyCastle provider for encryption algorithms */
            Security.addProvider(new BouncyCastleProvider());

            /* ObjectMapper for converting objects to JSON strings */
            String jsonString = data instanceof String ? (String) data : objectMapper.writeValueAsString(data);
            byte[] clean = jsonString.getBytes();

            /* Creates a secret key specification for AES using the provided key */
            SecretKeySpec keySpec = new SecretKeySpec(this.key.getBytes(StandardCharsets.UTF_8), "AES");

            /* Generate a secure random IV */
            byte[] iv = new byte[16];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            /* Initializes the cipher for AES encryption with CBC mode and PKCS5 padding */
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(clean);

            /* Combines the IV and the encrypted text into a single byte array */
            byte[] encryptedIVAndText = new byte[ivSpec.getIV().length + encrypted.length];

            /* Copies the IV and the encrypted text into the combined byte array */
            System.arraycopy(ivSpec.getIV(), 0, encryptedIVAndText, 0, ivSpec.getIV().length);
            System.arraycopy(encrypted, 0, encryptedIVAndText, iv.length, encrypted.length);

            /* Encodes the combined byte array to a Base64 string */
            return Base64.getEncoder().encodeToString(encryptedIVAndText);
        } catch (Exception e) {
            Utils.logError(e, "encryptData");
            throw new RuntimeException(e);
        }
    }
    public JSONObject decryptData(String encryptedString) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            // Decode the Base64 encoded encrypted string
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);

            // Extract the IV from the encrypted bytes (first 16 bytes)
            byte[] iv = new byte[16];
            System.arraycopy(encryptedBytes, 0, iv, 0, iv.length);

            // Extract the encrypted password bytes
            byte[] encryptedPasswordBytes = new byte[encryptedBytes.length - iv.length];
            System.arraycopy(encryptedBytes, iv.length, encryptedPasswordBytes, 0, encryptedPasswordBytes.length);

            // Initialize the key and IV specifications
            SecretKeySpec keySpec = new SecretKeySpec(this.key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Initialize the cipher for decryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // Decrypt the encrypted password bytes
            byte[] decryptedVal = cipher.doFinal(encryptedPasswordBytes);

            // Convert the decrypted value to string and parse it as JSON
            String decryptedString = new String(decryptedVal, StandardCharsets.UTF_8);
            return new JSONObject(decryptedString);
        } catch (Exception e) {
            Utils.logError(e, "decryptData");
            throw new RuntimeException(e);
        }
    }

}
