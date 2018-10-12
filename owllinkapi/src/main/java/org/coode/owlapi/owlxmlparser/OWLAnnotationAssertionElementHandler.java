package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 20-Mar-2007
 */
public class OWLAnnotationAssertionElementHandler extends
        AbstractOWLAxiomElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLAnnotationAssertionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    private OWLAnnotationSubject subject = null;
    private OWLAnnotationValue object = null;
    private OWLAnnotationProperty property = null;

    @Override
    public void handleChild(AbstractIRIElementHandler handler)
            throws OWLXMLParserException {
        if (subject == null) {
            subject = handler.getOWLObject();
        } else {
            object = handler.getOWLObject();
        }
    }

    @Override
    public void handleChild(OWLAnonymousIndividualElementHandler handler)
            throws OWLXMLParserException {
        if (subject == null) {
            subject = handler.getOWLObject();
        } else {
            object = handler.getOWLObject();
        }
    }

    @Override
    public void handleChild(OWLAnnotationPropertyElementHandler handler)
            throws OWLXMLParserException {
        property = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler)
            throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLAnnotationAssertionAxiom(property,
                subject, object, getAnnotations());
    }
}
