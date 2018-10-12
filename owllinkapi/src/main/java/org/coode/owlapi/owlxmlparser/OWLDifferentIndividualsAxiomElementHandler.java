package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLDifferentIndividualsAxiomElementHandler extends
        AbstractOWLIndividualOperandAxiomElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDifferentIndividualsAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLDifferentIndividualsAxiom(
                getOperands(), getAnnotations());
    }
}
