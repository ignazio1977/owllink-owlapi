package org.coode.owlapi.owlxmlparser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractOWLAxiomElementHandler extends
        AbstractOWLElementHandler<OWLAxiom> {

    private OWLAxiom axiom;
    private Set<OWLAnnotation> annotations;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public OWLAxiom getOWLObject() {
        return axiom;
    }

    /**
     * @param axiom
     *        axiom to add
     */
    public void setAxiom(OWLAxiom axiom) {
        this.axiom = axiom;
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        if (annotations != null) {
            annotations.clear();
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
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        setAxiom(createAxiom());
        getParentHandler().handleChild(this);
    }

    /** @return annotations */
    public Set<OWLAnnotation> getAnnotations() {
        if (annotations == null) {
            return Collections.emptySet();
        } else {
            return annotations;
        }
    }

    protected abstract OWLAxiom createAxiom() throws OWLXMLParserException;
}
