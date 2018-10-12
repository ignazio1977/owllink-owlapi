package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 08-Oct-2009
 */
public class SWRLDifferentIndividualsAtomElementHandler extends
        SWRLAtomElementHandler {

    private SWRLIArgument arg0;
    private SWRLIArgument arg1;

    /**
     * @param handler
     *        owlxml handler
     */
    public SWRLDifferentIndividualsAtomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler)
            throws OWLXMLParserException {
        if (arg0 == null) {
            arg0 = handler.getOWLObject();
        } else if (arg1 == null) {
            arg1 = handler.getOWLObject();
        }
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler)
            throws OWLXMLParserException {
        if (arg0 == null) {
            arg0 = getOWLDataFactory().getSWRLIndividualArgument(
                    handler.getOWLObject());
        } else if (arg1 == null) {
            arg1 = getOWLDataFactory().getSWRLIndividualArgument(
                    handler.getOWLObject());
        }
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLDifferentIndividualsAtom(arg0, arg1));
        getParentHandler().handleChild(this);
    }
}
