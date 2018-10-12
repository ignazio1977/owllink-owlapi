package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 * @param <P>
 *        property type
 * @param <O>
 *        object type
 */
public abstract class AbstractOWLAssertionAxiomElementHandler<P extends OWLPropertyExpression, O extends OWLObject>
        extends AbstractOWLAxiomElementHandler {

    private OWLIndividual subject;
    private P property;
    private O object;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    /** @return subject */
    public OWLIndividual getSubject() {
        return subject;
    }

    /** @return property */
    public P getProperty() {
        return property;
    }

    /** @return object */
    public O getObject() {
        return object;
    }

    /**
     * @param subject
     *        subject
     */
    public void setSubject(OWLIndividual subject) {
        this.subject = subject;
    }

    /**
     * @param property
     *        property
     */
    public void setProperty(P property) {
        this.property = property;
    }

    /**
     * @param object
     *        object
     */
    public void setObject(O object) {
        this.object = object;
    }
}
