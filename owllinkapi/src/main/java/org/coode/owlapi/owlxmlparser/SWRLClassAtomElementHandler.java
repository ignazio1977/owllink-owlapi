package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 08-Oct-2009
 */
public class SWRLClassAtomElementHandler extends SWRLAtomElementHandler {

    private OWLClassExpression ce;
    private SWRLIArgument arg;

    /**
     * @param handler
     *        owlxml handler
     */
    public SWRLClassAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler)
            throws OWLXMLParserException {
        arg = handler.getOWLObject();
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler)
            throws OWLXMLParserException {
        ce = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler)
            throws OWLXMLParserException {
        arg = getOWLDataFactory().getSWRLIndividualArgument(
                handler.getOWLObject());
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLClassAtom(ce, arg));
        getParentHandler().handleChild(this);
    }
}
