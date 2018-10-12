package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectExistsSelfElementHandler extends
        AbstractClassExpressionElementHandler {

    private OWLObjectPropertyExpression property;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectExistsSelfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        property = handler.getOWLObject();
    }

    @Override
    protected void endClassExpressionElement() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(),
                    "Was expecting object property expression element");
        }
        setClassExpression(getOWLDataFactory().getOWLObjectHasSelf(property));
    }
}
