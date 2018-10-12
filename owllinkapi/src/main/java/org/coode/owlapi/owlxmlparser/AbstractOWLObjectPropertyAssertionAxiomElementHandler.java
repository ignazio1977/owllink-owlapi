package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractOWLObjectPropertyAssertionAxiomElementHandler
        extends
        AbstractOWLAssertionAxiomElementHandler<OWLObjectPropertyExpression, OWLIndividual> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLObjectPropertyAssertionAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLAnonymousIndividualElementHandler handler)
            throws OWLXMLParserException {
        if (getSubject() == null) {
            setSubject(handler.getOWLObject());
        } else if (getObject() == null) {
            setObject(handler.getOWLObject());
        }
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler)
            throws OWLXMLParserException {
        if (getSubject() == null) {
            setSubject(handler.getOWLObject());
        } else if (getObject() == null) {
            setObject(handler.getOWLObject());
        }
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }
}
