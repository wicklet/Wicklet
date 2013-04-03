/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.dsl.html;

public interface IElement extends IChild {
	public abstract String tag();
	public abstract String id();
	//
	public abstract int attrCount();
	public abstract Iterable<IAttribute> attributes();
	public abstract IElement id(String id);
	public abstract IElement a(IAttributes a);
	public abstract IElement a(IAttribute a);
	public abstract IElement a(String name, String value);
	public abstract IElement a(String name, String...values);
	//
	public abstract int childCount();
	public abstract Iterable<IChild> children();
	public abstract IElement c(IChild...children);
	public abstract IElement a(ITop child);
	public abstract IElement a(IElement child);
	public abstract IElement a(IText child);
	public abstract IElement a(ICData child);
	public abstract IElement a(IComment child);
	public abstract IElement a(IPI child);
	public abstract IElement a(IDeclaration child);
	public abstract IElement a(INode child);
}
