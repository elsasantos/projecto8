/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.jsfbean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.text.html.ImageView;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pt.uc.aor.edcodisrt.entities.Snapshot;
import pt.uc.aor.edcodisrt.facades.SnapshotFacade;

/**
 *
 * @author Elsa
 */
@Named
@ApplicationScoped
public class showImage {

    @Inject
    private SnapshotFacade snapFacade;

    public StreamedContent getImage() throws IOException, ArrayIndexOutOfBoundsException, NullPointerException {
        System.out.println("Entra no método da imagem");
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            System.out.println("ENTROU NO IF");
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            Snapshot wb = snapFacade.find(Long.valueOf(id));
            System.out.println("Id da imagem" + wb.getId());

            byte[] blobAsBytes = wb.getImage();
            BufferedImage bi = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
            bi.getRaster().setDataElements(0, 0, 300, 300, blobAsBytes);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ImageIO.write(bi, "png", os);
            } catch (IOException ex) {
                Logger.getLogger(ImageView.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("CHEGA AQUI---->" + os);
            return new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
        }
    }

}
