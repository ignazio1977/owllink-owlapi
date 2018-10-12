package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public abstract class AbstractClassExpressionElementHandler extends
        AbstractOWLElementHandler<OWLClassExpression> {

    private OWLClassExpression desc;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractClassExpressionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        endClassExpressionElement();
        getParentHandler().handleChild(this);
    }

    protected abstract void endClassExpressionElement()
            throws OWLXMLParserException;

    protected void setClassExpression(OWLClassExpression desc) {
        this.desc = desc;
    }

    @Override
    public OWLClassExpression getOWLObject() {
        return desc;
    }
}
