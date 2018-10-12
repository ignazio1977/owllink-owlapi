package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.SWRLAtom;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 02-Oct-2009
 */
public abstract class SWRLAtomElementHandler extends
        AbstractOWLElementHandler<SWRLAtom> {

    private SWRLAtom atom;

    /**
     * @param handler
     *        owlxml handler
     */
    public SWRLAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    /**
     * @param atom
     *        atom
     */
    public void setAtom(SWRLAtom atom) {
        this.atom = atom;
    }

    @Override
    public SWRLAtom getOWLObject() throws OWLXMLParserException {
        return atom;
    }
}
