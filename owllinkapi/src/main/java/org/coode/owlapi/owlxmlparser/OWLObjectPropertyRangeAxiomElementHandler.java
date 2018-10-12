package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectPropertyRangeAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    private OWLClassExpression range;
    private OWLObjectPropertyExpression property;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectPropertyRangeAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) {
        range = handler.getOWLObject();
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        property = handler.getOWLObject();
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(),
                    OWLXMLVocabulary.OBJECT_PROPERTY.getShortForm());
        }
        if (range == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(), "OWL class expression element");
        }
        return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(property,
                range, getAnnotations());
    }
}
