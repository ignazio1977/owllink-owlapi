package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLDataPropertyDomainAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    private OWLClassExpression domain;
    private OWLDataPropertyExpression property;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDataPropertyDomainAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) {
        domain = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler) {
        property = handler.getOWLObject();
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "data property element");
        }
        if (domain == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "class expression element");
        }
        return getOWLDataFactory().getOWLDataPropertyDomainAxiom(property,
                domain, getAnnotations());
    }
}
