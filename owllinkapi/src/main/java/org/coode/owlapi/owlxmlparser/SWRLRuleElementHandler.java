package org.coode.owlapi.owlxmlparser;

import java.util.LinkedHashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group, Date: 02-Oct-2009
 */
public class SWRLRuleElementHandler extends AbstractOWLAxiomElementHandler {

    private Set<SWRLAtom> body = null;
    private Set<SWRLAtom> head = null;

    /**
     * @param handler
     *        owlxml handler
     */
    public SWRLRuleElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getSWRLRule(body, head, getAnnotations());
    }

    @Override
    public void handleChild(SWRLAtomListElementHandler handler)
            throws OWLXMLParserException {
        if (body == null) {
            body = new LinkedHashSet<>(handler.getOWLObject());
        } else if (head == null) {
            head = new LinkedHashSet<>(handler.getOWLObject());
        }
    }
}
