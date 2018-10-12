package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLNegativeDataPropertyAssertionAxiomElementHandler extends
        AbstractOWLDataPropertyAssertionAxiomElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLNegativeDataPropertyAssertionAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLNegativeDataPropertyAssertionAxiom(
                getProperty(), getSubject(), getObject(), getAnnotations());
    }
}
