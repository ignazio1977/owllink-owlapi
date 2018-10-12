package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLDataPropertyRangeAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    private OWLDataPropertyExpression property;
    private OWLDataRange range;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDataPropertyRangeAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler) {
        range = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler) {
        property = handler.getOWLObject();
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "data property element");
        }
        if (range == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "data range element");
        }
        return getOWLDataFactory().getOWLDataPropertyRangeAxiom(property,
                range, getAnnotations());
    }
}
