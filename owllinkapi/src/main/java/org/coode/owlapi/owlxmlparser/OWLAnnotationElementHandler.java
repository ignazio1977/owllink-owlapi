package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 18-Dec-2006
 */
public class OWLAnnotationElementHandler extends
        AbstractOWLElementHandler<OWLAnnotation> {

    private Set<OWLAnnotation> annotations;
    private OWLAnnotationProperty property;
    private OWLAnnotationValue object;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        getParentHandler().handleChild(this);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        super.attribute(localName, value);
        // Legacy Handling
        if (localName.equals(OWLXMLVocabulary.ANNOTATION_URI.getShortForm())) {
            IRI iri = getIRI(value);
            property = getOWLDataFactory().getOWLAnnotationProperty(iri);
        }
    }

    @Override
    public void handleChild(OWLAnnotationElementHandler handler)
            throws OWLXMLParserException {
        if (annotations == null) {
            annotations = new HashSet<>();
        }
        annotations.add(handler.getOWLObject());
    }

    @Override
    public void handleChild(OWLAnonymousIndividualElementHandler handler)
            throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler)
            throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLAnnotationPropertyElementHandler handler)
            throws OWLXMLParserException {
        property = handler.getOWLObject();
    }

    @Override
    public void handleChild(AbstractIRIElementHandler handler)
            throws OWLXMLParserException {
        object = handler.getOWLObject();
    }

    @Override
    public OWLAnnotation getOWLObject() {
        if (annotations == null) {
            return getOWLDataFactory().getOWLAnnotation(property, object);
        } else {
            return getOWLDataFactory().getOWLAnnotation(property, object,
                    annotations);
        }
    }

    @Override
    public boolean isTextContentPossible() {
        return false;
    }
}
