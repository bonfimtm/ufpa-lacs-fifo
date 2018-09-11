/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.fifo.controller;

import br.ufpa.fifo.model.entities.Measurement;
import br.ufpa.fifo.model.entities.Waiting;
import br.ufpa.fifo.model.jpa.MeasurementFacade;
import br.ufpa.fifo.model.jpa.WaitingFacade;
import java.util.List;

/**
 *
 * @author thiago
 */
@javax.faces.bean.ManagedBean
@javax.faces.bean.ApplicationScoped
public class IndexController implements java.io.Serializable {

    private boolean showSchoolarItens;

    public IndexController() {
        showSchoolarItens = false;
    }

    public String getAvg() {
        Double avg = 0.0;
        int counter = 0;
        Measurement m = new MeasurementFacade().findShowingMeasurement();
        if (m != null) {
            List<Waiting> lw = new WaitingFacade().findByMeasurement(m.getId());
            for (Waiting w : lw) {
                if (w.getConclusion() != null && !w.getCheater()) {
                    avg += (w.getConclusion().getTime() - w.getAdmission().getTime()) / 60000.0;
                    counter++;
                }
            }
            if (counter != 0) {
                avg /= counter;
            }
            return avg != 0.0 ? String.format("%.0f minutos e %.0f segundos", avg, ((avg * 60.0) % 60.0)) : "";
        } else {
            return "";
        }
    }

    public String getCheaters() {
        Integer cheaters = 0;
        Measurement m = new MeasurementFacade().findShowingMeasurement();
        if (m != null) {
            List<Waiting> lw = new WaitingFacade().findByMeasurement(m.getId());
            if (lw == null || lw.isEmpty()) {
                return "";
            }
            for (Waiting w : lw) {
                if (w.getCheater() != null && w.getCheater()) {
                    cheaters++;
                }
            }
            return cheaters.toString();
        } else {
            return "";
        }
    }

    public String getSchoolarItens() {
        Integer schoolarItens = 0;
        Measurement m = new MeasurementFacade().findShowingMeasurement();
        if (m != null) {
            List<Waiting> lw = new WaitingFacade().findByMeasurement(m.getId());
            if (lw == null || lw.isEmpty()) {
                return "";
            }
            for (Waiting w : lw) {
                if (w.getCheater() != null && !w.getCheater()) {
                    schoolarItens++;
                }
            }
            return schoolarItens.toString();
        } else {
            return "";
        }
    }

    public boolean isShowSchoolarItens() {
        return showSchoolarItens;
    }

    public void setShowSchoolarItens(boolean showSchoolarItens) {
        this.showSchoolarItens = showSchoolarItens;
    }

}
