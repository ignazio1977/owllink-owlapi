package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 13-Dec-2006
 * @param <O>
 *        handled type
 */
public abstract class AbstractOWLElementHandler<O> implements OWLElementHandler<O> {

    private OWLXMLParserHandler handler;
    private OWLElementHandler<?> parentHandler;
    private StringBuilder sb;
    private String elementName;

    /**
     * @param handler
     *        owlxml handler
     */
    protected AbstractOWLElementHandler(OWLXMLParserHandler handler) {
        this.handler = handler;
    }

    /** @return loader config */
    public OWLOntologyLoaderConfiguration getConfiguration() {
        return handler.getConfiguration();
    }

    /**
     * @param localName
     *        localName
     * @param value
     *        value
     * @return iri
     * @throws OWLParserException
     *         if an error is raised
     */
    public IRI getIRIFromAttribute(String localName, String value) throws OWLParserException {
        if (localName.equals(OWLXMLVocabulary.IRI_ATTRIBUTE.getShortForm())) {
            return getIRI(value);
        } else if (localName.equals(OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE.getShortForm())) {
            return getAbbreviatedIRI(value);
        } else if (localName.equals("URI")) {
            // Legacy
            return getIRI(value);
        }
        throw new OWLXMLParserAttributeNotFoundException(getLineNumber(), getColumnNumber(), OWLXMLVocabulary.IRI_ATTRIBUTE.getShortForm());
    }

    /**
     * @param elementLocalName
     *        elementLocalName
     * @param textContent
     *        textContent
     * @return iri
     * @throws OWLParserException
     *         if an error is raised
     */
    public IRI getIRIFromElement(String elementLocalName, String textContent) throws OWLParserException {
        if (elementLocalName.equals(OWLXMLVocabulary.IRI_ELEMENT.getShortForm())) {
            return handler.getIRI(textContent.trim());
        } else if (elementLocalName.equals(OWLXMLVocabulary.ABBREVIATED_IRI_ELEMENT.getShortForm())) {
            return handler.getAbbreviatedIRI(textContent.trim());
        }
        throw new OWLXMLParserException(elementLocalName + " is not an IRI element", getLineNumber(), getColumnNumber());
    }

    protected OWLOntologyManager getOWLOntologyManager() {
        return handler.getOWLOntologyManager();
    }

    protected OWLOntology getOntology() {
        return handler.getOntology();
    }

    protected OWLDataFactory getOWLDataFactory() {
        return handler.getDataFactory();
    }

    @Override
    public void setParentHandler(OWLElementHandler<?> handler) {
        this.parentHandler = handler;
    }

    protected OWLElementHandler<?> getParentHandler() {
        return parentHandler;
    }

    @Override
    public void attribute(String localName, String value) throws OWLParserException {}

    protected IRI getIRI(String iri) throws OWLParserException {
        return handler.getIRI(iri);
    }

    protected IRI getAbbreviatedIRI(String abbreviatedIRI) throws OWLParserException {
        return handler.getAbbreviatedIRI(abbreviatedIRI);
    }

    protected int getLineNumber() {
        return handler.getLineNumber();
    }

    protected int getColumnNumber() {
        return handler.getColumnNumber();
    }

    @Override
    public void startElement(String name) throws OWLXMLParserException {
        sb = null;
        elementName = name;
    }

    protected String getElementName() {
        return elementName;
    }

    @Override
    public void handleChild(AbstractOWLAxiomElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(AbstractClassExpressionElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(AbstractOWLDataRangeHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLDataPropertyElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLIndividualElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLLiteralElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLAnnotationElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLSubObjectPropertyChainElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLDatatypeFacetRestrictionElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLAnnotationPropertyElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(OWLAnonymousIndividualElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(AbstractIRIElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(SWRLVariableElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(SWRLAtomElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChild(SWRLAtomListElementHandler _handler) throws OWLXMLParserException {}

    @Override
    public void handleChars(char[] chars, int start, int length) {
        if (isTextContentPossible()) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(chars, start, length);
        }
    }

    @Override
    public String getText() {
        if (sb == null) {
            return "";
        }
        return sb.toString();
    }

    @Override
    public boolean isTextContentPossible() {
        return false;
    }
}
