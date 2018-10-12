package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractOWLObjectPropertyOperandAxiomElementHandler
        extends AbstractOperandAxiomElementHandler<OWLObjectPropertyExpression> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLObjectPropertyOperandAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        addOperand(handler.getOWLObject());
    }
}
