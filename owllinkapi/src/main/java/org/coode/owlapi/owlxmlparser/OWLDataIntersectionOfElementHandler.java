package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataRange;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 23-Apr-2009
 */
public class OWLDataIntersectionOfElementHandler extends
        AbstractOWLDataRangeHandler {

    private Set<OWLDataRange> dataRanges = new HashSet<>();

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDataIntersectionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler)
            throws OWLXMLParserException {
        dataRanges.add(handler.getOWLObject());
    }

    @Override
    protected void endDataRangeElement() throws OWLXMLParserException {
        setDataRange(getOWLDataFactory().getOWLDataIntersectionOf(dataRanges));
    }
}
