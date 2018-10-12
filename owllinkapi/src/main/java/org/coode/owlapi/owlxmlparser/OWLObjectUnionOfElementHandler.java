package org.coode.owlapi.owlxmlparser;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectUnionOfElementHandler extends
        AbstractNaryBooleanClassExpressionElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectUnionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLClassExpression createClassExpression(
            Set<OWLClassExpression> operands) {
        return getOWLDataFactory().getOWLObjectUnionOf(operands);
    }
}
