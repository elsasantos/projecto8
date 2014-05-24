/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.jsfbean;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.aor.edcodisrt.endpoint.MyWhiteboard;
import pt.uc.aor.edcodisrt.entities.Snapshot;
import pt.uc.aor.edcodisrt.entities.UserApp;
import pt.uc.aor.edcodisrt.facades.SnapshotFacade;
import pt.uc.aor.edcodisrt.facades.UserAppFacade;

/**
 *
 * @author Elsa
 */
@Named
@SessionScoped
public class UserSession implements Serializable {

    @Inject
    private UserAppFacade userFacade;
    @Inject
    private SnapshotFacade snapFacade;
    private String username;
    private UserApp user;
    private Date actualdate;
    private static Logger log = Logger.getLogger(UserSession.class.getName());

    public UserSession() {
        this.user = new UserApp();
    }

    public String loggedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        this.username = context.getExternalContext().getRemoteUser();
        return username;
    }

    public void saveWhiteboard() {
        Snapshot snap = new Snapshot();

        //vai buscar o array de binarios actual:
        byte[] activeData = MyWhiteboard.getDataActive().array();

        //Cria a data do sistema:
        GregorianCalendar gc = new GregorianCalendar();
        this.setActualdate(gc.getTime());

        //adiciona o snapshot à entidade:
        snap.setUserApp(userFacade.findbyName(username));
        snap.setImageDate(actualdate);
        snap.setImage(activeData);

        snapFacade.create(snap);
    }

    public UserAppFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserAppFacade userFacade) {
        this.userFacade = userFacade;
    }

    public UserApp getUser() {
        return user;
    }

    public void setUser(UserApp user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SnapshotFacade getSnapFacade() {
        return snapFacade;
    }

    public void setSnapFacade(SnapshotFacade snapFacade) {
        this.snapFacade = snapFacade;
    }

    public Date getActualdate() {
        return actualdate;
    }

    public void setActualdate(Date actualdate) {
        this.actualdate = actualdate;
    }

}
