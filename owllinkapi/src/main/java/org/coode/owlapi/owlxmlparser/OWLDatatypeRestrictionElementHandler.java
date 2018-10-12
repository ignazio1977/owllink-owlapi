package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 10-Apr-2007
 */
public class OWLDatatypeRestrictionElementHandler extends
        AbstractOWLDataRangeHandler {

    private OWLDatatype restrictedDataRange;
    private Set<OWLFacetRestriction> facetRestrictions;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLDatatypeRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        facetRestrictions = new HashSet<>();
    }

    @Override
    protected void endDataRangeElement() {
        setDataRange(getOWLDataFactory().getOWLDatatypeRestriction(
                restrictedDataRange, facetRestrictions));
    }

    @Override
    public void handleChild(AbstractOWLDataRangeHandler handler) {
        OWLDataRange dr = handler.getOWLObject();
        if (dr.isOWLDatatype()) {
            restrictedDataRange = dr.asOWLDatatype();
        }
    }

    @Override
    public void handleChild(OWLDatatypeFacetRestrictionElementHandler handler) {
        facetRestrictions.add(handler.getOWLObject());
    }
}
