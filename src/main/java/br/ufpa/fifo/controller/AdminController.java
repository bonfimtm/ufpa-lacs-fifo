package br.ufpa.fifo.controller;

import br.ufpa.fifo.controller.util.JsfUtil;
import br.ufpa.fifo.controller.util.UtilBean;
import br.ufpa.fifo.model.entities.Measurement;
import br.ufpa.fifo.model.entities.Waiting;
import br.ufpa.fifo.model.jpa.MeasurementFacade;
import br.ufpa.fifo.model.jpa.WaitingFacade;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.System.out;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author thiago
 */
@javax.faces.bean.ManagedBean
@javax.faces.bean.SessionScoped
public class AdminController implements java.io.Serializable {

    private String measurementName;
    private Measurement measurementToDelete;

    public String createMeasurement() {
        //Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Belem"), new Locale("pt", "BR"));
        Measurement m = new Measurement(null, new Date(), false, 0);
        if (measurementName != null && !measurementName.equals("")) {
            m.setName(measurementName);
        }
        new MeasurementFacade().create(m);
        measurementName = null;
        return "/admin?faces-redirect=true";
    }

    public List<Measurement> getMeasurements() {
        return new MeasurementFacade().findAll();
    }

    public void showToClients(Measurement measurement) {
        MeasurementFacade mf = new MeasurementFacade();
        List<Measurement> lm = mf.findAll();
        for (Measurement m : lm) {
            if (m.equals(measurement)) {
                if (!m.getShowing()) {
                    m.setShowing(true);
                } else {
                    m.setShowing(false);
                }
            } else {
                m.setShowing(false);
            }
            mf.edit(m);
        }
    }

    public void mesurementReport(Measurement measurement) {
        List<Waiting> l = new WaitingFacade().findByMeasurement(measurement.getId());
        String fileContent = "Id;Codigo;Adimisao;Conclusao;TEC (min);TEF (min);Trapaceiro;Tempo de espera (min);Medicao;Nome da Medicao;Data;TMEF;Itens Escolares;Sem Ficha\n";
        long previousAdmission = 0;
        for (Waiting w : l) {
            if (w.getConclusion() != null && w.getCheater() != null) {
                Measurement m = w.getMeasurementId();
                Double watingTime = (w.getConclusion().getTime() - w.getAdmission().getTime()) / 60000.0;
                fileContent += String.format("%d;%d;%s;%s;%.2f;%.2f;%s;%.2f;%d;%s;%s;%s;%s;%d\n",
                        w.getId(), w.getCode(), UtilBean.maskTime(w.getAdmission()), UtilBean.maskTime(w.getConclusion()),
                        previousAdmission == 0 ? 0.0 : (w.getAdmission().getTime() - previousAdmission) / 60000.0,
                        (w.getConclusion().getTime() - w.getAdmission().getTime()) / 60000.0,
                        UtilBean.maskBoolean(w.getCheater()), watingTime,
                        m.getId(), m.getName(), UtilBean.maskDateAndTime(m.getDateAndTime()),
                        getAvg(m), getSchoolarItens(m), m.getExtraCheaters());
                previousAdmission = w.getAdmission().getTime();
            } else {
                fileContent += String.format("%d;%d;%s;;;\n", w.getId(), w.getCode(), UtilBean.maskTime(w.getAdmission()));
            }
        }
        ServletOutputStream servletOutputStream = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("text/csv");
            response.setHeader("Content-disposition", "attachment;filename=data.csv");
            servletOutputStream = response.getOutputStream();
            PrintStream printStream = new PrintStream(servletOutputStream);
            printStream.print(fileContent);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger("index").log(Level.SEVERE, null, ex);
            out.print(ex.getMessage());
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger("index").log(Level.SEVERE, null, ex);
                out.print(ex.getMessage());
            }
        }
    }

    private String getAvg(Measurement m) {
        Double avg = 0.0;
        int counter = 0;
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
        return avg != 0.0 ? String.format("%.2f", avg) : "";
    }

    private String getSchoolarItens(Measurement m) {
        Integer schoolarItens = 0;
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

    public void prepareDeleteMesurement(Measurement measurement) {
        measurementToDelete = measurement;
    }

    public void deleteMesurement() {
        new MeasurementFacade().remove(measurementToDelete);
        JsfUtil.addSuccessMessage("Medição excluída com sucesso.");
    }

    public void cancelDeleteMesurement() {
        measurementToDelete = null;
    }

    public String getMeasurementName() {
        return measurementName;
    }

    public void setMeasurementName(String measurementName) {
        this.measurementName = measurementName;
    }

}
