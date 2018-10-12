package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectMaxCardinalityElementHandler extends
        AbstractOWLObjectCardinalityElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectMaxCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLClassExpression createCardinalityRestriction() {
        return getOWLDataFactory().getOWLObjectMaxCardinality(getCardinality(),
                getProperty(), getFiller());
    }
}
