package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.formats.OWLXMLDocumentFormatFactory;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 18-Dec-2006
 */
public class OWLXMLParserFactory implements OWLParserFactory {
    @Override
    public String getDefaultMIMEType() {
        return getSupportedFormat().getDefaultMIMEType();
    }
    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new OWLXMLDocumentFormatFactory();
    }
    @Override
    public OWLParser get() {
        return new OWLXMLParser();
    }

    @Override
    public OWLParser createParser() {
        return new OWLXMLParser();
    }
}
