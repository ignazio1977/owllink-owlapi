package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 * @param <F>
 *        filler type
 */
public abstract class AbstractDataRestrictionElementHandler<F extends OWLObject>
        extends AbstractRestrictionElementHandler<OWLDataPropertyExpression, F> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractDataRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }
}
