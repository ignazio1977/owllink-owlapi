package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 * @param <F>
 *        filler type
 */
public abstract class AbstractObjectRestrictionElementHandler<F extends OWLObject>
        extends
        AbstractRestrictionElementHandler<OWLObjectPropertyExpression, F> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractObjectRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }
}
