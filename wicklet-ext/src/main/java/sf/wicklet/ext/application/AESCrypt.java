/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.application;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.apache.wicket.util.crypt.AbstractCrypt;

public class AESCrypt extends AbstractCrypt {

	private static class Lazy {
		static final Cipher cipher;
		static final KeyGenerator gen;
		static final SecretKey key;
		static {
			try {
				cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			} catch (final NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new RuntimeException("ERROR: AES/ECB/PKCS5Padding cipher not available");
			} catch (final NoSuchPaddingException e) {
				e.printStackTrace();
				throw new RuntimeException("ERROR: AES/ECB/PKCS5Padding cipher not available");
			}
			try {
				gen = KeyGenerator.getInstance("AES");
			} catch (final NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new RuntimeException("ERROR: AES key generator not available");
			}
			gen.init(256);
			key = gen.generateKey();
		}
	}

	public AESCrypt() {
	}

	@Override
	protected byte[] crypt(final byte[] input, final int mode) throws GeneralSecurityException {
		Lazy.cipher.init(mode, Lazy.key);
		return Lazy.cipher.doFinal(input);
	}
}
