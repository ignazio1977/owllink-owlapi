package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataRange;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLDataComplementOfElementHandler extends
        AbstractOWLDataRangeHandler {

    private OWLDataRange operand;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDataComplementOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler) {
        operand = handler.getOWLObject();
    }

    @Override
    protected void endDataRangeElement() throws OWLXMLParserException {
        if (operand == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "data range element");
        }
        setDataRange(getOWLDataFactory().getOWLDataComplementOf(operand));
    }
}
