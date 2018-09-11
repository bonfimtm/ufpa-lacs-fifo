package br.ufpa.fifo.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "measurement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Measurement.findAll", query = "SELECT m FROM Measurement m"),
    @NamedQuery(name = "Measurement.findById", query = "SELECT m FROM Measurement m WHERE m.id = :id"),
    @NamedQuery(name = "Measurement.findByName", query = "SELECT m FROM Measurement m WHERE m.name = :name"),
    @NamedQuery(name = "Measurement.findByShowing", query = "SELECT m FROM Measurement m WHERE m.showing = :showing"),
    @NamedQuery(name = "Measurement.findByDateAndTime", query = "SELECT m FROM Measurement m WHERE m.dateAndTime = :dateAndTime")})
public class Measurement implements Serializable {

    @Basic(optional = false)
    @Column(name = "extra_cheaters")
    private int extraCheaters;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "date_and_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAndTime;
    @Basic(optional = false)
    @Column(name = "showing")
    private boolean showing;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurementId")
    private List<Waiting> waitingList;

    public Measurement() {
    }

    public Measurement(Long id) {
        this.id = id;
    }

    public Measurement(Long id, Date dateAndTime, boolean showing, int extraCheaters) {
        this.id = id;
        this.dateAndTime = dateAndTime;
        this.showing = showing;
        this.extraCheaters = extraCheaters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public boolean getShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    @XmlTransient
    public List<Waiting> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(List<Waiting> waitingList) {
        this.waitingList = waitingList;
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
        if (!(object instanceof Measurement)) {
            return false;
        }
        Measurement other = (Measurement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufpa.fifo.model.entities.Measurement[ id=" + id + " ]";
    }

    public int getExtraCheaters() {
        return extraCheaters;
    }

    public void setExtraCheaters(int extraCheaters) {
        this.extraCheaters = extraCheaters;
    }

}
