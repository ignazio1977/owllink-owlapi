package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLInverseFunctionalObjectPropertyAxiomElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLInverseFunctionalObjectPropertyAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLAxiom createPropertyCharacteristicAxiom() {
        return getOWLDataFactory().getOWLInverseFunctionalObjectPropertyAxiom(
                getProperty(), getAnnotations());
    }
}
