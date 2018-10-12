package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 08-Oct-2009
 */
public class SWRLDataRangeAtomElementHandler extends SWRLAtomElementHandler {

    private OWLDataRange prop;
    private SWRLDArgument arg1 = null;

    /**
     * @param handler
     *        owlxml handler
     */
    public SWRLDataRangeAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler _handler)
            throws OWLXMLParserException {
        prop = _handler.getOWLObject();
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler)
            throws OWLXMLParserException {
        arg1 = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler)
            throws OWLXMLParserException {
        arg1 = getOWLDataFactory().getSWRLLiteralArgument(
                handler.getOWLObject());
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLDataRangeAtom(prop, arg1));
        getParentHandler().handleChild(this);
    }
}
