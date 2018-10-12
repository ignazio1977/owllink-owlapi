package org.coode.owlapi.owlxmlrenderer;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * The Class OWLXMLOntologyStorageException.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 13-Apr-2007
 */
public class OWLXMLOntologyStorageException extends OWLOntologyStorageException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 30406L;

    /**
     * Instantiates a new oWLXML ontology storage exception.
     * 
     * @param message
     *        the message
     */
    public OWLXMLOntologyStorageException(String message) {
        super(message);
    }

    /**
     * Instantiates a new oWLXML ontology storage exception.
     * 
     * @param message
     *        the message
     * @param cause
     *        the cause
     */
    public OWLXMLOntologyStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new oWLXML ontology storage exception.
     * 
     * @param cause
     *        the cause
     */
    public OWLXMLOntologyStorageException(Throwable cause) {
        super(cause);
    }
}
