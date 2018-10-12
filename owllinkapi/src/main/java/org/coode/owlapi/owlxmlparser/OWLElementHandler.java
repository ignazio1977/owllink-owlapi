package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 * @param <O>
 *        handled type
 */
public interface OWLElementHandler<O> {

    /**
     * @param name
     *        element name
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void startElement(String name) throws OWLXMLParserException;

    /**
     * @param localName
     *        local attribute name
     * @param value
     *        attribute value
     * @throws OWLParserException
     *         if an error is raised
     */
    void attribute(String localName, String value) throws OWLParserException;

    /**
     * @throws OWLParserException
     *         if an error is raised
     * @throws UnloadableImportException
     *         if an import cannot be resolved
     */
    void endElement() throws OWLParserException, UnloadableImportException;

    /**
     * @return object
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    O getOWLObject() throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void setParentHandler(OWLElementHandler<?> handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(AbstractOWLAxiomElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(AbstractClassExpressionElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(AbstractOWLObjectPropertyElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(OWLDataPropertyElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(OWLIndividualElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(AbstractOWLDataRangeHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(OWLLiteralElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(OWLAnnotationElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(OWLAnonymousIndividualElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(OWLSubObjectPropertyChainElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(OWLDatatypeFacetRestrictionElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(OWLAnnotationPropertyElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(AbstractIRIElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param chars
     *        chars to handle
     * @param start
     *        start index
     * @param length
     *        end index
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChars(char[] chars, int start, int length)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(SWRLVariableElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(SWRLAtomElementHandler handler)
            throws OWLXMLParserException;

    /**
     * @param handler
     *        element handler
     * @throws OWLXMLParserException
     *         if an error is raised
     */
    void handleChild(SWRLAtomListElementHandler handler)
            throws OWLXMLParserException;

    /** @return text handled */
    String getText();

    /** @return true if text can be contained */
    boolean isTextContentPossible();
}
