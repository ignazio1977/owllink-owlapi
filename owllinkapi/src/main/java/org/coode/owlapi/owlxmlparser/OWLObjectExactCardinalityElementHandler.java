package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectExactCardinalityElementHandler extends
        AbstractOWLObjectCardinalityElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectExactCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLClassExpression createCardinalityRestriction() {
        return getOWLDataFactory().getOWLObjectExactCardinality(
                getCardinality(), getProperty(), getFiller());
    }
}
