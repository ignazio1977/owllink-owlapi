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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllClasses;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllDataProperties;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllIndividuals;
import org.semanticweb.owlapi.owllink.builtin.requests.GetAllObjectProperties;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfClasses;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfDataProperties;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividuals;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfObjectProperties;
import org.semanticweb.owlapi.util.CollectionFactory;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 02.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkGetEntitiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = CollectionFactory.createSet();
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(a(), b()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(a(), c()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(b(), d()));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(getOWLClass("E"), getOWLClass("F")));
        axioms.add(getDataFactory().getOWLSubClassOfAxiom(getOWLClass("E"), getDataFactory().getOWLObjectExactCardinality(1, opp())));

        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(getOWLObjectProperty("oA"), getOWLObjectProperty("oB")));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(getOWLObjectProperty("oB"), getOWLObjectProperty("oC")));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(getOWLObjectProperty("oC"), getOWLObjectProperty("oD")));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(getOWLObjectProperty("oD"), getOWLObjectProperty("oE")));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(getOWLObjectProperty("oE"), getOWLObjectProperty("oF")));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(getOWLObjectProperty("oG"), getOWLObjectProperty("oH")));

        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(getOWLDataProperty("dA"), getOWLDataProperty("dB")));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(getOWLDataProperty("dB"), getOWLDataProperty("dC")));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(getOWLDataProperty("dC"), getOWLDataProperty("dD")));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(getOWLDataProperty("dD"), getOWLDataProperty("dE")));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(getOWLDataProperty("dE"), getOWLDataProperty("dF")));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(getOWLDataProperty("dG"), getOWLDataProperty("dH")));

        axioms.add(getDataFactory().getOWLClassAssertionAxiom(a(), getOWLIndividual("i1")));
        axioms.add(getDataFactory().getOWLClassAssertionAxiom(b(), getOWLIndividual("i2")));
        axioms.add(getDataFactory().getOWLClassAssertionAxiom(getOWLClass("X"), getOWLIndividual("i3")));
        axioms.add(getDataFactory().getOWLClassAssertionAxiom(getOWLClass("Y"), getOWLIndividual("i4")));

        return axioms;
    }

    public void testGetAllClasses() throws Exception {
        GetAllClasses query = new GetAllClasses(getKBIRI());
        SetOfClasses answer = reasoner.answer(query);
        assertEquals(asUnorderedSet(getOntology().classesInSignature()), answer);
    }

    public void testGetAllDataProperties() throws Exception {
        GetAllDataProperties query = new GetAllDataProperties(getKBIRI());
        SetOfDataProperties answer = reasoner.answer(query);
        assertEquals(asUnorderedSet(getOntology().dataPropertiesInSignature()), answer);
    }

    public void testGetAllObjectProperties() throws Exception {
        GetAllObjectProperties query = new GetAllObjectProperties(getKBIRI());
        SetOfObjectProperties answer = reasoner.answer(query);
        assertEquals(asUnorderedSet(getOntology().objectPropertiesInSignature()), answer);
    }

    public void testGetAllIndividuals() throws Exception {
        GetAllIndividuals query = new GetAllIndividuals(getKBIRI());
        SetOfIndividuals answer = reasoner.answer(query);
        Set<OWLIndividual> individuals = CollectionFactory.createSet();
        add(individuals, getOntology().individualsInSignature());
        add(individuals, getOntology().referencedAnonymousIndividuals());
        assertEquals(individuals, answer);
    }
}
