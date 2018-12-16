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
