package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectPropertyAssertionAxiomElementHandler extends
        AbstractOWLObjectPropertyAssertionAxiomElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectPropertyAssertionAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(
                getProperty(), getSubject(), getObject(), getAnnotations());
    }
}
