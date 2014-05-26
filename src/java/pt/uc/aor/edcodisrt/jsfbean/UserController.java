package pt.uc.aor.edcodisrt.jsfbean;

import java.util.Date;
import java.util.GregorianCalendar;
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
import pt.uc.aor.edcodisrt.endpoint.MyWhiteboard;
import pt.uc.aor.edcodisrt.entities.Snapshot;
import pt.uc.aor.edcodisrt.entities.UserApp;
import pt.uc.aor.edcodisrt.facades.SnapshotFacade;
import pt.uc.aor.edcodisrt.facades.UserAppFacade;

/**
 * This JSF/CDI Managed Bean provides a way for users to log out of the
 * application.
 */
@Named
@RequestScoped
public class UserController {

    @Inject
    private UserAppFacade userFacade;
    @Inject
    private SnapshotFacade snapshotFacade;
    private String name;
    private UserApp user;
    private Date actualdate;

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
        name = context.getExternalContext().getRemoteUser();
        System.out.println("user logado: " + name);
        return name;
    }

    public void saveWhiteboard() {
        Snapshot snap = new Snapshot();

        //vai buscar o array de binarios actual:
        byte[] activeData = MyWhiteboard.getDataActive().array();

        //Cria a data do sistema:
        GregorianCalendar gc = new GregorianCalendar();
        this.setActualdate(gc.getTime());

        //adiciona o snapshot à entidade:
        snap.setUserApp(userFacade.findbyName(loggedUser()));
        snap.setImageDate(actualdate);
        snap.setImage(activeData);

        snapshotFacade.create(snap);
    }

    public UserApp userlogado() {
        return this.user = userFacade.findbyName(loggedUser());
    }

    public List<Snapshot> listSnapshotUser() {
        return snapshotFacade.userSnapshot(userFacade.findbyName(loggedUser()));
    }

    public String deleteSnapshot(Snapshot snap) {
        snapshotFacade.remove(snap);
        return "historic";
    }

    public SnapshotFacade getSnapshotFacade() {
        return snapshotFacade;
    }

    public void setSnapshotFacade(SnapshotFacade snapshotFacade) {
        this.snapshotFacade = snapshotFacade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserApp getUser() {
        return user;
    }

    public void setUser(UserApp user) {
        this.user = user;
    }

    public Date getActualdate() {
        return actualdate;
    }

    public void setActualdate(Date actualdate) {
        this.actualdate = actualdate;
    }

}
