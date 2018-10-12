package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectComplementOfElementHandler extends
        AbstractClassExpressionElementHandler {

    private OWLClassExpression operand;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectComplementOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) {
        operand = handler.getOWLObject();
    }

    @Override
    protected void endClassExpressionElement() throws OWLXMLParserException {
        if (operand == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "class expression element");
        }
        setClassExpression(getOWLDataFactory()
                .getOWLObjectComplementOf(operand));
    }
}
