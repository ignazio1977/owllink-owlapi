package org.coode.owlapi.owlxmlparser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLDeclarationAxiomElementHandler extends
        AbstractOWLAxiomElementHandler {

    private OWLEntity entity;
    private Set<OWLAnnotation> entityAnnotations;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDeclarationAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        entity = null;
        if (entityAnnotations != null) {
            entityAnnotations.clear();
        }
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler)
            throws OWLXMLParserException {
        entity = (OWLClass) handler.getOWLObject();
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler)
            throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler)
            throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler)
            throws OWLXMLParserException {
        entity = (OWLEntity) handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLAnnotationPropertyElementHandler handler)
            throws OWLXMLParserException {
        entity = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler) {
        entity = handler.getOWLObject();
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLDeclarationAxiom(entity,
                getAnnotations());
    }

    @Override
    public void handleChild(OWLAnnotationElementHandler handler)
            throws OWLXMLParserException {
        if (entity == null) {
            super.handleChild(handler);
        } else {
            if (entityAnnotations == null) {
                entityAnnotations = new HashSet<>();
            }
            entityAnnotations.add(handler.getOWLObject());
        }
    }

    /** @return entity annotations */
    public Set<OWLAnnotation> getEntityAnnotations() {
        if (entityAnnotations == null) {
            return Collections.emptySet();
        } else {
            return entityAnnotations;
        }
    }
}
