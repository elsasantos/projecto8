package pt.uc.aor.edcodisrt.jsfbean;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import pt.uc.aor.edcodisrt.entities.Snapshot;
import pt.uc.aor.edcodisrt.facades.SnapshotFacade;

/**
 * This JSF/CDI Managed Bean provides a way for users to log out of the
 * application.
 */
@Named
@RequestScoped
public class UserController {

    @Inject
    private UserSession userlogado;
    @Inject
    private SnapshotFacade snapshotFacade;

    private static Logger log = Logger.getLogger(UserController.class.getName());

    public UserController() {
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        UserController.log = log;
    }

    public String logout() {
        System.out.println("entrou");
        String destination = "/faces/index?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            HttpSession session = request.getSession();
            session.invalidate();

            request.logout();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            destination = "/loginerror?faces-redirect=true";
        }
        return destination;
    }

    public String loggedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        String name = context.getExternalContext().getRemoteUser();
        System.out.println("user logado: " + name);
        return name;
    }

    public List<Snapshot> listSnapshotUser() {
        return snapshotFacade.userSnapshot(userlogado.getUser());
    }

    public UserSession getUserlogado() {
        return userlogado;
    }

    public void setUserlogado(UserSession userlogado) {
        this.userlogado = userlogado;
    }

}
