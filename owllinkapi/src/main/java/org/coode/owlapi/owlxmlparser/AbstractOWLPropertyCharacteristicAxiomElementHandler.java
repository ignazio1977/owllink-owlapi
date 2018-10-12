package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 * @param <P>
 *        property type
 */
public abstract class AbstractOWLPropertyCharacteristicAxiomElementHandler<P extends OWLObject>
        extends AbstractOWLAxiomElementHandler {

    private P property;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLPropertyCharacteristicAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    /**
     * @param property
     *        property
     */
    public void setProperty(P property) {
        this.property = property;
    }

    protected P getProperty() {
        return property;
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "property element");
        }
        return createPropertyCharacteristicAxiom();
    }

    protected abstract OWLAxiom createPropertyCharacteristicAxiom()
            throws OWLXMLParserException;
}
