package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectSomeValuesFromElementHandler extends
        AbstractClassExpressionFillerRestriction {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectSomeValuesFromElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLClassExpression createRestriction() {
        return getOWLDataFactory().getOWLObjectSomeValuesFrom(getProperty(),
                getFiller());
    }
}
