package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractNaryBooleanClassExpressionElementHandler extends
        AbstractClassExpressionElementHandler {

    private Set<OWLClassExpression> operands;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractNaryBooleanClassExpressionElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
        operands = new HashSet<>();
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) {
        operands.add(handler.getOWLObject());
    }

    @Override
    protected void endClassExpressionElement() throws OWLXMLParserException {
        if (operands.size() >= 2) {
            setClassExpression(createClassExpression(operands));
        } else if (operands.size() == 1) {
            setClassExpression(operands.iterator().next());
        } else {
            throw new OWLXMLParserElementNotFoundException(
                    getLineNumber(),
                    getColumnNumber(),
                    "Found zero child elements of an "
                            + getElementName()
                            + " element. At least 2 class expression elements are required as child elements of "
                            + getElementName() + " elements");
        }
    }

    protected abstract OWLClassExpression createClassExpression(
            Set<OWLClassExpression> expressions);
}
