package org.coode.owlapi.owlxmlparser;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Apr-2007
 */
public class OWLXMLParserAttributeNotFoundException extends
        OWLXMLParserException {

    private static final long serialVersionUID = 30406L;

    /**
     * @param lineNumber
     *        lineNumber
     * @param columnNumber
     *        columnNumber
     * @param attributeName
     *        attributeName
     */
    public OWLXMLParserAttributeNotFoundException(int lineNumber,
            int columnNumber, String attributeName) {
        super("Attribute not found: " + attributeName, lineNumber, columnNumber);
    }
}
