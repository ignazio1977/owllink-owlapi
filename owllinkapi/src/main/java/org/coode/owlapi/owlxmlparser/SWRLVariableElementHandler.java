package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 02-Oct-2009
 */
public class SWRLVariableElementHandler extends
        AbstractOWLElementHandler<SWRLVariable> {

    /**
     * @param handler
     *        owlxml handler
     */
    protected SWRLVariableElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    private IRI iri;

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        getParentHandler().handleChild(this);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    public SWRLVariable getOWLObject() throws OWLXMLParserException {
        if (iri != null) {
            return getOWLDataFactory().getSWRLVariable(iri);
        }
        return null;
    }
}
