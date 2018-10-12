package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataRange;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractDataRangeFillerRestrictionElementHandler extends
        AbstractDataRestrictionElementHandler<OWLDataRange> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractDataRangeFillerRestrictionElementHandler(
            OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler) {
        setFiller(handler.getOWLObject());
    }
}
