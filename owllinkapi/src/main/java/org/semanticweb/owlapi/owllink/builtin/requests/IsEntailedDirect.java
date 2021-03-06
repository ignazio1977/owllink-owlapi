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

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;

/**
 * Represents a <a href="http://www.owllink.org/owllink-20091116/#KBEntailmentResponses">IsEntailedDirect</a>
 * request in the OWLlink specification.
 *
 * Author: Olaf Noppens
 * Date: 20.11.2009
 */
public class IsEntailedDirect extends AbstractKBRequest<BooleanResponse> {
    final OWLAxiom axiom;

    /** @param kb knowledge base 
     * @param axiom axiom */
    public IsEntailedDirect(IRI kb, OWLSubClassOfAxiom axiom) {
        super(kb);
        this.axiom = axiom;
    }

    /** @param kb knowledge base 
     * @param axiom axiom */
    public IsEntailedDirect(IRI kb, OWLSubObjectPropertyOfAxiom axiom) {
        super(kb);
        this.axiom = axiom;
    }

    /** @param kb knowledge base 
     * @param axiom axiom */
    public IsEntailedDirect(IRI kb, OWLSubDataPropertyOfAxiom axiom) {
        super(kb);
        this.axiom = axiom;
    }

    /** @param kb knowledge base 
     * @param axiom axiom */
    public IsEntailedDirect(IRI kb, OWLClassAssertionAxiom axiom) {
        super(kb);
        this.axiom = axiom;
    }

    /** @return axiom*/
    public OWLAxiom getAxiom() {
        return axiom;
    }

    /** @return true if subclass*/
    public boolean isOWLSubClassOfAxiom() {
        return this.axiom.isOfType(AxiomType.SUBCLASS_OF);
    }

    /** @return true if sub object property*/
    public boolean isOWLSubObjectPropertyOfAxiom() {
        return this.axiom.isOfType(AxiomType.SUB_OBJECT_PROPERTY);
    }

    /** @return true if sub data property*/
    public boolean isOWLSubDataPropertyOfAxiom() {
        return this.axiom.isOfType(AxiomType.SUB_DATA_PROPERTY);
    }

    /** @return true if class assertion*/
    public boolean isOWLClassAssertionAxiom() {
        return this.axiom.isOfType(AxiomType.CLASS_ASSERTION);
    }

    /** @return as subclass axiom*/
    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        if (isOWLSubClassOfAxiom())
            return (OWLSubClassOfAxiom) this.axiom;
        throw new OWLRuntimeException("axiom cannot be casted to OWLSubClassOfAxiom");
    }

    /** @return as sub object proeprty*/
    public OWLSubObjectPropertyOfAxiom asOWLSubObjectPropertOfAxiom() {
        if (isOWLSubObjectPropertyOfAxiom())
            return (OWLSubObjectPropertyOfAxiom) this.axiom;
        throw new OWLRuntimeException("axiom cannot be casted to OWLSubObjectPropertyOfAxiom");
    }

    /** @return as sub data property*/
    public OWLSubDataPropertyOfAxiom asOWLSubDataPropertOfAxiom() {
        if (isOWLSubDataPropertyOfAxiom())
            return (OWLSubDataPropertyOfAxiom) this.axiom;
        throw new OWLRuntimeException("axiom cannot be casted to OWLSubDataPropertyOfAxiom");
    }

    /** @return as class assertion*/
    public OWLClassAssertionAxiom asOWLClassAssertionAxiom() {
        if (isOWLClassAssertionAxiom())
            return (OWLClassAssertionAxiom) this.axiom;
        throw new OWLRuntimeException("axiom cannot be casted to OWLClassAssertionAxiom");
    }

    @Override
    public void accept(RequestVisitor visitor) {
        visitor.answer(this);
    }
}
