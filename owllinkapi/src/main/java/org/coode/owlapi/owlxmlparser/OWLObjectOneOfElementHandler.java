package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public class OWLObjectOneOfElementHandler extends
        AbstractClassExpressionElementHandler {

    private Set<OWLIndividual> individuals;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLObjectOneOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        individuals = new HashSet<>();
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler) {
        individuals.add(handler.getOWLObject());
    }

    @Override
    protected void endClassExpressionElement() throws OWLXMLParserException {
        if (individuals.size() < 1) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(),
                    getColumnNumber(),
                    "Expected at least one individual in object oneOf");
        }
        setClassExpression(getOWLDataFactory().getOWLObjectOneOf(individuals));
    }
}
