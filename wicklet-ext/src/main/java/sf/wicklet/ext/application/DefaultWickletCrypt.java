/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.application;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import org.apache.wicket.util.crypt.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sf.blacksun.util.RandomUtil;
import sf.blacksun.util.text.TextUtil;

public class DefaultWickletCrypt implements IWickletCrypt {

	private static final String PREFIX = "wIcklEtEId";
	private static final String CHARACTER_ENCODING = "UTF-8";
	private static final Logger log = LoggerFactory.getLogger(DefaultWickletCrypt.class);
	private static class Lazy {
		static final byte[] salt;
		static final String salt16;
		static final AESCrypt crypt = new AESCrypt();
		static {
			final RandomUtil util = RandomUtil.getSingleton();
			final int len = util.getInt(32, 64);
			salt = util.get(new byte[len]);
			salt16 = util.getString(16, 16);
		}
	}

	private static byte[] salte(final byte[] salt, final byte[] value) {
		final int vlen = value.length;
		final int slen = salt.length;
		final byte[] ret = new byte[vlen];
		for (int i = 0, j = 0; i < vlen; ++i, ++j) {
			if (j >= slen) {
				j = 0;
			}
			ret[i] = (byte)(value[i] ^ salt[j]);
		}
		return ret;
	}

	@Override
	public boolean isEncrypt(final String s) {
		return s.startsWith(PREFIX);
	}

	@Override
	public final String encryptUrlSafe(final String text) {
		String s = Lazy.salt16 + text;
		s += TextUtil.toLowerHex8(s.hashCode());
		try {
			final byte[] encrypted = Lazy.crypt.crypt(
				salte(Lazy.salt, s.getBytes(CHARACTER_ENCODING)), Cipher.ENCRYPT_MODE);
			if (encrypted != null) {
				return PREFIX
					+ new String(new Base64(-1, null, true).encode(encrypted), CHARACTER_ENCODING);
		}} catch (final GeneralSecurityException e) {
			log.error("Unable to encrypt text '" + text + "'", e);
		} catch (final UnsupportedEncodingException e) {
			log.error("Unable to encrypt text '" + text + "'", e);
		}
		return null;
	}
	@Override
	public final String decryptUrlSafe(final String text) {
		if (isEncrypt(text)) {
			byte[] b;
			try {
				b = decrypt(text.substring(PREFIX.length()));
				final String decrypted = new String(salte(Lazy.salt, b), CHARACTER_ENCODING);
				if (decrypted.length() >= 24) {
					final int end = decrypted.length() - 8;
					final String s = decrypted.substring(0, end);
					final String h = decrypted.substring(end);
					final int hash = TextUtil.hexToInt(h, 0);
					if (s.hashCode() != hash) {
						return null;
					}
					return s.substring(16);
			}} catch (final GeneralSecurityException e) {
				log.error("Unable to decrypt text '" + text + "'", e);
			} catch (final UnsupportedEncodingException e) {
				log.error("Unable to decrypt text '" + text + "'", e);
		}}
		return null;
	}

	@Override
	public void setKey(final String key) {
	}

	private byte[] decrypt(final String text) throws GeneralSecurityException {
		final byte[] decoded = new Base64(true).decode(text);
		return Lazy.crypt.crypt(decoded, Cipher.DECRYPT_MODE);
	}
}
