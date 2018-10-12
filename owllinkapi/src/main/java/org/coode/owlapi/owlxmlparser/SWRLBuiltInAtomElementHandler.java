package org.coode.owlapi.owlxmlparser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 08-Oct-2009
 */
public class SWRLBuiltInAtomElementHandler extends SWRLAtomElementHandler {

    private IRI iri;
    private List<SWRLDArgument> args = new ArrayList<>();

    /**
     * @param handler
     *        owlxml handler
     */
    public SWRLBuiltInAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler)
            throws OWLXMLParserException {
        args.add(handler.getOWLObject());
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler)
            throws OWLXMLParserException {
        args.add(getOWLDataFactory().getSWRLLiteralArgument(
                handler.getOWLObject()));
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLBuiltInAtom(iri, args));
        getParentHandler().handleChild(this);
    }
}
