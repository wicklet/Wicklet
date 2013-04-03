/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.client.test.test01.client;

import sf.wicklet.gwt.client.ajax.WickletAjax;
import sf.wicklet.gwt.client.ajax.WickletAjax.DefaultWickletAjaxCallback;
import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder;
import sf.wicklet.gwt.client.test.test01.shared.WID;
import sf.wicklet.gwt.client.ui.dialog.InfoDialog;
import sf.wicklet.gwt.client.ui.dialog.LoginDialog;
import sf.wicklet.gwt.client.util.GwtDomUtil;
import sf.wicklet.gwt.client.util.GwtTextUtil;
import sf.wicklet.gwt.client.util.GwtUrlUtil;
import sf.wicklet.gwt.client.util.GwtXmlUtil;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 */
public class Test01 implements EntryPoint {

	static final String SERVER_ERROR = "An error occurred while "
		+ "attempting to contact the server. Please check your network "
		+ "connection and try again.";

	@Override
	public void onModuleLoad() {
		new GwtBuilder() {
			void build() {
				testtoppanel();
				testmenubar();
				teststackpanel();
				testajaxpanel();
			}
			private void testtoppanel() {
				final Element userelm = DOM.getElementById(WID.topUser.toString());
				String user = null;
				if (userelm != null) {
					user = userelm.getInnerText();
					if (GwtTextUtil.isEmpty(user)) {
						user = null;
					}
					userelm.removeFromParent();
				}
				final Element homeelm = GwtDomUtil.findAndDetach(WID.topHome);
				final Element loginelm = GwtDomUtil.findAndDetach(WID.topLogin);
				final Element logoutelm = GwtDomUtil.findAndDetach(WID.topLogout);
				final boolean loggedin = (user != null);
				final boolean isadmin = ("admin".equals(user));
				final IGwtLabel userlabel = (userelm == null
					? (IGwtLabel)label(user).id(WID.topUser)
					: label(userelm));
				rootpanel(
					WID.topPanel,
					panel(
						id(WID.topMenu),
						css(WID.topMenu),
						anchor(homeelm),
						label(" | "),
						ipanel(hyperlink("Admin", "admin").id(WID.topAdmin), label(" ")).hide(
							!isadmin),
						panel(
							css(WID.loginPanel),
							ipanel(userlabel, label(" ")).hide(!loggedin), //
							ipanel(
								hyperlink(loginelm).setLink("login").setText("Login")
									.onclick(
										new ClickHandler() {
											@Override
											public void onClick(
												final ClickEvent event) {
												event.preventDefault();
												login();
											}
										}),
								label(" ")).hide(loggedin),
							ipanel(anchor(logoutelm), label(" ")).hide(!loggedin)),
						form(
							id(WID.searchForm),
							css(WID.searchForm),
							textbox("To be implemented").id(WID.searchText).css(
								WID.searchText))));
			}
			private void testmenubar() {
				rootpanel(WID.menubar, istyle("margin-top: 50px;")).c(
					hmenubar(
						menuitem(
							"File",
							vmenubar(
								menuitem("Open", cmd("Open")), //
								menuitem("Close", cmd("Close")))),
						menuitem(
							"Edit",
							vmenubar(
								menuitem("Copy", cmd("Copy")),
								menuitem("Delete", cmd("Delete")),
								menuitem("Paste", cmd("Paste")))),
						menuitem("About", cmd("About"))));
			}
			private void teststackpanel() {
				rootpanel(WID.stackpanel).c(
					stackpanel().istyle("margin: 5px;").c(
						header(
							"Documentation",
							vpanel(
								hyperlink("Introduction"),
								hyperlink("Projects"),
								hyperlink("Sources"),
								hyperlink("License"))), //
						header("Wiki", panel(hyperlink("Home", "WikiHome"))),
						header(
							"Forum",
							vpanel(
								hyperlink("Forum", "ForumHome"), //
								anchor("TestHref", "testhref"),
								anchor("TestHref")))));
			}
			private void testajaxpanel() {
				rootpanel(WID.ajaxpanel).istyle("margin: 10px;").c(
					html("<h3>TestAjax</h3>"),
					vpanel(
						anchor(byId(WID.ajaxshow)).onclick(
							new ClickHandler() {
								@Override
								public void onClick(final ClickEvent event) {
									event.preventDefault();
									WickletAjax.ajax(
										WickletAjax.POST,
										GwtUrlUtil.setUrlPath(
											Window.Location.getHref(),
											"/service"),
										"test=instance",
										new DefaultWickletAjaxCallback());
								}
							}),
						anchor(byId(WID.ajaxshowstatic)).onclick(
							new ClickHandler() {
								@Override
								public void onClick(final ClickEvent event) {
									event.preventDefault();
									WickletAjax.ajax(
										WickletAjax.POST,
										GwtUrlUtil.setUrlPath(
											Window.Location.getHref(),
											"/service"),
										"test=static",
										new DefaultWickletAjaxCallback());
								}
							}),
						anchor(byId(WID.ajaxhiddenbylink)),
						anchor(byId(WID.ajaxhiddenbystyle)),
						panel(byId(WID.ajaxresult))));
				createAjaxApi(new TestAjax());
			}
			private ScheduledCommand cmd(final String msg) {
				return new ScheduledCommand() {
					@Override
					public void execute() {
						Window.alert(msg);
					}
				};
			}
		}.build();
	}

	//    native void createAjaxApi(TestAjax c)
	//    /*-{
	//    window.Test01Api = {
	//    testAjaxStatic01 : function(xml) {
	//    @sf.wicklet.gwt.client.test.test01.client.Test01.TestAjax::testAjaxStatic01(Ljava/lang/String;)(xml);
	//    },
	//    testAjax01 : function(xml) {
	//    c.@sf.wicklet.gwt.client.test.test01.client.Test01.TestAjax::testAjax01(Ljava/lang/String;)(xml);
	//    }
	//    };
	//    }-*/
	//    ;

	native void createAjaxApi(TestAjax c)
	/*-{
	$wnd.Test01Api = {
	testAjaxStatic01 : $entry(@sf.wicklet.gwt.client.test.test01.client.Test01.TestAjax::testAjaxStatic01(Ljava/lang/String;)),
	testAjax01 : function(xml) {
	c.@sf.wicklet.gwt.client.test.test01.client.Test01.TestAjax::testAjax01(Ljava/lang/String;)(xml);
	}
	};
	}-*/
	;

	public static class TestAjax {
		public static void testAjaxStatic01(final String xml) {
			testajax01("static", xml);
		}
		private static void testajax01(final String title, final String xml) {
			new GwtBuilder() {
				void build() {
					final RootPanel root = RootPanel.get(WID.ajaxpanel.toString());
					final ComplexPanel vpanel
						= (ComplexPanel)root.getWidget(root.getWidgetCount() - 1);
					vpanel.remove(vpanel.getWidgetCount() - 1);
					final IGwtPanel panel = templatepanel(GwtXmlUtil.unescXmlString(xml));
					vpanel.add(panel);
					// FIXME htmlpanel.add() add to a wrap div instead.
					panel.c(
						panel(
							byId(WID.ajaxcontent),
							textbox(title),
							// NOTE: panel is not connected to the logical GWT widget system here,
							// so onclick handler would not be invoked.
							button("Submit").onclick(
								new ClickHandler() {
									@Override
									public void onClick(final ClickEvent event) {
										event.preventDefault();
										new InfoDialog(
											title, "Ajax test dialog")
											.showAtCenter();
									}
								})));
				}
			}.build();
		}
		public void testAjax01(final String xml) {
			testajax01("instance", xml);
		}
	}

	void login() {
		new LoginDialog() {
			@Override
			protected void onOK() {
				setMessage("Login failed");
			}
		}.showAtCenter();
	}
}
