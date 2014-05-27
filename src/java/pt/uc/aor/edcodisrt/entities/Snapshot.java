/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author Guilherme Pereira
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Snapshot.findByUser", query = "SELECT s FROM Snapshot s WHERE s.userApp = :userApp"),
    @NamedQuery(name = "Snapshot.findById", query = "SELECT s.image FROM Snapshot s WHERE s.id = :id"),})
public class Snapshot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date imageDate;

    @ManyToOne
    private UserApp userApp;

    @Lob
    private byte[] image;

    public Snapshot() {
    }

    public Snapshot(Date imageDate, UserApp userApp, byte[] image) {
        this.imageDate = imageDate;
        this.userApp = userApp;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getImageDate() {
        return imageDate;
    }

    public void setImageDate(Date imageDate) {
        this.imageDate = imageDate;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Snapshot)) {
            return false;
        }
        Snapshot other = (Snapshot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pt.uc.aor.edcodisrt.entities.Snapshot[ id=" + id + " ]";
    }

}
