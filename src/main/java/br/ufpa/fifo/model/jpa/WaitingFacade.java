/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.fifo.model.jpa;

import br.ufpa.fifo.model.entities.Waiting;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author thiago
 */
public class WaitingFacade extends AbstractFacade<Waiting> {

    public WaitingFacade() {
        super(Waiting.class);
    }

    public List<Waiting> findByMeasurement(long measurementId) {
        // Using named queries
        // Use entity manager to retrieve named query
        TypedQuery<Waiting> query = getEntityManager().createNamedQuery("Waiting.findByMeasurement", Waiting.class);
        // Set dynamic data for query
        query.setParameter("measurementId", measurementId);
        // Execute query and get results
        return query.getResultList();
    }
    
    public List<Waiting> findByCodeAndMeasurement(int code, long measurementId) {
        // Using named queries
        // Use entity manager to retrieve named query
        TypedQuery<Waiting> query = getEntityManager().createNamedQuery("Waiting.findByCodeAndMeasurement", Waiting.class);
        // Set dynamic data for query
        query.setParameter("code", code);
        query.setParameter("measurementId", measurementId);
        // Execute query and get results
        return query.getResultList();
    }
}
