package pt.uc.aor.edcodisrt.jsfbean;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This JSF/CDI Managed Bean provides a way for users to log out of the
 * application.
 */
@Named
@RequestScoped
public class LogoutBean {

 
    private static Logger log = Logger.getLogger(LogoutBean.class.getName());

    public LogoutBean() {
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        LogoutBean.log = log;
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
        return context.getExternalContext().getRemoteUser();
    }

}
