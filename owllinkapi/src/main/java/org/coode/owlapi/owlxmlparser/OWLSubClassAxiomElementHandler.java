package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLSubClassAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    private OWLClassExpression subClass;
    private OWLClassExpression supClass;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLSubClassAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        subClass = null;
        supClass = null;
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler)
            throws OWLXMLParserException {
        if (subClass == null) {
            subClass = handler.getOWLObject();
        } else if (supClass == null) {
            supClass = handler.getOWLObject();
        }
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, supClass,
                getAnnotations());
    }
}
