package org.coode.owlapi.owlxmlparser;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Apr-2007
 */
public class OWLXMLParserElementNotFoundException extends OWLXMLParserException {

    private static final long serialVersionUID = 30406L;

    /**
     * @param lineNumber
     *        lineNumber
     * @param columnNumber
     *        columnNumber
     * @param elementType
     *        elementType
     */
    public OWLXMLParserElementNotFoundException(int lineNumber,
            int columnNumber, String elementType) {
        super("Element not found: " + elementType, lineNumber, columnNumber);
    }
}
