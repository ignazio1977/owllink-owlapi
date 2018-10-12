package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLEquivalentClassesAxiomElementHandler extends
        AbstractClassExpressionOperandAxiomElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLEquivalentClassesAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLEquivalentClassesAxiom(getOperands(),
                getAnnotations());
    }
}
