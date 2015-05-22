package edu.harvard.huit.iam.util;

import org.jasypt.util.text.BasicTextEncryptor;
import java.util.Properties;
import java.io.IOException;

public class EncryptorUtil {

    private BasicTextEncryptor basicTextEncryptor = null;

	public String encrypt(String text) {
		return this.basicTextEncryptor.encrypt(text);
	}
	
	public String decrypt(String encryptedText) {
		return this.basicTextEncryptor.decrypt(encryptedText);
	}

    public EncryptorUtil() {
        if (basicTextEncryptor == null) {
            basicTextEncryptor = new BasicTextEncryptor();
            Properties props = new Properties();
            try {
                props.load(ClassLoader.getSystemResourceAsStream("encryption.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            basicTextEncryptor.setPassword(props.getProperty("encrypt"));
            //System.out.println("passphrase" + props.getProperty("encrypt"));
        }
    }
}
