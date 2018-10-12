package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Apr-2007
 */
public class OWLXMLParserException extends OWLParserException {

    private static final long serialVersionUID = 30406L;

    /**
     * @param message
     *        message
     * @param lineNumber
     *        lineNumber
     * @param columnNumber
     *        columnNumber
     */
    public OWLXMLParserException(String message, int lineNumber,
            int columnNumber) {
        super(message, lineNumber, columnNumber);
    }
}
