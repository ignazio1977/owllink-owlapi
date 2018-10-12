/*
 * Copyright (C) 2010, Ulm University
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.semanticweb.owlapi.owllink.parser;

import org.coode.owlapi.owlxmlparser.OWLElementHandler;
import org.coode.owlapi.owlxmlparser.OWLElementHandlerFactory;
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.owllink.OWLlinkXMLVocabulary;
import org.semanticweb.owlapi.owllink.PrefixManagerProvider;
import org.semanticweb.owlapi.owllink.Request;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handler for OWLlink Responses.
 * <p/>
 * Here we also handle the prefix name mapping for abbreviated IRIs.
 */
public class OWLlinkXMLParserHandler extends MyOWLXMLParserHandler {

    protected Map<String, OWLlinkElementHandlerFactory> owllinkHandlerMap;
    protected OWLlinkResponseMessageElementHandler responseMessageHandler = new OWLlinkResponseMessageElementHandler(this);
    protected PrefixManagerProvider prov;
    Request[] requests;

    public OWLlinkXMLParserHandler(OWLOntologyManager owlOntologyManager, PrefixManagerProvider prov, Request[] requests, OWLOntology ontology) {
        this(owlOntologyManager, prov, requests, ontology, null);
    }

    public OWLlinkXMLParserHandler(OWLOntologyManager owlOntologyManager, PrefixManagerProvider provider, Request[] requests, OWLOntology ontology, OWLElementHandler topHandler) {
        super(owlOntologyManager, ontology, topHandler);
        this.owllinkHandlerMap = new HashMap<>();
        this.prov = provider;
        this.requests = requests;


        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DESCRIPTION) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkDescriptionElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PublicKB) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkPublicKBElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SUPPORTEDEXTENSION) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSupportedExtensionElemenetHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PROPERTY) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkPropertyElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SETTING) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSettingElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.LITERAL) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkLiteralElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.ONEOF) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkOneOfElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.LIST) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkListElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATATYPE) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkDatatypeElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PROTOCOLVERSION) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkProtocolVersionElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.REASONERVERSION) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkReasonerVersionElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PREFIXES) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkPrefixesElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.KB_RESPONSE) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkKBElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OK) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkOKElementHandler(handler);
            }
        });


        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.BOOLEAN_RESPONSE) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkBooleanResponseElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.UNKNOWN_RESPONSE) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkUnknownResponseElementHandler(handler);
            }
        });


        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_ANNOTATIONPROPERTIES) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfAnnotationPropertiesElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_CLASSES) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfClassesElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_DATAPROPERTIES) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfDataPropertiesElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_DATATYPES) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfDatatypesElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_INDIVIDUALS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfIndividualsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_OBJECTPROPERTIES) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfObjectPropertiesElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.String_RESPONSE) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkStringResponseElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_CLASS_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfClassSynsetsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASSES) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkClassesElementHandler(handler);
            }
        });


        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASS_SYNSET) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkClassSynsetElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASS_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkClassSynsetsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASS_HIERARCHY) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkClassHierarchyElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.CLASS_SUBCLASSESPAIR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkClassSubClassesPairElementHandler(handler);
            }
        });


        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SUBCLASS_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSubClassSynsetsElementHandler(handler);
            }
        });


        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_OBJECTPROPERTY_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfObjectPropertySynsetsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_SYNSET) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkObjectPropertySynsetElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkObjectPropertySynsetsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_HIERARCHY) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkObjectPropertyHierarchyElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_SUBOBJECTPROPERTIESPAIR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkObjectPropertySubPropertiesPairElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.OBJECTPROPERTY_SUBOBJECTPROPERTIESPAIR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkObjectPropertySubPropertiesPairElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SUBOBJECTPROPERTY_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSubObjectPropertySynsetsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_DATAPROPERTY_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfDataPropertySynsetsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SYNSET) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkDataPropertySynsetElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkDataPropertySynsetsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SYNONYMS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkDataPropertySynonymsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_HIERARCHY) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkDataPropertyHierarchyElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SUBDATAPROPERTIESPAIR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkDataPropertySubDataPropertiesPairElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.DATAPROPERTY_SUBDATAPROPERTIESPAIR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkDataPropertySubDataPropertiesPairElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SUBDATAPROPERTY_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSubDataPropertySynsetsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_INDIVIDUALS_SYNSETS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfIndividualSynsetsElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.INDIVIDUAL_SYNSET) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkIndividualSynsetElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.INDIVIDUAL_SYNONYMS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkIndividualSynonymsElementHandler(handler);
            }
        });
        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SET_OF_LITERALS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSetOfLiteralsElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.RESPONSE_MESSAGE) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return responseMessageHandler;
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.ERROR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkErrorResponseElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.KBERROR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkKBErrorElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.NOTSUPPORTEDDATATYPEERROR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkNotSupportedDatatypeErrorElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PROFILEVIOLATIONERROR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkProfileViolationResponseErrorExceptionElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SEMANTIC_ERROR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSemanticErrorElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SYNTAX_ERROR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSyntaxErrorElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.UNSATISFIABLEKBERROR) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkUnsatisfiableKBErrorElementHandler(handler);
            }
        });


        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PREFIX) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkPrefixElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.PREFIXES) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkPrefixesElementHandler(handler);
            }
        });

        addFactory(new AbstractOWLlinkElementHandlerFactory(OWLlinkXMLVocabulary.SETTINGS) {
            @Override
            public OWLlinkElementHandler createHandler(OWLXMLParserHandler handler) {
                return new OWLlinkSettingsElementHandler(handler);
            }
        });
    }

    public void addFactory(OWLlinkElementHandlerFactory factory, String... legacyElementNames) {
        this.owllinkHandlerMap.put(factory.getElementName(), factory);
        for (String elementName : legacyElementNames) {
            this.owllinkHandlerMap.put(elementName, factory);
        }
    }

    public void addFactories(Collection<OWLlinkElementHandlerFactory> factories) {
        for (OWLlinkElementHandlerFactory factory : factories)
            this.owllinkHandlerMap.put(factory.getElementName(), factory);
    }


    public Request getRequest(int index) {
        return this.requests[index];
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            processXMLBase(attributes);
            if (Namespaces.OWL2.toString().equals(uri) || Namespaces.OWL.toString().equals(uri) || Namespaces.OWL11XML.toString().equals(uri)) {
                super.startElement(uri, localName, qName, attributes);
            } else {
                OWLElementHandlerFactory handlerFactory = owllinkHandlerMap.get(localName);
                if (handlerFactory != null) {
                    OWLElementHandler handler = handlerFactory.createHandler(this);
                    if (!handlerStack.isEmpty()) {
                        OWLElementHandler topElement = handlerStack.get(0);
                        handler.setParentHandler(topElement);
                    }
                    handlerStack.add(0, handler);
                    handler.startElement(localName);

                    for (int i = 0; i < attributes.getLength(); i++) {
                        handler.attribute(attributes.getLocalName(i), attributes.getValue(i));
                    }
                }
            }
        }
        catch (OWLRuntimeException e) {
            throw new SAXException(e.getMessage() + "(Current element " + localName + ")", e);
        }
    }

    public List<Object> getResponses() {
        return responseMessageHandler.getOWLLinkObject();
    }

}
