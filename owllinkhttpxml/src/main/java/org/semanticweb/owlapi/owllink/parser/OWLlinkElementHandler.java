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
import org.coode.owlapi.owlxmlparser.OWLXMLParserException;

/**
 * @param <O> object type
 */
public interface OWLlinkElementHandler<O> extends OWLElementHandler<O> {

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkElementHandler<?> handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkResponseElementHandler<?> handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkErrorElementHandler<?> handler) throws OWLXMLParserException;

    //here are then the built-in element handler, for all other use the first 3 methods.
    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkBooleanResponseElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkConfigurationElementHandler<?> handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkPropertyElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkSettingElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkDataRangeElementHandler<?> handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkLiteralElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkPrefixElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkProtocolVersionElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkReasonerVersionElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkPublicKBElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkSupportedExtensionElemenetHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkClassSynsetElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkObjectPropertySynsetElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkDataPropertySynsetElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkIndividualSynsetElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkClassSubClassesPairElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkObjectPropertySubPropertiesPairElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkDataPropertySubDataPropertiesPairElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkSubClassSynsetsElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkSubObjectPropertySynsetsElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkSubDataPropertySynsetsElementHandler handler) throws OWLXMLParserException;

    /** @param handler handler
     * @throws OWLXMLParserException parsing issue */
    void handleChild(OWLlinkResponseMessageElementHandler handler) throws OWLXMLParserException;

    /** @return object
     * @throws OWLXMLParserException parsing issue */
    O getOWLLinkObject() throws OWLXMLParserException;
}
