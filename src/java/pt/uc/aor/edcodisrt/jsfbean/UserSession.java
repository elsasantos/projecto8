/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.jsfbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIForm;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.aor.edcodisrt.entities.Snapshot;
import pt.uc.aor.edcodisrt.facades.SnapshotFacade;

/**
 *
 * @author Elsa
 */
@Named
@SessionScoped
public class UserSession implements Serializable {

    @Inject
    private SnapshotFacade snapshotFacade;
    private Snapshot snapshotSelected;
    private UIForm confirmShowImage;
    private boolean renderedImage;

    public UserSession() {
    }

    @PostConstruct
    public void init() {
        this.renderedImage = false;
    }

//    public StreamedContent showImage() throws IOException, ArrayIndexOutOfBoundsException, NullPointerException {
//        System.out.println("Entro no método que mostra a imagem");
//        System.out.println("Snap selecionado: " + snapshotSelected.getId());
//        byte[] imageData = snapshotSelected.getImage();
//        BufferedImage img = new BufferedImage(500, 300, BufferedImage.TYPE_4BYTE_ABGR);
//        img.getRaster().setDataElements(0, 0, 500, 300, imageData);
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(img, "png", os);
//        InputStream is = new ByteArrayInputStream(os.toByteArray());
//        StreamedContent image = new DefaultStreamedContent(is, "image/png");
//        return image;
//    }
    public void prepareImage() {
        confirmShowImage.setRendered(true);
    }

    public Snapshot snapSelect(Snapshot snap) {
        this.snapshotSelected = snap;
        return snapshotSelected;
    }

//    public String prepare() {
//        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
//        flash.put("item", snapshotSelected);
//        return "/user/snapshot?faces-redirect=true";
//    }
    public SnapshotFacade getSnapshotFacade() {
        return snapshotFacade;
    }

    public void setSnapshotFacade(SnapshotFacade snapshotFacade) {
        this.snapshotFacade = snapshotFacade;
    }

    public Snapshot getSnapshotSelected() {
        return snapshotSelected;
    }

    public void setSnapshotSelected(Snapshot snapshotSelected) {
        if (snapshotSelected != null) {
            this.renderedImage = true;
            System.out.println("FOI RENDERIZADO");
        }
        this.snapshotSelected = snapshotSelected;
    }

    public UIForm getConfirmShowImage() {
        return confirmShowImage;
    }

    public void setConfirmShowImage(UIForm confirmShowImage) {
        this.confirmShowImage = confirmShowImage;
    }

    public boolean isRenderedImage() {
        return renderedImage;
    }

    public void setRenderedImage(boolean renderedImage) {
        this.renderedImage = renderedImage;
    }

}
