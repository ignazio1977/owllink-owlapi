package org.coode.owlapi.owlxmlparser;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public interface OWLElementHandlerFactory {

    /** @return element name */
    String getElementName();

    /**
     * @param handler
     *        owlxml handler
     * @return element handler
     */
    OWLElementHandler<?> createHandler(OWLXMLParserHandler handler);
}
