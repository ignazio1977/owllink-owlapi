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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.owllink.builtin.requests.*;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividualSynsets;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividuals;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfObjectPropertySynsets;
import org.semanticweb.owlapi.reasoner.Node;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 03.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkIndividualObjectPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = createSet();
        axioms.add(getDataFactory().getOWLObjectPropertyAssertionAxiom(opp(), getOWLIndividual("i"), getOWLIndividual("j")));
        axioms.add(getDataFactory().getOWLObjectPropertyAssertionAxiom(opp(), getOWLIndividual("i"), getOWLIndividual("k")));
        axioms.add(getDataFactory().getOWLSubObjectPropertyOfAxiom(opp(), opq()));
        axioms.add(getDataFactory().getOWLEquivalentObjectPropertiesAxiom(opp(), opr()));

        return axioms;
    }


    public void testGetObjectPropertiesOfSource() throws Exception {
        GetObjectPropertiesOfSource query = new GetObjectPropertiesOfSource(getKBIRI(), getOWLIndividual("i"));
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertTrue(response.getFlattened().size() == 4);
        assertTrue(response.getFlattened().contains(opp()));
        assertTrue(response.getFlattened().contains(opq()));
        assertTrue(response.getFlattened().contains(opr()));
        assertTrue(response.getFlattened().contains(manager.getOWLDataFactory().getOWLTopObjectProperty()));

        /* Set<Node<OWLObjectProperty>> synsets = CollectionFactory.createSet();
        Synset<OWLObjectProperty> synset = new SynsetImpl<OWLObjectProperty>(opp(), opr());
        synsets.add(synset);
        synset = new SynsetImpl<OWLObjectProperty>(opq());
        synsets.add(synset);
        for (Synset<OWLObjectProperty> set : response) {
            synsets.remove(set);
        }
        assertTrue(synsets.isEmpty());*/
    }

    public void testGetObjectPropertiesOfTarget() throws Exception {
        GetObjectPropertiesOfTarget query = new GetObjectPropertiesOfTarget(getKBIRI(), getOWLIndividual("k"));
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertTrue(response.getFlattened().size() == 4);
        assertTrue(response.getFlattened().contains(opp()));
        assertTrue(response.getFlattened().contains(opq()));
        assertTrue(response.getFlattened().contains(opr()));
        assertTrue(response.getFlattened().contains(manager.getOWLDataFactory().getOWLTopObjectProperty()));
    }

    public void testGetObjectPropertiesBetween() throws Exception {
        GetObjectPropertiesBetween query = new GetObjectPropertiesBetween(getKBIRI(), getOWLIndividual("i"), getOWLIndividual("j"));
        SetOfObjectPropertySynsets response = super.reasoner.answer(query);
        assertTrue(response.getFlattened().size() == 4);
        assertTrue(response.getFlattened().contains(opp()));
        assertTrue(response.getFlattened().contains(opq()));
        assertTrue(response.getFlattened().contains(opr()));
        assertTrue(response.getFlattened().contains(manager.getOWLDataFactory().getOWLTopObjectProperty()));
        for (Node<OWLObjectPropertyExpression> synset : response) {
            if (synset.contains(opr()))
                assertFalse(synset.isSingleton());
            else
                assertTrue(synset.isSingleton());
        }
    }

    public void testAreIndividualsRelated() throws Exception {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLObjectPropertyAssertionAxiom(opp(), getOWLIndividual("i"), getOWLIndividual("j")));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLObjectPropertyAssertionAxiom(opr(), getOWLIndividual("i"), getOWLIndividual("j")));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLObjectPropertyAssertionAxiom(opp(), getOWLIndividual("i"), getOWLIndividual("k")));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLObjectPropertyAssertionAxiom(opq(), getOWLIndividual("i"), getOWLIndividual("k")));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLObjectPropertyAssertionAxiom(opr(), getOWLIndividual("j"), getOWLIndividual("j")));
        falseResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLObjectPropertyAssertionAxiom(opp(), getOWLIndividual("j"), getOWLIndividual("i")));
        falseResponse(reasoner.answer(query));
    }

    public void testGetObjectPropertyTargets() throws Exception {
        GetObjectPropertyTargets query = new GetObjectPropertyTargets(getKBIRI(), getOWLIndividual("i"), opp());
        SetOfIndividualSynsets response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("j"),getOWLIndividual("k")), response.getFlattened());
    }

    public void testGetObjectPropertySources() throws Exception {
        GetObjectPropertySources query = new GetObjectPropertySources(getKBIRI(), getOWLIndividual("j"), opp());
        SetOfIndividualSynsets response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("i")), response.getFlattened());
    }

    public void testGetFlattenedObjectPropertyTargets() throws Exception {
        GetFlattenedObjectPropertyTargets query = new GetFlattenedObjectPropertyTargets(getKBIRI(), getOWLIndividual("i"), opp());
        SetOfIndividuals response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("j"), getOWLIndividual("k")), response);
    }

    public void testGetFlattenedObjectPropertySources() throws Exception {
        GetFlattenedObjectPropertySources query = new GetFlattenedObjectPropertySources(getKBIRI(), getOWLIndividual("j"), opp());
        SetOfIndividuals response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("i")), response);
    }
}
