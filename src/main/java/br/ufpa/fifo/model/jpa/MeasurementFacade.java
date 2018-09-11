/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.fifo.model.jpa;

import br.ufpa.fifo.model.entities.Measurement;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author thiago
 */
public class MeasurementFacade extends AbstractFacade<Measurement> {

    public MeasurementFacade() {
        super(Measurement.class);
    }

    public Measurement findShowingMeasurement() {
        // Using named queries
        // Use entity manager to retrieve named query
        TypedQuery<Measurement> query = getEntityManager().createNamedQuery("Measurement.findByShowing", Measurement.class);
        // Set dynamic data for query
        query.setParameter("showing", true);
        // Execute query and get results
        List<Measurement> lm = query.getResultList();
        return lm != null && !lm.isEmpty() ? lm.get(0) : null;
    }

}
