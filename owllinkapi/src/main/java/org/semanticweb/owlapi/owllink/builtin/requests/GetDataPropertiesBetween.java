/*
 * This file is part of the OWLlink API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, derivo GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, derivo GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
