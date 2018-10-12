package org.coode.owlapi.owlxmlparser;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormatFactory;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyInputSourceException;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group, Date:
 *         13-Dec-2006
 */
public class OWLXMLParser extends AbstractOWLParser {

    @Override
    public OWLDocumentFormat parse(IRI documentIRI, OWLOntology ontology) {
        return parse(new IRIDocumentSource(documentIRI), ontology,
            new OWLOntologyLoaderConfiguration());
    }

    @Override
    public OWLDocumentFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology,
        OWLOntologyLoaderConfiguration configuration)
        throws OWLParserException, OWLOntologyChangeException, UnloadableImportException {
        try {
            System.setProperty("entityExpansionLimit", "100000000");
            OWLXMLDocumentFormat format = new OWLXMLDocumentFormat();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",
                false);
            factory.setFeature("http://xml.org/sax/features/validation", false);
            SAXParser parser = factory.newSAXParser();
            InputSource isrc = null;
            try {
                isrc = getInputSource(documentSource, configuration);
                OWLXMLParserHandler handler = new OWLXMLParserHandler(ontology, configuration);
                parser.parse(isrc, handler);
                Map<String, String> prefix2NamespaceMap = handler.getPrefixName2PrefixMap();
                for (String prefix : prefix2NamespaceMap.keySet()) {
                    format.setPrefix(prefix, prefix2NamespaceMap.get(prefix));
                }
            } finally {
                if (isrc != null && isrc.getByteStream() != null) {
                    isrc.getByteStream().close();
                } else if (isrc != null && isrc.getCharacterStream() != null) {
                    isrc.getCharacterStream().close();
                }
            }
            return format;
        } catch (ParserConfigurationException e) {
            // What the hell should be do here? In serious trouble if this
            // happens
            throw new OWLRuntimeException(e);
        } catch (TranslatedOWLParserException e) {
            throw e.getParserException();
        } catch (TranslatedUnloadableImportException e) {
            throw e.getUnloadableImportException();
        } catch (SAXException | OWLOntologyInputSourceException | IOException e) {
            // General exception
            throw new OWLParserException(e);
        }
    }

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new OWLXMLDocumentFormatFactory();
    }
}
