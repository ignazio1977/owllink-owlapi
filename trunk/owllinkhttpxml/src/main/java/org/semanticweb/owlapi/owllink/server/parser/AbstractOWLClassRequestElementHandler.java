/*
 * Copyright (C) 2009, Ulm University
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

package org.semanticweb.owlapi.owllink.server.parser;

import org.coode.owlapi.owlxmlparser.AbstractClassExpressionElementHandler;
import org.coode.owlapi.owlxmlparser.OWLXMLParserException;
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.owllink.KBRequest;

/**
 * Author: Olaf Noppens
 * Date: 25.10.2009
 */
public abstract class AbstractOWLClassRequestElementHandler<R extends KBRequest> extends
        AbstractOWLlinkObjectRequestElementHandler<R, OWLClassExpression> {

    public AbstractOWLClassRequestElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        this.o = handler.getOWLObject();
    }
}
