package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public abstract class AbstractOWLObjectPropertyElementHandler extends
        AbstractOWLElementHandler<OWLObjectPropertyExpression> {

    private OWLObjectPropertyExpression property;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        endObjectPropertyElement();
        getParentHandler().handleChild(this);
    }

    protected void setOWLObjectPropertyExpression(
            OWLObjectPropertyExpression prop) {
        property = prop;
    }

    @Override
    public OWLObjectPropertyExpression getOWLObject() {
        return property;
    }

    protected abstract void endObjectPropertyElement()
            throws OWLXMLParserException;
}
