package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.xml.sax.SAXException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 07-Dec-2009
 */
public class TranslatedOWLOntologyChangeException extends SAXException {

    private static final long serialVersionUID = 30406L;

    /**
     * @param e
     *        e
     */
    public TranslatedOWLOntologyChangeException(OWLOntologyChangeException e) {
        super(e);
    }

    @Override
    public OWLOntologyChangeException getCause() {
        return (OWLOntologyChangeException) super.getCause();
    }
}
