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
public class HeadController implements java.io.Serializable {

    private Long headMeasurementId;
    private Measurement headMeasurement;
    private Integer headWaitingCode;
    private Integer lastHeadWaitingCode;

    public HeadController() {
        headWaitingCode = lastHeadWaitingCode = null;
    }

    public void prepareHead() {
        headMeasurement = new MeasurementFacade().find(headMeasurementId);
    }

    public String manageHead(Long headMeasurementId) {
        return "/faces/head.xhtml?faces-redirect=true&measurementId=" + headMeasurementId;
    }

    public void pop() {
        WaitingFacade wf = new WaitingFacade();
        // Fetch waiter by headWaitingCode
        List<Waiting> l = wf.findByCodeAndMeasurement(headWaitingCode, headMeasurement.getId());
        if (l != null && !l.isEmpty()) {
            // This waiter has entered in the line
            Waiting w = l.get(0);
            if (w.getConclusion() != null) {
                // This waiter has already exited in the line
                JsfUtil.addErrorMessage("Cliente " + headWaitingCode + " já saiu da fila.");
            } else {
                // Verify cheating
                if (lastHeadWaitingCode != null) {
                    if (headWaitingCode - lastHeadWaitingCode > 5) {
                        w.setCheater(true);
                    } else {
                        w.setCheater(false);
                    }
                    /*if (headWaitingCode - lastHeadWaitingCode > 1) {
                     // Verifyies if previous waiter is a cheater
                     List<Waiting> pl = wf.findByCodeAndMeasurement(headWaitingCode - 1, headMeasurement.getId());
                     if (pl != null && !pl.isEmpty()) {
                     Waiting pw = pl.get(0);
                     Boolean pwc = pw.getCheater();
                     if (pwc != null) {
                     if (pwc) {
                     w.setCheater(false);
                     } else {
                     w.setCheater(true);
                     }
                     } else {
                     w.setCheater(true);
                     }
                     } else {
                     w.setCheater(true);
                     }
                     } else {
                     w.setCheater(false);
                     }*/
                } else {
                    // There is no previous waiter
                    w.setCheater(false);
                }
                // Set waiter time
                w.setConclusion(new Date());
                // Update database
                wf.edit(w);
                // Calculates waiting time
                Double watingTime = null;
                if (w.getConclusion() != null) {
                    watingTime = (w.getConclusion().getTime() - w.getAdmission().getTime()) / 60000.0;
                }
                // Messages
                JsfUtil.addSuccessMessage("Cliente " + headWaitingCode + " Saiu.");
                JsfUtil.addSuccessMessage(String.format("Tempo de espera:  %.2f minutos.", watingTime));
                if (w.getCheater()) {
                    JsfUtil.addWarningMessage("Este é um trapaceiro!");
                } else {
                    // Updates last waiter
                    lastHeadWaitingCode = headWaitingCode;
                }
            }
        } else {
            // This waiter has not entered in the line
            JsfUtil.addErrorMessage("Cliente " + headWaitingCode + " não entrou na fila.");
        }
        // Updates last waiter
        // lastHeadWaitingCode = headWaitingCode;
        headWaitingCode = null;
    }

    public String getNextOnHead() {
        return String.format("%d", lastHeadWaitingCode + 1);
    }

    public void popNext() {
        headWaitingCode = ++lastHeadWaitingCode;
        pop();
    }

    public void popUnknownCheaterCheater() {
        int xch = headMeasurement.getExtraCheaters();
        headMeasurement.setExtraCheaters(xch + 1);
        new MeasurementFacade().edit(headMeasurement);
        JsfUtil.addSuccessMessage("Cliente sem ficha saiu.");
        JsfUtil.addSuccessMessage((xch + 1) + " clientes sem ficha sairam até o momento.");
    }

    public Measurement getHeadMeasurement() {
        return headMeasurement;
    }

    public void setHeadMeasurement(Measurement headMeasurement) {
        this.headMeasurement = headMeasurement;
    }

    public Integer getHeadWaitingCode() {
        return headWaitingCode;
    }

    public void setHeadWaitingCode(Integer headWaitingCode) {
        this.headWaitingCode = headWaitingCode;
    }

    public Integer getLastHeadWaitingCode() {
        return lastHeadWaitingCode;
    }

    public void setLastHeadWaitingCode(Integer lastHeadWaitingCode) {
        this.lastHeadWaitingCode = lastHeadWaitingCode;
    }

    public Long getHeadMeasurementId() {
        return headMeasurementId;
    }

    public void setHeadMeasurementId(Long headMeasurementId) {
        this.headMeasurementId = headMeasurementId;
    }

}
