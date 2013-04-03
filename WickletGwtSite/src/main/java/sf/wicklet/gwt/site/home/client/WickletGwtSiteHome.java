/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.home.client;

import java.util.List;
import sf.wicklet.gwt.client.HistoryChangeListener;
import sf.wicklet.gwt.client.ajax.WickletAjax;
import sf.wicklet.gwt.client.ajax.WickletAjax.XmlWickletAjaxCallback;
import sf.wicklet.gwt.client.dsl.gwt.GwtBuilder;
import sf.wicklet.gwt.client.dsl.gwt.IGwtBuilder.IGwtComplexPanel;
import sf.wicklet.gwt.client.ui.dialog.ConfirmationDialog;
import sf.wicklet.gwt.client.ui.dialog.TextEditDialog;
import sf.wicklet.gwt.client.ui.richtexttoolbar.RichTextToolbar;
import sf.wicklet.gwt.client.ui.richtexttoolbar.RichTextToolbar.IToolbarHandler;
import sf.wicklet.gwt.client.util.DomCache;
import sf.wicklet.gwt.client.util.GwtDomUtil;
import sf.wicklet.gwt.client.util.GwtUrlUtil;
import sf.wicklet.gwt.client.util.GwtUrlUtil.UrlParts;
import sf.wicklet.gwt.client.util.GwtXmlUtil;
import sf.wicklet.gwt.client.util.IRunnable;
import sf.wicklet.gwt.shared.GwtHttp;
import sf.wicklet.gwt.site.clients.CConf;
import sf.wicklet.gwt.site.clients.WickletGwtSiteBuilder;
import sf.wicklet.gwt.site.clients.WickletGwtSiteUtil;
import sf.wicklet.gwt.site.shared.ICS;
import sf.wicklet.gwt.site.shared.Role;
import sf.wicklet.gwt.site.shared.Shared;
import sf.wicklet.gwt.site.shared.Shared.ContextPath;
import sf.wicklet.gwt.site.shared.Shared.Href;
import sf.wicklet.gwt.site.shared.Shared.Services;
import sf.wicklet.gwt.site.shared.WID;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 */
public class WickletGwtSiteHome implements EntryPoint {

    static final String SERVER_ERROR = "An error occurred while "
        + "attempting to contact the server. Please check your network "
        + "connection and try again.";

    IGwtComplexPanel topPanel;
    IGwtComplexPanel rightPanel;

    @Override
    public void onModuleLoad() {
        HistoryChangeListener.init();
        final String page = WickletGwtSiteUtil.getPageToken();
        if ("/p/admin".equals(page)) {
            CConf.runAsync(
                new RunAsyncCallback() {
                    @Override
                    public void onSuccess() {
                        initAdminPage();
                    }
                    @Override
                    public void onFailure(final Throwable e) {
                        CConf.debug("ERROR: failed to run initAdminPage", e);
                    }
                });
        } else if ("/p/user".equals(page)) {
            CConf.runAsync(
                new RunAsyncCallback() {
                    @Override
                    public void onSuccess() {
                        initUserPage();
                    }
                    @Override
                    public void onFailure(final Throwable e) {
                        CConf.debug("ERROR: failed to run initUserPage", e);
                    }
                });
        } else {
            CConf.runAsync(
                new RunAsyncCallback() {
                    @Override
                    public void onSuccess() {
                        initHomePage();
                    }
                    @Override
                    public void onFailure(final Throwable e) {
                        CConf.debug("ERROR: failed to run initHomePage", e);
                    }
                });
    }}

    void initAdminPage() {
        HistoryChangeListener.get().putHandler(
            "",
            new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(final ValueChangeEvent<String> event) {
                    showAdmin(Href.AdminUsers);
                }
            });
        new WickletGwtSiteBuilder() {
            void build() {
                rootpanel(WID.bodyContent.toString()).c(
                    panel(
                        byId(WID.threePane), //
                        topPanel = topPanel(),
                        leftPanel(),
                        rightPanel = rightPanel()));
                showAdmin(Href.AdminUsers);
            }
            protected IGwtPanel leftPanel() {
                return panel(
                    byId(WID.leftPanel),
                    panel(
                        byId(WID.leftTopPanel),
                        stackpanel().istyle("width: 100%;").c(
                            populatePanel(
                                "Admin",
                                WID.adminPanel.toString(),
                                new IRunnable<String>() {
                                    @Override
                                    public void run(final String token) {
                                        showAdmin(token);
                                    }
                                }))));
            }
        }.build();
    }

    void initUserPage() {
        HistoryChangeListener.get().putHandler(
            "",
            new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(final ValueChangeEvent<String> event) {
                    showUser(Href.UserPreference);
                }
            });
        new WickletGwtSiteBuilder() {
            void build() {
                rootpanel(WID.bodyContent.toString()).c(
                    panel(
                        byId(WID.threePane), //
                        topPanel = topPanel(),
                        leftPanel(),
                        rightPanel = rightPanel()));
                showUser(Href.UserPreference);
            }
            protected IGwtComplexPanel leftPanel() {
                return panel(
                    byId(WID.leftPanel),
                    panel(
                        byId(WID.leftTopPanel),
                        stackpanel().istyle("width: 100%;").c(
                            populatePanel(
                                "Preference",
                                WID.userPanel.toString(),
                                new IRunnable<String>() {
                                    @Override
                                    public void run(final String token) {
                                        showUser(token);
                                    }
                                }))));
            }
        }.build();
    }

    void initHomePage() {
        HistoryChangeListener.get().putHandler(
            "",
            new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(final ValueChangeEvent<String> event) {
                    showWiki(Href.WikiHome);
                }
            });
        new WickletGwtSiteBuilder() {
            void build() {
                rootpanel(WID.bodyContent.toString()).c(
                    panel(
                        byId(WID.threePane), //
                        topPanel = topPanel(),
                        leftPanel(),
                        rightPanel = rightPanel()));
                showWiki(Href.WikiHome);
            }
            protected IGwtPanel leftPanel() {
                return panel(
                    byId(WID.leftPanel),
                    panel(
                        byId(WID.leftTopPanel),
                        stackpanel().istyle("width: 100%;").c(
                            populatePanel(
                                "Wiki",
                                "wikiPanel",
                                new IRunnable<String>() {
                                    @Override
                                    public void run(final String href) {
                                        showWiki(href);
                                    }
                                }), //
                            populatePanel(
                                "News",
                                "newsPanel",
                                new IRunnable<String>() {
                                    @Override
                                    public void run(final String href) {
                                        showNews(href);
                                    }
                                }), //
                            populatePanel(
                                "Forum",
                                "forumPanel",
                                new IRunnable<String>() {
                                    @Override
                                    public void run(final String href) {
                                        showForum(href);
                                    }
                                }),
                            populatePanel(
                                "Bugs",
                                "bugsPanel",
                                new IRunnable<String>() {
                                    @Override
                                    public void run(final String href) {
                                        showBugs(href);
                                    }
                                }))));
            }
        }.build();
    }

    public void showWiki(final String href) {
        WickletAjax.ajax(
            WickletGwtSiteUtil.getServiceUrl(),
            "action=" + Services.GetWiki + "&href=" + href,
            new WickletAjax.XmlWickletAjaxCallback() {
                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    CConf.debug("# getwiki");
                    new GwtBuilder() {
                        void build() {
                            clearRightPanel();
                            rightPanel.c(
                                wikitoolbar(), //
                                htmlpanel(response.getText(), istyle("padding: 10px;")));
                        }
                        private IGwtPanel wikitoolbar() {
                            final boolean iswriter = WickletGwtSiteUtil.hasRole(Role.writer.name());
                            return panel(
                                istyle(
                                    "width: 100%; line-height: 32px; background-color: #eee; text-align: right; vertical-align: middle"),
                                panel(
                                    istyle("line-height: 32px; padding: 0px 10px;"),
                                    // NOTE: no history here
                                    hyperlink("Comment", "wiki-comment").onclick(
                                        new ClickHandler() {
                                            @Override
                                            public void onClick(final ClickEvent event) {
                                                event.preventDefault();
                                                wikiComment(href);
                                            }
                                        }), //
                                    (iswriter ? label(" | ") : label("")),
                                    (iswriter
                                        // NOTE: no history here
                                        ? hyperlink("Edit", "wiki-edit").onclick(
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(final ClickEvent event) {
                                                    event.preventDefault();
                                                    wikiEdit(href);
                                                }
                                            })
                                        : label(""))));
                        }
                    }.build();
                }
                @Override
                public void onError(final Request request, final Throwable e) {
                    final String msg = "ERROR: Show wiki failed: " + href;
                    CConf.debug(msg + ": " + request, e);
                    showError(msg);
                }
            });
    }

    void wikiGet(final String href, final WickletAjax.WickletAjaxCallback cb) {
        WickletAjax.ajax(
            WickletGwtSiteUtil.getServiceUrl(),
            "action=" + Services.GetWiki + "&href=" + href,
            new XmlWickletAjaxCallback() {
                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    cb.onResponseReceived(request, response);
                }
                @Override
                public void onError(final Request request, final Throwable e) {
                    cb.onError(request, e);
                }
            });
    }

    void wikiPut(final String href, final String html) {
        WickletAjax.ajax(
            WickletGwtSiteUtil.getContextUrl(ContextPath.Service),
            ("action=" + Services.PutWiki)
                + ("&href=" + URL.encodeQueryString(href))
                + ("&" + WID.commentText + "=" + URL.encodeQueryString(html)),
            new XmlWickletAjaxCallback() {
                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    if (GwtHttp.Status.isOK(response.getStatusCode())) {
                        showWiki(href);
                    } else {
                        showError("ERROR: " + response.getStatusText());
                }}
                @Override
                public void onError(final Request request, final Throwable e) {
                    CConf.debug("ERROR: Update wiki failed: " + request, e);
                    showError("ERROR: Update wiki failed");
                }
            });
    }

    public void wikiEdit(final String href) {
        wikiGet(
            href,
            new WickletAjax.WickletAjaxCallback() {
                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    CConf.debug("# getwiki");
                    clearRightPanel();
                    final ComplexPanel rightpanel = rightPanel.asPanel();
                    final RichTextArea area = new RichTextArea();
                    area.setHTML(response.getText());
                    area.getElement().setAttribute("name", "richtextarea");
                    final RichTextToolbar toolbar = new RichTextToolbar(
                        area,
                        new IToolbarHandler() {
                            @Override
                            public void save() {
                                wikiPut(href, area.getHTML());
                            }
                            @Override
                            public void quit() {
                                showWiki(href);
                            }
                            @Override
                            public void revert() {
                                wikiGet(
                                    href,
                                    new WickletAjax.WickletAjaxCallback() {
                                        @Override
                                        public void onResponseReceived(final Request request, final Response response) {
                                            if (GwtHttp.Status.isOK(response.getStatusCode())) {
                                                area.setHTML(response.getText());
                                            } else {
                                                showError(response.getStatusText());
                                        }}
                                        @Override
                                        public void onError(final Request request, final Throwable e) {
                                            CConf.debug("ERROR: Revert failed: " + request, e);
                                            showError("ERROR: Revert failed");
                                        }
                                    });
                            }
                            @Override
                            public void editAsText(final String html) {
                                new TextEditDialog(60, 24, href, html) {
                                    @Override
                                    protected void onCancel() {
                                        super.onCancel();
                                        area.setHTML(html);
                                    }
                                    @Override
                                    protected void onOK() {
                                        area.setHTML(getText());
                                        hide();
                                    }
                                }.showAtCenter();
                            }
                        });
                    toolbar.setWidth("100%");
                    final Grid grid = new Grid(2, 1);
                    grid.setWidget(0, 0, toolbar);
                    grid.setWidget(1, 0, area);
                    grid.setWidth("100%");
                    rightpanel.add(grid);
                    area.setSize(
                        "100%",
                        String.valueOf(
                            Window.getClientHeight()
                                - topPanel.asPanel().getOffsetHeight()
                                - toolbar.getOffsetHeight()
                                - 40) + "px");
                }
                @Override
                public void onError(final Request request, final Throwable e) {
                    CConf.debug("ERROR: Get wiki failed: " + request, e);
                    showError("ERROR: Get wiki failed: " + href);
                }
            });
    }

    public void wikiComment(final String href) {
        WickletAjax.ajax(
            GwtUrlUtil.getWickletGwtAjaxBehaviorUrl(),
            "action=" + Services.WikiComment + "&href=" + href,
            new XmlWickletAjaxCallback() {
                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    final String text = response.getText();
                    if (!GwtHttp.Status.isOK(response.getStatusCode())) {
                        showError(text);
                        return;
                    }
                    final Document doc = XMLParser.parse(text);
                    final com.google.gwt.xml.client.Element e
                        = GwtXmlUtil.getFirstChildElement(doc.getDocumentElement());
                    // Expected a single ajax-response/component element.
                    if (e == null
                        || !"component".equals(e.getTagName())
                        || !WID.commentPanel.toString().equals(e.getAttribute("id"))) {
                        showError("ERROR: commentPanel element not found");
                        return;
                    }
                    clearRightPanel();
                    wikiCommentPanel(href, GwtXmlUtil.unescTextUnder(e));
                }
                @Override
                public void onError(final Request request, final Throwable e) {
                    CConf.debug("ERROR: Wiki comment failed: " + request, e);
                    showError("ERROR: Wiki comment failed");
                }
            });
    }

    void wikiCommentPanel(final String href, final String html) {
        new GwtBuilder() {
            void build() {
                final IGwtComplexPanel top = templatepanel(html);
                final ComplexPanel rightpanel = rightPanel.asPanel();
                rightpanel.add(top);
                final DomCache cache = new DomCache(top.asPanel().getElement(), "id", "name");
                final Element form = cache.byId(WID.commentForm);
                final UrlParts formaction = UrlParts.parse(form.getAttribute("action"));
                final IGwtTextBox subject = textbox(cache.by("name", WID.commentSubject).get(0));
                final IGwtTextArea comment = textarea(cache.by("name", WID.commentText).get(0));
                final IGwtTextBox captcha = textbox(cache.by("name", WID.commentCaptchaText).get(0));
                final FocusHandler clearmsg = new FocusHandler() {
                    @Override
                    public void onFocus(final FocusEvent event) {
                        GwtDomUtil.clear(DOM.getElementById(WID.commentFeedback.toString()));
                    }
                };
                top.istyle("margin: 10px").c(
                    form(form).onsubmit(
                        new SubmitHandler() {
                            @Override
                            public void onSubmit(final SubmitEvent event) {
                                event.cancel();
                                WickletAjax.ajax(
                                    WickletGwtSiteUtil.getContextUrl("/wicket/page?" + formaction.getQuery()),
                                    encode(subject.getText(), comment.getText(), captcha.getText()),
                                    new XmlWickletAjaxCallback() {
                                        @Override
                                        public void onResponseReceived(final Request request, final Response response) {
                                            if (!GwtHttp.Status.isOK(response.getStatusCode())) {
                                                showError("ERROR: Invalid response: " + response.getText());
                                                return;
                                            }
                                            final String res = response.getText();
                                            final Document doc = XMLParser.parse(res);
                                            final com.google.gwt.xml.client.Element e = doc.getDocumentElement();
                                            final String tag = e.getNodeName();
                                            if ("success".equals(tag)) {
                                                rightpanel.remove(top);
                                                rightpanel.asWidget().getElement().setInnerHTML(
                                                    GwtXmlUtil.unescXml(GwtXmlUtil.getTextUnder(e)));
                                            } else if ("feedback".equals(tag)) {
                                                final Element o = DOM.getElementById(WID.commentFeedback.toString());
                                                final String text = GwtXmlUtil.unescTextUnder(e);
                                                o.setInnerHTML(text);
                                            } else {
                                                showError("ERROR: Invalid response: " + tag);
                                        }}
                                        @Override
                                        public void onError(final Request request, final Throwable e) {
                                            CConf.debug("ERROR: Wiki comment failed: " + request, e);
                                            showError("ERROR: Wiki comment failed");
                                        }
                                    });
                            }
                        }),
                    subject.onfocus(clearmsg),
                    comment.onfocus(clearmsg),
                    image(cache.byId(WID.commentCaptchaImage)),
                    captcha.onfocus(clearmsg),
                    button(cache.byId(WID.commentSubmit)));
            }
            String encode(final String subject, final String comment, final String captcha) {
                return ""
                    + (WID.commentSubject + "=" + URL.encodeQueryString(subject))
                    + ("&" + WID.commentText + "=" + URL.encodeQueryString(comment))
                    + ("&" + WID.commentCaptchaText + "=" + URL.encodeQueryString(captcha));
            }
        }.build();
    }

    void showNews(final String href) {
        todo();
    }

    void showForum(final String href) {
        todo();
    }

    void showBugs(final String href) {
        todo();
    }

    void showAdmin(final String href) {
        if (Href.AdminUsers.equals(href)) {
            showAdminUsers();
        } else if (Href.AdminWikis.equals(href)) {
            showAdminWikis();
        } else if (Href.AdminForums.equals(href)) {
            showAdminForums();
        } else if (Href.AdminBugs.equals(href)) {
            showAdminBugs();
        } else {
            CConf.debug("ERROR: Unknown admin action: " + href);
    }}

    void showUser(final String href) {
        if (Href.UserPreference.equals(href)) {
            showUserPreference();
        } else {
            CConf.debug("ERROR: Unknown user action: " + href);
    }}

    void showAdminUsers() {
        WickletAjax.ajax(
            WickletGwtSiteUtil.getContextUrl(ContextPath.AdminService),
            "action=" + Shared.Admin.ListUsers,
            new XmlWickletAjaxCallback() {
                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    if (!GwtHttp.Status.isOK(response.getStatusCode())) {
                        showError("ERROR: " + response.getStatusText());
                        return;
                    }
                    final Document doc = XMLParser.parse(response.getText());
                    final List<com.google.gwt.xml.client.Element> users
                        = GwtXmlUtil.getChildElements(doc.getDocumentElement());
                    new GwtBuilder() {
                        void build() {
                            final IGwtGridPanel grid = grid(1 + users.size(), 3).spacing(4);
                            grid.css(ICS.zebraTable).attr("cellspacing", 0);
                            grid.c(label("Username"), label("Roles"), label("Actions"));
                            int row = 0;
                            for ( final com.google.gwt.xml.client.Element user: users) {
                                final String username = user.getAttribute("username");
                                grid.c(
                                    label(GwtXmlUtil.unescXml(username)),
                                    label(GwtXmlUtil.unescXml(user.getAttribute("roles"))),
                                    ipanel(
                                        hyperlink("edit").onclick(
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(final ClickEvent event) {
                                                    new EditUserDialog(
                                                        user,
                                                        new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                showAdminUsers();
                                                            }
                                                        }).showAtCenter();
                                                }
                                            }),
                                        label(" "),
                                        hyperlink("delete").onclick(
                                            new ClickHandler() {
                                                @Override
                                                public void onClick(final ClickEvent event) {
                                                    new ConfirmationDialog(
                                                        "Confirm", "Are you sure to delete user: " + username + " ?") {
                                                        @Override
                                                        protected void onOK() {
                                                            deleteUser(username);
                                                            hide();
                                                        }
                                                    }.showAtCenter();
                                                }
                                            })));
                                grid.rowstyle((row++ % 2 == 0) ? "even" : "odd");
                            }
                            clearRightPanel();
                            rightPanel.c(panel(istyle("margin: 10px"), grid, panel(id(WID.rightPanelFeedback))));
                        }
                    }.build();
                }
                @Override
                public void onError(final Request request, final Throwable e) {
                    CConf.debug("ERROR: List users failed: " + request, e);
                    showError("ERROR: List users failed");
                }
                void deleteUser(final String username) {
                    WickletAjax.ajax(
                        WickletGwtSiteUtil.getContextUrl(ContextPath.AdminService),
                        "action=" + Shared.Admin.DeleteUser + "&username=" + URL.encodeQueryString(username),
                        new XmlWickletAjaxCallback() {
                            @Override
                            public void onResponseReceived(final Request request, final Response response) {
                                if (!GwtHttp.Status.isOK(response.getStatusCode())) {
                                    showError(response.getStatusText());
                                    return;
                                }
                                showAdminUsers();
                            }
                            @Override
                            public void onError(final Request request, final Throwable e) {
                                final String msg = "ERROR: Delete user failed:  user=" + username;
                                CConf.debug(msg + ": " + request, e);
                                feedback(msg);
                            }
                        });
                }
                void feedback(final String msg) {
                    DOM.getElementById(WID.rightPanelFeedback.toString()).setInnerText(msg);
                }
            });
    }

    void showAdminWikis() {
        todo();
    }

    void showAdminForums() {
        todo();
    }

    void showAdminBugs() {
        todo();
    }

    void showUserPreference() {
        todo();
    }

    void showError(final String msg) {
        clearRightPanel();
        new GwtBuilder() {
            void build() {
                rightPanel.a(panel(istyle("color: red;"), label(msg)));
            }
        }.build();
    }

    void clearRightPanel() {
        final ComplexPanel rightpanel = rightPanel.asPanel();
        while (rightpanel.getWidgetCount() > 0) {
            rightpanel.remove(0);
        }
        GwtDomUtil.clear(rightpanel.getElement());
    }

    void todo() {
        clearRightPanel();
        new GwtBuilder() {
            void build() {
                rightPanel.c(panel(istyle("margin: 10px"), label("TODO")));
            }
        }.build();
    }
}
