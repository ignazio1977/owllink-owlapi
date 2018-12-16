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

package org.semanticweb.owlapi.owllink;

import org.semanticweb.owlapi.owllink.parser.OWLlinkElementHandlerFactory;
import org.semanticweb.owlapi.owllink.renderer.OWLlinkRequestRendererFactory;
import org.semanticweb.owlapi.owllink.retraction.OWLlinkXMLRetractionRequestRendererFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Olaf Noppens
 * Date: 28.04.2010
 */
public class OWLlinkXMLFactoryRegistry {
    private static OWLlinkXMLFactoryRegistry instance = new OWLlinkXMLFactoryRegistry();

    final List<OWLlinkRequestRendererFactory> rendererFactories = new ArrayList<>();
    final List<OWLlinkElementHandlerFactory> parserFactories = new ArrayList<>();

    private OWLlinkXMLFactoryRegistry() {
        register(new OWLlinkXMLRetractionRequestRendererFactory());
    }

    /** @return instance */
    public synchronized static OWLlinkXMLFactoryRegistry getInstance() {
        return instance;
    }

    /** @return request renderers */
    public synchronized List<OWLlinkRequestRendererFactory> getRequestRendererFactories() {
        return Collections.unmodifiableList(rendererFactories);
    }

    /** @return element factories */
    public synchronized List<OWLlinkElementHandlerFactory> getParserFactories() {
        return Collections.unmodifiableList(parserFactories);
    }

    /** @param factory factory */
    public synchronized void register(OWLlinkRequestRendererFactory factory) {
        if (!rendererFactories.contains(factory))
            rendererFactories.add(factory);
    }

    /** @param factory factory */
    public synchronized void register(OWLlinkElementHandlerFactory factory) {
        if (!parserFactories.contains(factory))
            parserFactories.add(factory);
    }

    /** @param factory factory */
    public synchronized void unregister(OWLlinkRequestRendererFactory factory) {
        rendererFactories.remove(factory);
    }

    /** @param factory factory */
    public synchronized void unregister(OWLlinkElementHandlerFactory factory) {
        parserFactories.remove(factory);
    }
}
