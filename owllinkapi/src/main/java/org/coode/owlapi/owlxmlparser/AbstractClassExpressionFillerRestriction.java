package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractClassExpressionFillerRestriction extends
        AbstractObjectRestrictionElementHandler<OWLClassExpression> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractClassExpressionFillerRestriction(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) {
        setFiller(handler.getOWLObject());
    }
}
