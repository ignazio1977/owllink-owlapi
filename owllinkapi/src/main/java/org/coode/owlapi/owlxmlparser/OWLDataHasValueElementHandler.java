package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLDataHasValueElementHandler extends
        AbstractDataRestrictionElementHandler<OWLLiteral> {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDataHasValueElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler)
            throws OWLXMLParserException {
        setFiller(handler.getOWLObject());
    }

    @Override
    protected OWLClassExpression createRestriction() {
        return getOWLDataFactory().getOWLDataHasValue(getProperty(),
                getFiller());
    }
}
