package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectHasValueElementHandler extends
        AbstractObjectRestrictionElementHandler<OWLIndividual> {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectHasValueElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler) {
        setFiller(handler.getOWLObject());
    }

    @Override
    public void handleChild(OWLAnonymousIndividualElementHandler handler) {
        setFiller(handler.getOWLObject());
    }

    @Override
    protected OWLClassExpression createRestriction() {
        return getOWLDataFactory().getOWLObjectHasValue(getProperty(),
                getFiller());
    }
}
