/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.support;

public class WickletPasswd {
	private static final String SALT = "asdf09we87r-21263sdflxcmn29473456y^)^&%sefaskdfsjgfd61031lsdhfasd";
	public static void main(final String[] args) {
		String salt = SALT;
		int index = 0;
		if ("-s".equals(args[index])) {
			salt = args[index + 1];
			index += 2;
		}
		final byte[] bsalt = salt.getBytes();
		for (int i = index; i < args.length; ++i) {
			final String s = args[i];
			System.out.println(s + ": " + sf.wicklet.gwt.site.server.Config.hash(bsalt, s));
	}}
}
