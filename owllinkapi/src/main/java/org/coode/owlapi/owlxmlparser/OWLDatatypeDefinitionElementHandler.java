package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 05-Jun-2009
 */
public class OWLDatatypeDefinitionElementHandler extends
        AbstractOWLAxiomElementHandler {

    private OWLDatatype datatype;
    private OWLDataRange dataRange;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDatatypeDefinitionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler)
            throws OWLXMLParserException {
        OWLDataRange handledDataRange = handler.getOWLObject();
        if (handledDataRange.isOWLDatatype() && datatype == null) {
            datatype = handledDataRange.asOWLDatatype();
        } else {
            dataRange = handledDataRange;
        }
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLDatatypeDefinitionAxiom(datatype,
                dataRange, getAnnotations());
    }
}
