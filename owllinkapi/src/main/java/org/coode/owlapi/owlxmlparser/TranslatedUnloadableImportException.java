package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.UnloadableImportException;
import org.xml.sax.SAXException;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 07-Dec-2009
 */
public class TranslatedUnloadableImportException extends SAXException {

    private static final long serialVersionUID = 30406L;
    private UnloadableImportException unloadableImportException;

    /**
     * @param e
     *        cause
     */
    public TranslatedUnloadableImportException(UnloadableImportException e) {
        super(e);
        unloadableImportException = e;
    }

    /** @return cause */
    public UnloadableImportException getUnloadableImportException() {
        return unloadableImportException;
    }
}
