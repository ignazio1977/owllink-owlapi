package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractOWLDataPropertyAssertionAxiomElementHandler
        extends
        AbstractOWLAssertionAxiomElementHandler<OWLDataPropertyExpression, OWLLiteral> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLDataPropertyAssertionAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLAnonymousIndividualElementHandler handler)
            throws OWLXMLParserException {
        setSubject(handler.getOWLObject());
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler) {
        setSubject(handler.getOWLObject());
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler) {
        setObject(handler.getOWLObject());
    }
}
