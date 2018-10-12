package org.coode.owlapi.owlxmlparser;

import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLInverseObjectPropertiesAxiomElementHandler extends
        AbstractOWLObjectPropertyOperandAxiomElementHandler {

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLInverseObjectPropertiesAxiomElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        Set<OWLObjectPropertyExpression> props = getOperands();
        if (props.size() > 2 || props.size() < 1) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(),
                    "Expected 2 object property expression elements");
        }
        Iterator<OWLObjectPropertyExpression> it = props.iterator();
        OWLObjectPropertyExpression propA = it.next();
        OWLObjectPropertyExpression propB;
        if (it.hasNext()) {
            propB = it.next();
        } else {
            // Syntactic variant of symmetric property
            propB = propA;
        }
        return getOWLDataFactory().getOWLInverseObjectPropertiesAxiom(propA,
                propB, getAnnotations());
    }
}
