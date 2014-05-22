/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.jsfbean;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PrePersist;
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
public class UserBean implements Serializable {

    @Inject
    private UserAppFacade userFacade;
    @Inject
    private SnapshotFacade snapFacade;
    @Inject
    private MyWhiteboard myWhiteboard;
    private String username;
    private UserApp user;
    private Date actualdate;

    @PrePersist
    public void onCreate() {
        GregorianCalendar gc = new GregorianCalendar();
        this.setActualdate(gc.getTime());
    }

    public UserBean() {
        this.user = new UserApp();
    }

    public String loggedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        this.username = context.getExternalContext().getRemoteUser();
        return username;
    }

    public void saveWhiteboard() {
        Snapshot snap = new Snapshot();

        //procura na entidade UserApp qual o user logado;:
        List<UserApp> listUser = userFacade.findAll();
        for (UserApp u : listUser) {
            if (u.getName().equals(username)) {
                this.user = u;
            }
        }

        //vai buscar o array de binarios actual:
        byte[] activeData = myWhiteboard.getDataToSave();

        //adiciona o snapshot à entidade:
        snap.setUserApp(user);
        snap.setImageDate(actualdate);
        snap.setImage(activeData);
        snapFacade.addSnapshot(snap);
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

    public MyWhiteboard getMyWhiteboard() {
        return myWhiteboard;
    }

    public void setMyWhiteboard(MyWhiteboard myWhiteboard) {
        this.myWhiteboard = myWhiteboard;
    }

    public Date getActualdate() {
        return actualdate;
    }

    public void setActualdate(Date actualdate) {
        this.actualdate = actualdate;
    }

}
