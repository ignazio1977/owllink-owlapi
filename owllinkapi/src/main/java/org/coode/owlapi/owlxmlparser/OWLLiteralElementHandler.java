package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public class OWLLiteralElementHandler extends
        AbstractOWLElementHandler<OWLLiteral> {

    private OWLLiteral literal;
    private IRI iri;
    private String lang;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLLiteralElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        if (localName.equals(OWLXMLVocabulary.DATATYPE_IRI.getShortForm())) {
            iri = getIRI(value);
        } else if (localName.equals("lang")) {
            lang = value;
        }
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        if (iri != null && !iri.isPlainLiteral()) {
            literal = getOWLDataFactory().getOWLLiteral(getText(),
                    getOWLDataFactory().getOWLDatatype(iri));
        } else {
            literal = getOWLDataFactory().getOWLLiteral(getText(), lang);
        }
        lang = null;
        iri = null;
        getParentHandler().handleChild(this);
    }

    @Override
    public OWLLiteral getOWLObject() {
        return literal;
    }

    @Override
    public boolean isTextContentPossible() {
        return true;
    }
}
