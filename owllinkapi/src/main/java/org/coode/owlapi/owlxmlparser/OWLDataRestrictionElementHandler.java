package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLDataRestrictionElementHandler extends
        AbstractOWLDataRangeHandler {

    private OWLDataRange dataRange;
    private OWLLiteral constant;
    private IRI facetIRI;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDataRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler) {
        dataRange = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler)
            throws OWLXMLParserException {
        constant = handler.getOWLObject();
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        super.attribute(localName, value);
        if (localName.equals("facet")) {
            facetIRI = getIRI(value);
        }
    }

    @Override
    protected void endDataRangeElement() throws OWLXMLParserException {
        if (dataRange == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "data range element");
        }
        if (constant == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "typed constant element");
        }
        setDataRange(getOWLDataFactory().getOWLDatatypeRestriction(
                (OWLDatatype) dataRange, OWLFacet.getFacet(facetIRI), constant));
    }
}
