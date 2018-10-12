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
import org.semanticweb.owlapi.owllink.builtin.requests.IsClassSatisfiable;
import org.semanticweb.owlapi.owllink.builtin.response.BooleanResponse;
import org.semanticweb.owlapi.util.CollectionFactory;

import java.util.Set;

/**
 * @author Olaf Noppens
 */
public class OWLlinkIsClassSatisfiableTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(a(), b()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(c(), getDataFactory().getOWLNothing()));
        axioms.add(getDataFactory().getOWLEquivalentClassesAxiom(d(), getDataFactory().getOWLNothing()));
        axioms.add(getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opP(), opS()));
        return axioms;
    }

    public void testIsClassSatisfiable() throws Exception {
        IsClassSatisfiable query = new IsClassSatisfiable(getKBIRI(), a());
        BooleanResponse response = super.reasoner.answer(query);
        assertTrue(response.getResult());
        query = new IsClassSatisfiable(getKBIRI(), b());
        response = super.reasoner.answer(query);
        assertTrue(response.getResult());
        query = new IsClassSatisfiable(getKBIRI(), c());
        response = super.reasoner.answer(query);
        assertFalse(response.getResult());
        query = new IsClassSatisfiable(getKBIRI(), d());
        response = super.reasoner.answer(query);
        assertFalse(response.getResult());
        query = new IsClassSatisfiable(getKBIRI(), getDataFactory().getOWLNothing());
        response = super.reasoner.answer(query);
        assertFalse(response.getResult());
        query = new IsClassSatisfiable(getKBIRI(), getDataFactory().getOWLObjectIntersectionOf(getDataFactory().getOWLObjectExactCardinality(0, opP()), getDataFactory().getOWLObjectExactCardinality(1, opP())));
        response = super.reasoner.answer(query);
        assertFalse(response.getResult());
    }
}
