package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractOWLIndividualOperandAxiomElementHandler extends
        AbstractOperandAxiomElementHandler<OWLIndividual> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLIndividualOperandAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler) {
        addOperand(handler.getOWLObject());
    }

    @Override
    public void handleChild(OWLAnonymousIndividualElementHandler handler)
            throws OWLXMLParserException {
        addOperand(handler.getOWLObject());
    }
}
