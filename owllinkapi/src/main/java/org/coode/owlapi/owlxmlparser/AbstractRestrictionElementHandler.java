package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 * @param <P>
 *        property type
 * @param <F>
 *        filler type
 */
public abstract class AbstractRestrictionElementHandler<P extends OWLPropertyExpression, F extends OWLObject>
        extends AbstractClassExpressionElementHandler {

    private P property;
    private F filler;

    /**
     * @param handler
     *        owlxml handler
     */
    protected AbstractRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    protected void setProperty(P prop) {
        property = prop;
    }

    protected P getProperty() {
        return property;
    }

    protected F getFiller() {
        return filler;
    }

    protected void setFiller(F filler) {
        this.filler = filler;
    }

    @Override
    protected void endClassExpressionElement() throws OWLXMLParserException {
        setClassExpression(createRestriction());
    }

    protected abstract OWLClassExpression createRestriction();
}
