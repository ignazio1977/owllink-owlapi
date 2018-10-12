package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractDataCardinalityRestrictionElementHandler extends
        AbstractDataRangeFillerRestrictionElementHandler {

    private int cardinality;

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractDataCardinalityRestrictionElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void attribute(String localName, String value)
            throws OWLParserException {
        if (localName.equals("cardinality")) {
            cardinality = Integer.parseInt(value);
        }
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        setFiller(getOWLDataFactory().getTopDatatype());
    }

    protected int getCardinality() {
        return cardinality;
    }
}
