package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 10-Apr-2007
 */
public class OWLDisjointUnionElementHandler extends
        AbstractOWLAxiomElementHandler {

    private OWLClass cls;
    private Set<OWLClassExpression> classExpressions;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDisjointUnionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        classExpressions = new HashSet<>();
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLDisjointUnionAxiom(cls,
                classExpressions, getAnnotations());
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler)
            throws OWLXMLParserException {
        if (cls == null) {
            OWLClassExpression desc = handler.getOWLObject();
            cls = (OWLClass) desc;
        } else {
            classExpressions.add(handler.getOWLObject());
        }
    }
}
