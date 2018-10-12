package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractOWLDataPropertyOperandAxiomElementHandler extends
        AbstractOperandAxiomElementHandler<OWLDataPropertyExpression> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLDataPropertyOperandAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler) {
        addOperand(handler.getOWLObject());
    }
}
