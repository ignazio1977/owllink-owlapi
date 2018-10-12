package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 14-Dec-2006
 */
public abstract class AbstractElementHandlerFactory implements
        OWLElementHandlerFactory {

    private String elementName;

    /**
     * @param v
     *        vocabulary
     */
    public AbstractElementHandlerFactory(OWLXMLVocabulary v) {
        elementName = v.getShortForm();
    }

    protected AbstractElementHandlerFactory(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public String getElementName() {
        return elementName;
    }
}
