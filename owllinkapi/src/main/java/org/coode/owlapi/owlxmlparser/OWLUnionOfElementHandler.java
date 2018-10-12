package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 10-Apr-2007
 */
public class OWLUnionOfElementHandler extends
        AbstractOWLElementHandler<OWLClassExpression> {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLUnionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler)
            throws OWLXMLParserException {
        // We simply pass on to our parent, which MUST be an OWLDisjointUnionOf
        getParentHandler().handleChild(handler);
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {}

    @Override
    public OWLClassExpression getOWLObject() {
        throw new OWLRuntimeException(
                "getOWLObject should not be called on OWLUnionOfElementHandler");
    }
}
