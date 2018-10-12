package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 * @param <O>
 *        operand type
 */
public abstract class AbstractOperandAxiomElementHandler<O extends OWLObject>
        extends AbstractOWLAxiomElementHandler {

    private Set<O> operands;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOperandAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        operands = new HashSet<>();
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        operands.clear();
    }

    protected Set<O> getOperands() {
        return operands;
    }

    protected void addOperand(O operand) {
        operands.add(operand);
    }
}
