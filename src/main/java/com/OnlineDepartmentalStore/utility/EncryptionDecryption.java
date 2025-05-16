package com.OnlineDepartmentalStore.utility;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptionDecryption {
	private static final String SECRET_KEY = "my_super_secret_key";
    private static final String SALT = "ssshhhhhhhhhhh!!!!";

    private static final Logger LOGGER = Logger.getLogger(EncryptionDecryption.class.getName());

    public static String encrypt(String strToEncrypt) {
        try {
            // Create a 16-byte random IV (Initialization Vector)
            byte[] iv = new byte[16];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Generate encryption key using PBKDF2 with the given key and salt
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tempKey = keyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(tempKey.getEncoded(), "AES");

            // Set up AES cipher in CBC mode with padding
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            // Encrypt the text and combine the IV with the encrypted data
            byte[] encryptedData = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] finalData = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, finalData, 0, iv.length);
            System.arraycopy(encryptedData, 0, finalData, iv.length, encryptedData.length);

            // Encode to Base64 to make the encrypted string safe to store or transmit
            return Base64.getEncoder().encodeToString(finalData);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Problem occurred during encryption: ", e);
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            // Decode Base64 string to get byte array with IV + ciphertext
            byte[] encryptedWithIv = Base64.getDecoder().decode(strToDecrypt);

            // Pull out the IV portion
            byte[] iv = new byte[16];
            System.arraycopy(encryptedWithIv, 0, iv, 0, iv.length);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Pull out the actual encrypted data
            int ciphertextLength = encryptedWithIv.length - iv.length;
            byte[] encryptedData = new byte[ciphertextLength];
            System.arraycopy(encryptedWithIv, iv.length, encryptedData, 0, ciphertextLength);

            // Reconstruct the same key used for encryption
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tempKey = keyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(tempKey.getEncoded(), "AES");

            // Set up cipher for decryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            // Perform decryption and return the original string
            return new String(cipher.doFinal(encryptedData), StandardCharsets.UTF_8);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Problem occurred during decryption: ", e);
        }
        return null;
    }
}
