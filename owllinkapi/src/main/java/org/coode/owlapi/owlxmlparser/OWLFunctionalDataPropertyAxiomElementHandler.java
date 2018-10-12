package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLFunctionalDataPropertyAxiomElementHandler
        extends
        AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLDataPropertyExpression> {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLFunctionalDataPropertyAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }

    @Override
    protected OWLAxiom createPropertyCharacteristicAxiom()
            throws OWLXMLParserException {
        if (getProperty() == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "Expected data property element");
        }
        return getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(
                getProperty(), getAnnotations());
    }
}
