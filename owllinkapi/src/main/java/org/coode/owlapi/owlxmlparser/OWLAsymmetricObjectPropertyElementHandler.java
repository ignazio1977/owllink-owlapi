package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management
 *         Group, Date: 07-Sep-2008
 */
public class OWLAsymmetricObjectPropertyElementHandler extends
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLAsymmetricObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLAxiom createPropertyCharacteristicAxiom() {
        return getOWLDataFactory().getOWLAsymmetricObjectPropertyAxiom(
                getProperty(), getAnnotations());
    }
}
