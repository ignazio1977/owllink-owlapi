package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.xml.sax.SAXException;

/**
 * Translates an {@link org.semanticweb.owlapi.io.OWLParserException} to a
 * {@link org.xml.sax.SAXException}.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group, Date: 07-Dec-2009
 */
public class TranslatedOWLParserException extends SAXException {

    private static final long serialVersionUID = 30406L;
    private OWLParserException parserException;

    /**
     * @param cause
     *        cause
     */
    public TranslatedOWLParserException(OWLParserException cause) {
        super(cause);
        parserException = cause;
    }

    /** @return cause */
    public OWLParserException getParserException() {
        return parserException;
    }
}
