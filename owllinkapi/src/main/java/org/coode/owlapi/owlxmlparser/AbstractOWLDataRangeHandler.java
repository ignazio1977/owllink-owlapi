package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 */
public abstract class AbstractOWLDataRangeHandler extends
        AbstractOWLElementHandler<OWLDataRange> {

    private OWLDataRange dataRange;

    /**
     * @param handler
     *        owlxml handler
     */
    protected AbstractOWLDataRangeHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    /**
     * @param dataRange
     *        datarange
     */
    public void setDataRange(OWLDataRange dataRange) {
        this.dataRange = dataRange;
    }

    @Override
    public OWLDataRange getOWLObject() {
        return dataRange;
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        endDataRangeElement();
        getParentHandler().handleChild(this);
    }

    protected abstract void endDataRangeElement() throws OWLXMLParserException;
}
