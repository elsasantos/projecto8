/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.jsfbean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.aor.edcodisrt.entities.UserApp;
import pt.uc.aor.edcodisrt.facades.UserAppFacade;

/**
 *
 * @author Elsa
 */
@Named
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private UserAppFacade userfacade;
    private String username;
    private UserApp user;

    public UserBean() {
    }

    public UserAppFacade getUserfacade() {
        return userfacade;
    }

    public void setUserfacade(UserAppFacade userfacade) {
        this.userfacade = userfacade;
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

}
