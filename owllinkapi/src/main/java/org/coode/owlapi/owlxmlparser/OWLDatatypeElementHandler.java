package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 18-Dec-2006
 */
public class OWLDatatypeElementHandler extends AbstractOWLDataRangeHandler {

    private IRI iri;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDatatypeElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    protected void endDataRangeElement() throws OWLXMLParserException {
        if (iri == null) {
            throw new OWLXMLParserAttributeNotFoundException(getLineNumber(),
                    getColumnNumber(), "IRI");
        }
        setDataRange(getOWLDataFactory().getOWLDatatype(iri));
    }
}
