package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractOWLObjectCardinalityElementHandler extends
        AbstractClassExpressionFillerRestriction {

    private int cardinality;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractOWLObjectCardinalityElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        setFiller(getOWLDataFactory().getOWLThing());
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        if (localName.equals("cardinality")) {
            cardinality = Integer.parseInt(value);
        }
    }

    @Override
    protected OWLClassExpression createRestriction() {
        return createCardinalityRestriction();
    }

    protected abstract OWLClassExpression createCardinalityRestriction();

    protected int getCardinality() {
        return cardinality;
    }
}
