package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 17-May-2009
 */
public abstract class AbstractIRIElementHandler extends
        AbstractOWLElementHandler<IRI> {

    /**
     * @param handler
     *        owlxml handler
     */
    public AbstractIRIElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }
}
