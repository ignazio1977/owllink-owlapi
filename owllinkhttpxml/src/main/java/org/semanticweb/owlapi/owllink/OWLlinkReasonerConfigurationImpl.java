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

import org.semanticweb.owlapi.reasoner.*;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: Olaf Noppens
 * Date: 19.02.2010
 */
public class OWLlinkReasonerConfigurationImpl extends SimpleConfiguration {
    private static URL defaultURL;
    URL reasonerURL;

    static {
        try {
            defaultURL = new URL("http://localhost:8080");
        } catch (MalformedURLException e) {
            // dead code
            e.printStackTrace();
        }
    }

    /**
     * @param progressMonitor progressMonitor 
     * @param policy policy 
     * @param timeout timeout 
     * @param individualNodeSetPolicy individualNodeSetPolicy 
     * @param reasonerURL reasonerURL 
     */
    public OWLlinkReasonerConfigurationImpl(ReasonerProgressMonitor progressMonitor,FreshEntityPolicy policy, long timeout, IndividualNodeSetPolicy individualNodeSetPolicy, URL reasonerURL) {
        super(progressMonitor, policy, timeout, individualNodeSetPolicy);
        this.reasonerURL = reasonerURL;
    }

    /**
     * @param progressMonitor progressMonitor 
     * @param policy policy 
     * @param timeout timeout 
     * @param individualNodeSetPolicy individualNodeSetPolicy 
     */
    public OWLlinkReasonerConfigurationImpl(ReasonerProgressMonitor progressMonitor,FreshEntityPolicy policy, long timeout, IndividualNodeSetPolicy individualNodeSetPolicy) {
        this(progressMonitor, policy, timeout, individualNodeSetPolicy, defaultURL);
    }

    /**
     * @param progressMonitor progressMonitor 
     * @param reasonerURL reasonerURL 
     * @param individualNodeSetPolicy individualNodeSetPolicy 
     */
    public OWLlinkReasonerConfigurationImpl(ReasonerProgressMonitor progressMonitor, URL reasonerURL, IndividualNodeSetPolicy individualNodeSetPolicy) {
        super(progressMonitor, FreshEntityPolicy.DISALLOW, Long.MAX_VALUE, individualNodeSetPolicy);
        this.reasonerURL = reasonerURL;
    }

    /**
     * @param progressMonitor progressMonitor 
     * @param policy policy 
     */
    public OWLlinkReasonerConfigurationImpl(ReasonerProgressMonitor progressMonitor, IndividualNodeSetPolicy policy) {
        this(progressMonitor, defaultURL, policy);
    }

    /**
     * @param reasonerURL reasonerURL 
     * @param policy policy 
     */
    public OWLlinkReasonerConfigurationImpl(URL reasonerURL, IndividualNodeSetPolicy policy) {
        this(new NullReasonerProgressMonitor(), reasonerURL, policy);
    }

    /**
     * @param policy policy 
     */
    public OWLlinkReasonerConfigurationImpl(IndividualNodeSetPolicy policy) {
        this(defaultURL, policy);
    }

     /**
     * @param reasonerURL reasonerURL 
     */
    public OWLlinkReasonerConfigurationImpl(URL reasonerURL) {
        this(reasonerURL, IndividualNodeSetPolicy.BY_SAME_AS);
    }

    /**
     * Same as setting.
     */
    public OWLlinkReasonerConfigurationImpl() {
        this(IndividualNodeSetPolicy.BY_SAME_AS);
    }

    /** @return reasoner url */
    public URL getReasonerURL() {
        return this.reasonerURL;
    }
}
