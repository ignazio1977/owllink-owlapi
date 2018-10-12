package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLDataOneOfElementHandler extends AbstractOWLDataRangeHandler {

    Set<OWLLiteral> constants;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDataOneOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        constants = new HashSet<>();
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler) {
        constants.add(handler.getOWLObject());
    }

    @Override
    protected void endDataRangeElement() throws OWLXMLParserException {
        if (constants.isEmpty()) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "data oneOf element");
        }
        setDataRange(getOWLDataFactory().getOWLDataOneOf(constants));
    }
}
