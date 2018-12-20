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
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.owllink.builtin.requests.*;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfDataPropertySynsets;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividualSynsets;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfIndividuals;
import org.semanticweb.owlapi.owllink.builtin.response.SetOfLiterals;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

/**
 * Author: Olaf Noppens
 * Date: 03.11.2009
 */
@SuppressWarnings("javadoc")
public class OWLlinkIndividualDataPropertiesTestCase extends AbstractOWLlinkAxiomsTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = createSet();
        axioms.add(getDataFactory().getOWLDataPropertyAssertionAxiom(dpp(), getOWLIndividual("i"), getLiteral(1)));
        axioms.add(getDataFactory().getOWLDataPropertyAssertionAxiom(dpp(), getOWLIndividual("i"), getLiteral(2)));
        axioms.add(getDataFactory().getOWLSubDataPropertyOfAxiom(dpp(), dpq()));
        axioms.add(getDataFactory().getOWLEquivalentDataPropertiesAxiom(dpp(), dpr()));

        return axioms;
    }

    public void testGetDataPropertiesOfSource() throws Exception {
        GetDataPropertiesOfSource query = new GetDataPropertiesOfSource(getKBIRI(), getOWLIndividual("i"));
        SetOfDataPropertySynsets response = super.reasoner.answer(query);
        Set<OWLDataProperty> expected = set(dpp(), dpq(), dpr(), topProperty()); 
        assertEquals(expected, asUnorderedSet(response.entities()));
    }

    public void testGetDataPropertiesOfTarget() throws Exception {
        GetDataPropertiesOfLiteral query = new GetDataPropertiesOfLiteral(getKBIRI(), getLiteral(2));
        SetOfDataPropertySynsets response = super.reasoner.answer(query);
        Set<OWLDataProperty> expected = set(dpp(), dpq(), dpr(), topProperty()); 
        assertEquals(expected, asUnorderedSet(response.entities()));
    }

    public void testGetDataPropertiesBetween() throws Exception {
        GetDataPropertiesBetween query = new GetDataPropertiesBetween(getKBIRI(), getOWLIndividual("i"), getLiteral(1));
        SetOfDataPropertySynsets response = super.reasoner.answer(query);
        assertEquals(set(dpp(),dpq(),dpr(),topProperty()), asUnorderedSet(response.entities()));
    }

    protected OWLDataProperty topProperty() {
        return manager.getOWLDataFactory().getOWLTopDataProperty();
    }

    public void testIsIndividualRelatedWithLiteral() throws Exception {
        IsEntailed query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDataPropertyAssertionAxiom(dpp(), getOWLIndividual("i"), getLiteral(1)));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDataPropertyAssertionAxiom(dpr(), getOWLIndividual("i"), getLiteral(1)));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDataPropertyAssertionAxiom(dpp(), getOWLIndividual("i"), getLiteral(2)));
        trueResponse(reasoner.answer(query));

        query = new IsEntailed(getKBIRI(), getDataFactory().getOWLDataPropertyAssertionAxiom(dpq(), getOWLIndividual("i"), getLiteral(2)));
        trueResponse(reasoner.answer(query));
    }

    public void testGetDataPropertyTargets() throws Exception {
        GetDataPropertyTargets query = new GetDataPropertyTargets(getKBIRI(), getOWLIndividual("i"), dpp());
        SetOfLiterals response = super.reasoner.answer(query);
        assertEquals(set(getLiteral(1), getLiteral(2)), response);
    }

    public void testGetDataPropertySources() throws Exception {
        GetDataPropertySources query = new GetDataPropertySources(getKBIRI(), dpp(), getLiteral(1));
        SetOfIndividualSynsets response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("i")), response.getFlattened());
    }

    public void testGetFlattenedDataPropertySources() throws Exception {
        GetFlattenedDataPropertySources query = new GetFlattenedDataPropertySources(getKBIRI(), dpp(), getLiteral(1));
        SetOfIndividuals response = super.reasoner.answer(query);
        assertEquals(set(getOWLIndividual("i")), response);
    }
}
