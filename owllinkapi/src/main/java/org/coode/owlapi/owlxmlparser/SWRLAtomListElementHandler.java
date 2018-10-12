package org.coode.owlapi.owlxmlparser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 02-Oct-2009
 */
public class SWRLAtomListElementHandler extends
        AbstractOWLElementHandler<List<SWRLAtom>> {

    private List<SWRLAtom> atoms = new ArrayList<>();

    /**
     * @param handler
     *        owlxml handler
     */
    protected SWRLAtomListElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(SWRLAtomElementHandler handler)
            throws OWLXMLParserException {
        atoms.add(handler.getOWLObject());
    }

    @Override
    public List<SWRLAtom> getOWLObject() throws OWLXMLParserException {
        return atoms;
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        getParentHandler().handleChild(this);
    }
}
