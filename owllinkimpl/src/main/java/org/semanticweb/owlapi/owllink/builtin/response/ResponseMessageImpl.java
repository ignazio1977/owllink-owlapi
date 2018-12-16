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

package org.semanticweb.owlapi.owllink.builtin.response;

import org.semanticweb.owlapi.owllink.Request;
import org.semanticweb.owlapi.owllink.Response;

import java.util.*;

/**
 * Author: Olaf Noppens
 * Date: 27.10.2009
 */
public class ResponseMessageImpl implements ResponseMessage {
    private Map<Request<?>, Object> responsesByRequests;
    protected List<Request<?>> requests;
    private boolean hasError = false;

    /**
     * @param requests requests 
     */
    public ResponseMessageImpl(Request<?>... requests) {
        this.requests = Collections.unmodifiableList(Arrays.asList(requests));
        this.responsesByRequests = new HashMap<>();
        for (Request<?> request : requests)
            this.responsesByRequests.put(request, null);
    }

    /**
     * @param <R> response type
     * @param request request 
     * @param response response 
     */
    public <R extends Response> void add(Request<R> request, R response) {
        this.responsesByRequests.put(request, response);
    }

    /**
     * @param response response 
     * @param index index 
     */
    public void add(Response response, int index) {
        this.responsesByRequests.put(this.requests.get(index), response);
    }

    /**
     * @param exception exception 
     * @param index index 
     */
    public void add(OWLlinkErrorResponseException exception, int index) {
        this.responsesByRequests.put(this.requests.get(index), exception);
        this.hasError = true;
    }

    @Override
    public Iterator<Response> iterator() {
        return new Iterator<Response>() {
            Iterator<Request<?>> internal = requests.iterator();

            @Override
            public boolean hasNext() {
                return this.internal.hasNext();
            }

            @Override
            public Response next() {
                Request<?> request = this.internal.next();
                return _getResponse(request);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public boolean hasError() {
        return this.hasError;
    }

    /**
     * @param request request 
     * @param exception exception 
     */
    public void add(Request<?> request, OWLlinkErrorResponseException exception) {
        this.responsesByRequests.put(request, exception);
        this.hasError = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends Response> R getResponse(Request<R> request) throws OWLlinkErrorResponseException {
        return (R) this._getResponse(request);
    }

    protected Response _getResponse(Request<?> request) throws OWLlinkErrorResponseException {
        Object response = this.responsesByRequests.get(request);
        if (response instanceof Response) {
            return (Response) response;
        } else if (response instanceof OWLlinkErrorResponseException) {
            throw (OWLlinkErrorResponseException) response;
        }
        return null;
    }

    @Override
    public boolean hasErrorResponse(Request<?> request) {
        return responsesByRequests.get(request) instanceof OWLlinkErrorResponseException;
    }

    @Override
    public boolean isErrorResponse(int index) {
        return hasErrorResponse(this.requests.get(index));
    }

    @Override
    public OWLlinkErrorResponseException getError(Request<?> request) {
        Object error = this.responsesByRequests.get(request);
        if (error instanceof OWLlinkErrorResponseException)
            return (OWLlinkErrorResponseException) error;
        return null;
    }

    @Override
    public Response get(int index) throws OWLlinkErrorResponseException {
        Object response = this.responsesByRequests.get(this.requests.get(index));
        if (response instanceof Response) {
            return (Response) response;
        } else if (response instanceof OWLlinkErrorResponseException) {
            throw (OWLlinkErrorResponseException) response;
        }
        return null;
    }

    @Override
    public OWLlinkErrorResponseException getError(int index) {
        return this.getError(this.requests.get(index));
    }
}
