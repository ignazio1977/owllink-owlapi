package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractClassExpressionOperandAxiomElementHandler extends
        AbstractOperandAxiomElementHandler<OWLClassExpression> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractClassExpressionOperandAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) {
        addOperand(handler.getOWLObject());
    }
}
