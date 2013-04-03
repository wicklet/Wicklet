/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.pages.s;

import java.util.Collection;
import java.util.TreeSet;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sf.blacksun.util.net.IHttpConstants.HttpStatus;
import sf.blacksun.util.text.XMLStringWriter;
import sf.wicklet.ext.application.IWickletSupport;
import sf.wicklet.gwt.server.UserInfo;
import sf.wicklet.gwt.server.ajax.IGwtAjaxRequestHandler;
import sf.wicklet.gwt.server.ajax.IGwtAjaxTarget;
import sf.wicklet.gwt.server.ajax.IGwtAjaxXmlTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxWickletTarget;
import sf.wicklet.gwt.server.ajax.impl.GwtAjaxXmlTarget;
import sf.wicklet.gwt.site.server.MyAuthenticatedWebSession;
import sf.wicklet.gwt.site.server.WickletGwtSiteApplication;
import sf.wicklet.gwt.site.shared.Shared;

/** Provide ajax service at /s/service/ that require no session/conversation state. */
@RequireHttps
@AuthorizeInstantiation("admin")
public class AdminService extends Page {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    public static final String PATH = "/s/adminservice";
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_ROLES = "roles";

    IWickletSupport support;

    public AdminService(final PageParameters params) {
        final WickletGwtSiteApplication app = (WickletGwtSiteApplication)getApplication();
        support = app.getWickletSupport();
        final IRequestParameters p = getRequest().getRequestParameters();
        final String action = p.getParameterValue(PARAM_ACTION).toString();
        IGwtAjaxTarget target;
        if (Shared.Admin.ListUsers.equalsIgnoreCase(action)) {
            target = listusers(new GwtAjaxXmlTarget(this), p);
        } else if (Shared.Admin.DeleteUser.equalsIgnoreCase(action)) {
            target = deleteuser(new GwtAjaxXmlTarget(this), p);
        } else if (Shared.Admin.EditUser.equalsIgnoreCase(action)) {
            target = edituser(new GwtAjaxXmlTarget(this), p);
        } else {
            target = new GwtAjaxWickletTarget(this);
            target.error(HttpStatus.ServiceUnavailable, "ERROR: Invalid action: " + action, null);
        }
        final IGwtAjaxRequestHandler handler = app.getGwtAjaxSupport().createGwtAjaxRequestHandler(this, target);
        RequestCycle.get().scheduleRequestHandlerAfterCurrent(handler);
    }

    private IGwtAjaxXmlTarget listusers(final IGwtAjaxXmlTarget target, final IRequestParameters p) {
        final MyAuthenticatedWebSession session = MyAuthenticatedWebSession.get();
        if (session.isAdmin()) {
            final Collection<UserInfo> infos = new TreeSet<UserInfo>(UserInfo.nameSorter);
            infos.addAll(session.getUserInfos());
            target.write(
                new XMLStringWriter() {
                    String build() {
                        xmlHeader().start("users");
                        for ( final UserInfo info: infos) {
                            element(
                                "user", "", "username", escXml(info.getUsername()), "roles", escXml(info.getRoles()));
                        }
                        end();
                        return toString();
                    }
                }.build());
        } else {
            target.error(HttpStatus.Forbidden, "ERROR: You are not authorized for this action", null);
        }
        return target;
    }

    private IGwtAjaxXmlTarget deleteuser(final IGwtAjaxXmlTarget target, final IRequestParameters p) {
        final MyAuthenticatedWebSession session = MyAuthenticatedWebSession.get();
        if (session.isAdmin()) {
            final String user = p.getParameterValue(PARAM_USERNAME).toString();
            final String result = session.deleteUser(user);
            if (result != null) {
                final String msg = "ERROR: Failed to delete user: " + user;
                logger.error(msg + ": " + result);
                target.error(HttpStatus.BadRequest, msg, null);
            } else {
                target.write(new XMLStringWriter().xmlHeader().empty("success").toString());
        }} else {
            target.error(HttpStatus.Forbidden, "ERROR: You are not authorized for this action", null);
        }
        return target;
    }

    private IGwtAjaxXmlTarget edituser(final IGwtAjaxXmlTarget target, final IRequestParameters p) {
        final MyAuthenticatedWebSession session = MyAuthenticatedWebSession.get();
        if (session.isAdmin()) {
            final String user = p.getParameterValue(PARAM_USERNAME).toString();
            final String roles = p.getParameterValue(PARAM_ROLES).toString();
            final String result = session.editUser(new UserInfo(user, roles));
            if (result != null) {
                final String msg = "ERROR: Failed to edit user: " + user;
                logger.error(msg + ": " + result);
                target.error(HttpStatus.BadRequest, msg, null);
            } else {
                target.write(new XMLStringWriter().xmlHeader().empty("success").toString());
        }} else {
            target.error(HttpStatus.Forbidden, "ERROR: You are not authorized for this action", null);
        }
        return target;
    }
}
