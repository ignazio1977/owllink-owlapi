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

import org.coode.owlapi.owlxmlparser.OWLXMLParserException;
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * Created by IntelliJ IDEA.
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
public class OWLlinkDataPropertySubDataPropertiesPairElementHandler extends AbstractOWLlinkPairElementHandler<OWLDataProperty> {

    public OWLlinkDataPropertySubDataPropertiesPairElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    public void handleChild(OWLlinkDataPropertySynsetElementHandler handler) throws OWLXMLParserException {
        super.superSynset = handler.getOWLLinkObject();
    }

    public void handleChild(OWLlinkSubDataPropertySynsetsElementHandler handler) throws OWLXMLParserException {
        super.subSetOfSynset = handler.getOWLLinkObject();
    }

    public void endElement() throws OWLXMLParserException {
        getParentHandler().handleChild(this);
    }
}