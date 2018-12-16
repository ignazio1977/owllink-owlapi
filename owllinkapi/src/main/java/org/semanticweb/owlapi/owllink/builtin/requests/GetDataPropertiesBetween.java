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

package org.semanticweb.owlapi.owllink.builtin.requests;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfDataPropertySynsets;

/**
 * Represents a <a href="http://www.owllink.org/owllink-20091116/#IndividualDataPropQueries">GetDataPropertiesBetween</a>
 * request in the OWLlink specification.
 * Author: Olaf Noppens
 * Date: 23.10.2009
 */
public class GetDataPropertiesBetween extends AbstractKBRequest
        <SetOfDataPropertySynsets> {
    final OWLLiteral targetValue;
    final OWLIndividual sourceIndividual;
    final boolean isNegative;

    /** @param kb knowledge base 
     * @param sourceIndividual sourceIndividual
     * @param targetValue targetValue */
    public GetDataPropertiesBetween(IRI kb, OWLIndividual sourceIndividual, OWLLiteral targetValue) {
        super(kb);
        this.sourceIndividual = sourceIndividual;
        this.targetValue = targetValue;
        isNegative = false;
    }

    /** @param kb knowledge base 
     * @param sourceIndividual sourceIndividual 
     * @param targetValue targetValue 
     * @param isNegative isNegative */
    public GetDataPropertiesBetween(IRI kb, OWLIndividual sourceIndividual, OWLLiteral targetValue, boolean isNegative) {
        super(kb);
        this.sourceIndividual = sourceIndividual;
        this.targetValue = targetValue;
        this.isNegative = isNegative;
    }

    /** @return target value */
    public OWLLiteral getTargetValue() {
        return this.targetValue;
    }

    /** @return source individual*/
    public OWLIndividual getSourceIndividual() {
        return this.sourceIndividual;
    }

    /** @return true if negative*/
    public boolean isNegative() {
        return this.isNegative;
    }

    @Override
    public void accept(RequestVisitor visitor) {
        visitor.answer(this);
    }

}
