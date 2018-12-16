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

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.owllink.builtin.requests.IsEntailed;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkIsObjectPropertyIsXTestCase extends AbstractOWLlinkAxiomsTestCase {
    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axiom = CollectionFactory.createSet();
        axiom.add(getDataFactory().getOWLFunctionalObjectPropertyAxiom(opa()));
        axiom.add(getDataFactory().getOWLInverseFunctionalObjectPropertyAxiom(opb()));
        axiom.add(getDataFactory().getOWLReflexiveObjectPropertyAxiom(opc()));
        axiom.add(getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opd()));
        axiom.add(getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opE()));
        axiom.add(getDataFactory().getOWLTransitiveObjectPropertyAxiom(opF()));

        return axiom;
    }

    public void testIsFunctional() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLFunctionalObjectPropertyAxiom
                (opa()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLFunctionalObjectPropertyAxiom
                (opb()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsFunctionalViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLFunctionalObjectPropertyAxiom
                (opa());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLFunctionalObjectPropertyAxiom
                (opb());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsInverseFunctional() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().
                getOWLInverseFunctionalObjectPropertyAxiom(opb()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().
                getOWLInverseFunctionalObjectPropertyAxiom(opa()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsInverseFunctionalViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().
                getOWLInverseFunctionalObjectPropertyAxiom(opb());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().
                getOWLInverseFunctionalObjectPropertyAxiom(opa());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsReflexive() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLReflexiveObjectPropertyAxiom(opc()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLReflexiveObjectPropertyAxiom(opd()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsReflexiveViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLReflexiveObjectPropertyAxiom(opc());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLReflexiveObjectPropertyAxiom(opd());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsIrreflexive() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opd()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opa()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsIrreflexiveViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opd());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(opa());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsAsymmetric() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opE()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opF()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsAsymmetricViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opE());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLAsymmetricObjectPropertyAxiom(opF());
        assertFalse(reasoner.isEntailed(axiom));
    }

    public void testIsTranstive() {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLTransitiveObjectPropertyAxiom(opF()));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLTransitiveObjectPropertyAxiom(opE()));
        falseResponse(reasoner.answer(query));
    }

    public void testIsTranstiveViaOWLReasoner() {
        OWLAxiom axiom = getDataFactory().getOWLTransitiveObjectPropertyAxiom(opF());
        assertTrue(reasoner.isEntailed(axiom));

        axiom = getDataFactory().getOWLTransitiveObjectPropertyAxiom(opE());
        assertFalse(reasoner.isEntailed(axiom));
    }
}
