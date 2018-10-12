package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 17-May-2009
 */
public class AbbreviatedIRIElementHandler extends AbstractIRIElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbbreviatedIRIElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    private IRI iri;

    @Override
    public boolean isTextContentPossible() {
        return true;
    }

    @Override
    public IRI getOWLObject() throws OWLXMLParserException {
        return iri;
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        String iriText = getText().trim();
        iri = getAbbreviatedIRI(iriText);
        getParentHandler().handleChild(this);
    }
}
