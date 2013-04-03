/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.shared;

public interface GwtHttp {

	public interface Header {
		String Accept = "Accept";
		String AcceptCharset = "Accept-Charset";
		String AcceptEncoding = "Accept-Encoding";
		String AcceptLanguage = "Accept-Language";
		String AcceptRanges = "Accept-Ranges";
		String Age = "Age";
		String Allow = "Allow";
		String Authorization = "Authorization";
		String CacheControl = "Cache-Control";
		String Connection = "Connection";
		String ContentBase = "Content-Base";
		String ContentEncoding = "Content-Encoding";
		String ContentLanguage = "Content-Language";
		String ContentLength = "Content-Length";
		String ContentLocation = "Content-Location";
		String ContentMD5 = "Content-MD5";
		String ContentRange = "Content-Range";
		String ContentType = "Content-Type";
		String Date = "Date";
		String ETag = "ETag";
		String Expires = "Expires";
		String From = "From";
		String Host = "Host";
		String IfModifiedSince = "If-Modified-Since";
		String IfMatch = "If-Match";
		String IfNoneMatch = "If-None-Match";
		String IfRange = "If-Range";
		String IfUnmodifiedSince = "If-Unmodified-Since";
		String LastModified = "Last-Modified";
		String Location = "Location";
		String MaxForwards = "Max-Forwards";
		String Pragma = "Pragma";
		String ProxyAuthenticate = "Proxy-Authenticate";
		String ProxyAuthorization = "Proxy-Authorization";
		String Public = "Public";
		String Range = "Range";
		String Referer = "Referer";
		String RetryAfter = "Retry-After";
		String Server = "Server";
		String TransferEncoding = "Transfer-Encoding";
		String Upgrade = "Upgrade";
		String UserAgent = "User-Agent";
		String Vary = "Vary";
		String Via = "Via";
		String Warning = "Warning";
		String WWWAuthenticate = "WWW-Authenticate";
	}

	public interface Wicklet {
		String Accept = "Wicklet-Accept";
		String Content = "Wicklet-Content";
	}

	public interface Wicket {
		String Ajax = "Wicket-Ajax";
		String AjaxBaseURL = "Wicket-Ajax-BaseURL";
	}

	public enum Status {
		Continue(100, "Continue"),
		SwitchingProtocols(101, "Switching Protocols"),
		OK(200, "OK"),
		Created(201, "Created"),
		Accepted(202, "Accepted"),
		NonAuthoritativeInformation(203, "Non-Authoritative Information"),
		NoContent(204, "No Content"),
		ResetContent(205, "Reset Content"),
		PartialContent(206, "Partial Content"),
		MultipleChoices(300, "Multiple Choices"),
		MovedPermanently(301, "Moved Permanently"),
		MovedTemporarily(302, "Moved Temporarily"),
		SeeOther(303, "See Other"),
		NotModified(304, "Not Modified"),
		UseProxy(305, "Use Proxy"),
		BadRequest(400, "Bad Request"),
		Unauthorized(401, "Unauthorized"),
		PaymentRequired(402, "Payment Required"),
		Forbidden(403, "Forbidden"),
		NotFound(404, "Not Found"),
		MethodNotAllowed(405, "Method Not Allowed"),
		NotAcceptable(406, "Not Acceptable"),
		ProxyAuthenticationRequired(407, "Proxy Authentication Required"),
		RequestTimeout(408, "Request Timeout"),
		Conflict(409, "Conflict"),
		Gone(410, "Gone"),
		LengthRequired(411, "Length Required"),
		PreconditionFailed(412, "Precondition Failed"),
		RequestEntityTooLarge(413, "Request Entity Too Large"),
		RequestURITooLong(414, "Request-URI Too Long"),
		UnsupportedMediaType(415, "Unsupported Media Type"),
		InternalServerError(500, "Internal Server Error"),
		NotImplemented(501, "Not Implemented"),
		BadGateway(502, "Bad Gateway"),
		ServiceUnavailable(503, "Service Unavailable"),
		GatewayTimeout(504, "Gateway Timeout"),
		HTTPVersionNotSupported(505, "HTTP Version Not Supported");

		private final int code;
		private final String msg;

		Status(final int code, final String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int code() {
			return code;
		}

		public String msg() {
			return msg;
		}

		public static boolean isOK(final int status) {
			return status == 200;
		}

		public static boolean isRedirect(final int status) {
			return status >= 300 && status < 400;
		}
	}
}
