/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.shared;

public class Shared {

	public interface Href {
		String WikiHome = "home";
		String WikiProjects = "projects";
		String WikiSources = "sources";
		String WikiLicense = "license";
		String WikiAbout = "about";
		//
		String AdminUsers = "admin-users";
		String AdminWikis = "admin-wikis";
		String AdminForums = "admin-forums";
		String AdminBugs = "admin-bugs";
		//
		String UserPreference = "user-preference";
	}

	public interface Services {
		String ListWiki = "listwiki";
		String GetWiki = "getwiki";
		String PutWiki = "putwiki";
		String WikiComment = "wikicomment";
	}

	public interface Admin {
		String ListUsers = "listusers";
		String DeleteUser = "deleteuser";
		String EditUser = "edituser";
	}

	public interface ContextPath {
		String Service = "/s/service";
		String AdminService = "/s/adminservice";
		String WikiComment = "/s/wikicomment";
		String Login = "/p/login";
		String Logout = "/p/logout";
		String Admin = "/p/admin";
		String User = "/p/user";
	}
}
