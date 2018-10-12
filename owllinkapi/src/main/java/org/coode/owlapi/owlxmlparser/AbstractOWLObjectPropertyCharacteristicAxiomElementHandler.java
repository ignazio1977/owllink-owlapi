package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractOWLObjectPropertyCharacteristicAxiomElementHandler
        extends
        AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLObjectPropertyExpression> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLObjectPropertyCharacteristicAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }
}
