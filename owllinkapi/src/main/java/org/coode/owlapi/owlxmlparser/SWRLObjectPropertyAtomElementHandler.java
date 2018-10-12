package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 08-Oct-2009
 */
public class SWRLObjectPropertyAtomElementHandler extends
        SWRLAtomElementHandler {

    private OWLObjectPropertyExpression prop;
    private SWRLIArgument arg0 = null;
    private SWRLIArgument arg1 = null;

    /**
     * @param handler
     *        owlxml handler
     */
    public SWRLObjectPropertyAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler)
            throws OWLXMLParserException {
        prop = handler.getOWLObject();
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
        setAtom(getOWLDataFactory().getSWRLObjectPropertyAtom(prop, arg0, arg1));
        getParentHandler().handleChild(this);
    }
}
