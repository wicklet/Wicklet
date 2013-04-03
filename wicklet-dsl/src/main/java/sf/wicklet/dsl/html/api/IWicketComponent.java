/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.dsl.html.api;

public interface IWicketComponent extends IElement {
	public String getWicketId();
	public boolean isAuto();
	public int getFlags();
	public IWicketComponent setAuto(boolean b);
	public IWicketComponent setFlags(int flags);
}
