package br.ufpa.fifo.controller;

import br.ufpa.fifo.controller.util.JsfUtil;
import br.ufpa.fifo.model.entities.Measurement;
import br.ufpa.fifo.model.entities.Waiting;
import br.ufpa.fifo.model.jpa.MeasurementFacade;
import br.ufpa.fifo.model.jpa.WaitingFacade;
import java.util.Date;
import java.util.List;

/**
 *
 * @author thiago
 */
@javax.faces.bean.ManagedBean
@javax.faces.bean.ViewScoped
public class TailController implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2901340664577524927L;
	private Long tailMeasurementId;
    private Measurement tailMeasurement;
    private Integer tailWaitingCode;
    private Integer lastTailWaitingCode;

    public TailController() {
        tailWaitingCode = lastTailWaitingCode = null;
    }

    public void prepareTail() {
        tailMeasurement = new MeasurementFacade().find(tailMeasurementId);
    }

    public String manageTail(Long tailMeasurementId) {
        return "/faces/tail.xhtml?faces-redirect=true&measurementId=" + tailMeasurementId;
    }

    public void put() {
        WaitingFacade wf = new WaitingFacade();
        List<Waiting> l = wf.findByCodeAndMeasurement(tailWaitingCode, tailMeasurement.getId());
        if (l != null && !l.isEmpty()) {
            JsfUtil.addErrorMessage("Cliente " + tailWaitingCode + " já está na fila.");
        } else {
            Waiting w = new Waiting();
            w.setCode(tailWaitingCode);
            w.setAdmission(new Date());
            w.setMeasurementId(tailMeasurement);
            wf.create(w);
            JsfUtil.addSuccessMessage("Cliente " + tailWaitingCode + " entrou.");
            lastTailWaitingCode = tailWaitingCode;
        }
        tailWaitingCode = null;
    }

    public String getNextOnTail() {
        return String.format("%d", lastTailWaitingCode + 1);
    }

    public void putNext() {
        tailWaitingCode = ++lastTailWaitingCode;
        put();
    }

    public Measurement getTailMeasurement() {
        return tailMeasurement;
    }

    public void setTailMeasurement(Measurement tailMeasurement) {
        this.tailMeasurement = tailMeasurement;
    }

    public Integer getTailWaitingCode() {
        return tailWaitingCode;
    }

    public void setTailWaitingCode(Integer tailWaitingCode) {
        this.tailWaitingCode = tailWaitingCode;
    }

    public Integer getLastTailWaitingCode() {
        return lastTailWaitingCode;
    }

    public void setLastTailWaitingCode(Integer lastTailWaitingCode) {
        this.lastTailWaitingCode = lastTailWaitingCode;
    }

    public Long getTailMeasurementId() {
        return tailMeasurementId;
    }

    public void setTailMeasurementId(Long tailMeasurementId) {
        this.tailMeasurementId = tailMeasurementId;
    }

}
