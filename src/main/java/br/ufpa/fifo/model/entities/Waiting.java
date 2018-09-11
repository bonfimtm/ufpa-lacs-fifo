package br.ufpa.fifo.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "waiting")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Waiting.findAll", query = "SELECT w FROM Waiting w"),
    @NamedQuery(name = "Waiting.findById", query = "SELECT w FROM Waiting w WHERE w.id = :id"),
    @NamedQuery(name = "Waiting.findByAdmission", query = "SELECT w FROM Waiting w WHERE w.admission = :admission"),
    @NamedQuery(name = "Waiting.findByConclusion", query = "SELECT w FROM Waiting w WHERE w.conclusion = :conclusion"),
    @NamedQuery(name = "Waiting.findByCode", query = "SELECT w FROM Waiting w WHERE w.code = :code"),
    @NamedQuery(name = "Waiting.findByMeasurement", query = "SELECT w FROM Waiting w,Measurement m WHERE w.measurementId.id = m.id"
            + " and m.id = :measurementId"),
    @NamedQuery(name = "Waiting.findByCodeAndMeasurement", query = "SELECT w FROM Waiting w,Measurement m WHERE w.code = :code"
            + " and w.measurementId.id = m.id and m.id = :measurementId")})
public class Waiting implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "admission")
    @Temporal(TemporalType.TIMESTAMP)
    private Date admission;
    @Column(name = "conclusion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date conclusion;
    @Basic(optional = false)
    @Column(name = "code")
    private int code;
    @JoinColumn(name = "measurement_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Measurement measurementId;
    @Column(name = "cheater")
    private Boolean cheater;

    public Waiting() {
    }

    public Waiting(Long id) {
        this.id = id;
    }

    public Waiting(Long id, Date admission, Date conclusion, int code) {
        this.id = id;
        this.admission = admission;
        this.conclusion = conclusion;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAdmission() {
        return admission;
    }

    public void setAdmission(Date admission) {
        this.admission = admission;
    }

    public Date getConclusion() {
        return conclusion;
    }

    public void setConclusion(Date conclusion) {
        this.conclusion = conclusion;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Measurement getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Measurement measurementId) {
        this.measurementId = measurementId;
    }

    public Boolean getCheater() {
        return cheater;
    }

    public void setCheater(Boolean cheater) {
        this.cheater = cheater;
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
        if (!(object instanceof Waiting)) {
            return false;
        }
        Waiting other = (Waiting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.ufpa.fifo.model.entities.Waiting[ id=" + id + " ]";
    }

}
