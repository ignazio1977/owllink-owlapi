package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 10-Apr-2007
 */
public class OWLDatatypeFacetRestrictionElementHandler extends
        AbstractOWLElementHandler<OWLFacetRestriction> {

    private OWLFacet facet;
    private OWLLiteral constant;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDatatypeFacetRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler)
            throws OWLXMLParserException {
        constant = handler.getOWLObject();
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        if (localName.equals("facet")) {
            facet = OWLFacet.getFacet(IRI.create(value));
        }
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        getParentHandler().handleChild(this);
    }

    @Override
    public OWLFacetRestriction getOWLObject() {
        return getOWLDataFactory().getOWLFacetRestriction(facet, constant);
    }
}
