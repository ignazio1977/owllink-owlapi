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

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.owllink.builtin.requests.*;
import org.semanticweb.owlapi.owllink.builtin.response.*;
import org.semanticweb.owlapi.owllink.retraction.RetractRequest;
import org.semanticweb.owlapi.owllink.server.response.ErrorResponse;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.reasoner.impl.*;
import org.semanticweb.owlapi.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code OWLlinkHTTPXMLReasoner} is an implementation of {@code OWLlinkReasoner} that uses XML over
 * HTTP as binding protocol. <p>
 * It behaves as a normal OWLReasoner, i.e., changes on the root ontology will be transfered to the OWLlinkServer
 * via OWLlink. For that a knowledge base (see {@link #getDefaultKB}) will be created that contains all
 * axioms of the root ontology and its import closure. Calling methods of OWLReasoner will always be executed
 * with respect to that OWLlink knowledge base. In addition, additional knowledge bases can be created directly
 * over OWLlink (CreateKB requests) and all OWLlink requests (either on the knowledge base related to the
 * root ontolgoy or on any other knowledge base managed by the OWLlink server) are supported.
 *
 * @author Olaf Noppens
 */
public class OWLlinkHTTPXMLReasoner extends OWLReasonerBase implements OWLlinkReasoner {

    protected static Logger LOGGER = LoggerFactory.getLogger(OWLlinkHTTPXMLReasoner.class);
    
    protected IRI defaultKnowledgeBase;
    protected PrefixManagerProvider prov = new DefaultPrefixManagerProvider();
    Description description;
    HTTPSessionImpl session;

    /**
     * @param rootOntology rootOntology 
     * @param configuration configuration 
     * @param bufferingMode bufferingMode 
     */
    public OWLlinkHTTPXMLReasoner(OWLOntology rootOntology, OWLlinkReasonerConfigurationImpl configuration, BufferingMode bufferingMode) {
        super(rootOntology, configuration, bufferingMode);
        session = new HTTPSessionImpl(rootOntology.getOWLOntologyManager(), configuration.getReasonerURL(), prov);
        createDefaultKB();
        this.description = getReasonerInfo();
        flush();
        Set<OWLAxiom> axioms = new HashSet<>(getReasonerAxioms());
        if (axioms.size() > 0) {
            Tell tell = new Tell(defaultKnowledgeBase, axioms);
            performRequestOWLAPI(tell);
            try {
                isConsistent();
            } catch (InconsistentOntologyException e) {
                throw e;
            } catch (Exception e) {
                LOGGER.error("Error checking consistency", e);
            }
        }
    }

    @Override
    public String getReasonerName() {
        return this.description.getName();
    }

    @Override
    public Version getReasonerVersion() {
        return new org.semanticweb.owlapi.util.Version(description.getReasonerVersion().getMajor(),
                description.getReasonerVersion().getMinor(), 0, 0);
    }

    @Override
    public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
        return true;
    }

    @Override
    public void interrupt() {
    }

    /** @return default kb iri */
    public IRI getDefaultKB() {
        return this.defaultKnowledgeBase;
    }

    @Override
    public <R extends Response> R answer(Request<R> request) {
        return performRequest(request);
    }

    @Override
    public ResponseMessage answer(Request<?>... request) {
        return performRequests(request);
    }

    @Override
    protected void handleChanges(Set<OWLAxiom> addAxioms, Set<OWLAxiom> removeAxioms) {
        if (removeAxioms.isEmpty()) {
            Tell tell = new Tell(defaultKnowledgeBase, addAxioms);
            performRequest(tell);
        } else {
            if (supportsRetraction()) {
                RetractRequest retraction = new RetractRequest(defaultKnowledgeBase, removeAxioms);
                performRequest(retraction);
                if (!addAxioms.isEmpty()) {
                    Tell tell = new Tell(defaultKnowledgeBase, addAxioms);
                    performRequests(tell);
                }

            } else {
                ReleaseKB release = new ReleaseKB(defaultKnowledgeBase);
                performRequest(release);
                CreateKB createKB = new CreateKB(defaultKnowledgeBase);
                performRequest(createKB);
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.addAll(getReasonerAxioms());
                if (axioms.size() > 0) {
                    Tell tell = new Tell(defaultKnowledgeBase, axioms);
                    performRequest(tell);
                }
            }
        }
    }

    private boolean supportsRetraction() {
        return this.description.getSupportedExtensions().contains(RetractRequest.EXTENSION_IRI);
    }

    @Override
    public boolean isConsistent() throws ReasonerInterruptedException, TimeOutException {
        IsKBSatisfiable satisfiable = new IsKBSatisfiable(getDefaultKB());
        BooleanResponse response = performRequestOWLAPI(satisfiable);
        return response.getResult().booleanValue();
    }

    @Override
    public boolean isSatisfiable(OWLClassExpression classExpression) throws ReasonerInterruptedException, TimeOutException, ClassExpressionNotInProfileException, FreshEntitiesException, InconsistentOntologyException {
        IsClassSatisfiable query = new IsClassSatisfiable(defaultKnowledgeBase, classExpression);
        return performRequestOWLAPI(query).getResult().booleanValue();
    }

    @Override
    public boolean isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, FreshEntitiesException {
        IsEntailed query = new IsEntailed(defaultKnowledgeBase, axiom);
        return performRequestOWLAPI(query).getResult().booleanValue();
    }

    @Override
    public boolean isEntailed(Set<? extends OWLAxiom> axioms) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, FreshEntitiesException {
        IsEntailed[] queries = new IsEntailed[axioms.size()];
        int i = 0;
        for (OWLAxiom axiom : axioms) {
            queries[i++] = new IsEntailed(defaultKnowledgeBase, axiom);
        }
        ResponseMessage message = performRequests(queries);
        for (int j = 0; j < queries.length; j++) {
            if (message.get(j) instanceof BooleanResponse) {
                BooleanResponse answer = (BooleanResponse) message.get(j);
                if (!answer.getResult().booleanValue()) return false;
            } else if (message.get(j) instanceof ErrorResponse) {
                throw new OWLlinkReasonerRuntimeException(((ErrorResponse) message.get(j)).getErrorString());
            }
        }
        return true;
    }

    @Override
    public Node<OWLClass> getTopClassNode() {
        final GetEquivalentClasses query = new GetEquivalentClasses(defaultKnowledgeBase, getOWLDataFactory().getOWLThing());
        final SetOfClasses classes = performRequest(query);
        final OWLClassNode node = new OWLClassNode();
        node.add(getOWLDataFactory().getOWLThing());
        for (OWLClass clazz : classes)
            node.add(clazz);
        return node;
    }

    @Override
    public Node<OWLClass> getBottomClassNode() {
        return getUnsatisfiableClasses();
    }

    @Override
    public NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct) {
        GetSubClasses query = new GetSubClasses(defaultKnowledgeBase, ce, direct);
        SetOfClassSynsets result = performRequestOWLAPI(query);
        return result;
    }

    @Override
    public NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetSuperClasses query = new GetSuperClasses(defaultKnowledgeBase, ce, direct);
        SetOfClassSynsets result = performRequestOWLAPI(query);
        return result;
    }

    @Override
    public NodeSet<OWLClass> getDisjointClasses(OWLClassExpression ce) {
            GetDisjointClasses query = new GetDisjointClasses(defaultKnowledgeBase, ce);
            return performRequestOWLAPI(query);
    }

    @Override
    public Node<OWLClass> getEquivalentClasses(OWLClassExpression ce) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetEquivalentClasses query = new GetEquivalentClasses(defaultKnowledgeBase, ce);
        OWLClassNode node = new OWLClassNode();
        for (OWLClass clazz : performRequestOWLAPI(query))
            node.add(clazz);
        return node;

    }

    @Override
    public Node<OWLClass> getUnsatisfiableClasses() throws ReasonerInterruptedException, TimeOutException {
        return getEquivalentClasses(getOWLDataFactory().getOWLNothing());
    }

    @Override
    public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetSubObjectProperties query = new GetSubObjectProperties(defaultKnowledgeBase, pe, direct);
        return performRequestOWLAPI(query);
    }

    @Override
    public NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetSuperObjectProperties query = new GetSuperObjectProperties(defaultKnowledgeBase, pe, direct);
        return performRequestOWLAPI(query);
    }

    @Override
    public Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetEquivalentObjectProperties query = new GetEquivalentObjectProperties(defaultKnowledgeBase, pe);
        OWLObjectPropertyNode node = new OWLObjectPropertyNode();
        for (OWLObjectPropertyExpression prop : performRequestOWLAPI(query)) {
            node.add(prop);
        }
        return node;
    }

    @Override
    public Node<OWLObjectPropertyExpression> getTopObjectPropertyNode() {
        final GetEquivalentObjectProperties query = new GetEquivalentObjectProperties(defaultKnowledgeBase, getOWLDataFactory().getOWLTopObjectProperty());
        final SetOfObjectProperties classes = performRequest(query);
        final OWLObjectPropertyNode node = new OWLObjectPropertyNode();
        node.add(getOWLDataFactory().getOWLTopObjectProperty());
        for (OWLObjectPropertyExpression clazz : classes)
            node.add(clazz);
        return node;
    }

    @Override
    public Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode() {
        final GetEquivalentObjectProperties query = new GetEquivalentObjectProperties(defaultKnowledgeBase, getOWLDataFactory().getOWLBottomObjectProperty());
        final SetOfObjectProperties classes = performRequest(query);
        final OWLObjectPropertyNode node = new OWLObjectPropertyNode();
        node.add(getOWLDataFactory().getOWLBottomObjectProperty());
        for (OWLObjectPropertyExpression clazz : classes)
            node.add(clazz);
        return node;
    }

    @Override
    public NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
            GetDisjointObjectProperties properties = new GetDisjointObjectProperties(defaultKnowledgeBase, pe);
            return performRequestOWLAPI(properties);
    }

    @Override
    public Node<OWLObjectPropertyExpression> getInverseObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        final OWLObjectPropertyExpression inverse = pe.getInverseProperty();
        return getEquivalentObjectProperties(inverse);
    }

    @Override
    public NodeSet<OWLClass> getObjectPropertyDomains(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NodeSet<OWLClass> getObjectPropertyRanges(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Node<OWLDataProperty> getTopDataPropertyNode() {
        final GetEquivalentDataProperties query = new GetEquivalentDataProperties(defaultKnowledgeBase, getOWLDataFactory().getOWLTopDataProperty());
        final DataPropertySynonyms classes = performRequest(query);
        final OWLDataPropertyNode node = new OWLDataPropertyNode();
        node.add(getOWLDataFactory().getOWLTopDataProperty());
        for (OWLDataProperty clazz : classes)
            node.add(clazz);
        return node;
    }

    @Override
    public Node<OWLDataProperty> getBottomDataPropertyNode() {
        final GetEquivalentDataProperties query = new GetEquivalentDataProperties(defaultKnowledgeBase, getOWLDataFactory().getOWLBottomDataProperty());
        final DataPropertySynonyms classes = performRequest(query);
        final OWLDataPropertyNode node = new OWLDataPropertyNode();
        node.add(getOWLDataFactory().getOWLBottomDataProperty());
        for (OWLDataProperty clazz : classes)
            node.add(clazz);
        return node;
    }

    @Override
    public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetSubDataProperties query = new GetSubDataProperties(defaultKnowledgeBase, pe, direct);
        return performRequestOWLAPI(query);
    }

    @Override
    public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetSuperDataProperties query = new GetSuperDataProperties(defaultKnowledgeBase, pe, direct);
        return performRequestOWLAPI(query);
    }

    @Override
    public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetEquivalentDataProperties query = new GetEquivalentDataProperties(defaultKnowledgeBase, pe);
        return performRequestOWLAPI(query);
    }

    @Override
    public NodeSet<OWLDataProperty> getDisjointDataProperties(OWLDataPropertyExpression pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetDisjointDataProperties query = new GetDisjointDataProperties(defaultKnowledgeBase, pe);
            return performRequestOWLAPI(query);
    }

    @Override
    public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        if (getIndividualNodeSetPolicy() == IndividualNodeSetPolicy.BY_NAME) {
            GetFlattenedInstances query = new GetFlattenedInstances(defaultKnowledgeBase, ce, direct);
            SetOfIndividuals individuals = performRequest(query);
            return convertByNamePolicy(individuals);
        }
        GetInstances query = new GetInstances(defaultKnowledgeBase, ce, direct);
        SetOfIndividualSynsets answer = performRequest(query);
        if (answer.isNode())
            return answer.asNode();
        else {
            return convertToNamedIndividuals(answer);
        }
    }

    @Override
    public NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetTypes query = new GetTypes(defaultKnowledgeBase, ind, direct);
        return performRequestOWLAPI(query);
    }

    @Override
    public NodeSet<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual ind, OWLObjectPropertyExpression pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        if (getIndividualNodeSetPolicy() == IndividualNodeSetPolicy.BY_NAME) {
            GetFlattenedObjectPropertyTargets query = new GetFlattenedObjectPropertyTargets(defaultKnowledgeBase, ind, pe);
            return convertByNamePolicy(performRequest(query));
        }
        GetObjectPropertyTargets query = new GetObjectPropertyTargets(defaultKnowledgeBase, ind, pe);
        SetOfIndividualSynsets answer = performRequest(query);
        if (answer.isNode())
            return answer.asNode();
        else {

        }
        return convertToNamedIndividuals(answer);
    }

    @Override
    public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind, OWLDataProperty pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetDataPropertyTargets query = new GetDataPropertyTargets(defaultKnowledgeBase, ind, pe);
        return performRequestOWLAPI(query);
    }

    @Override
    public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetSameIndividuals query = new GetSameIndividuals(defaultKnowledgeBase, ind);
        IndividualSynonyms answer = performRequest(query);
        if (answer.isNode()) {
            return answer.asNode();
        } else
            return convertToNamedIndividual(answer);
    }

    @Override
    public NodeSet<OWLNamedIndividual> getDifferentIndividuals(OWLNamedIndividual ind) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        GetDifferentIndividuals query = new GetDifferentIndividuals(defaultKnowledgeBase, ind);
        SetOfIndividualSynsets answer = performRequest(query);
        if (answer.isNode())
            return answer.asNode();
        else
            return convertToNamedIndividuals(answer);
    }

    protected NodeSet<OWLNamedIndividual> convertToNamedIndividuals(SetOfIndividualSynsets setOfsynsets) {
        OWLNamedIndividualNodeSet set = new OWLNamedIndividualNodeSet();
        for (IndividualSynset synset : setOfsynsets.getSynsets()) {
            OWLNamedIndividualNode node = new OWLNamedIndividualNode();
            for (OWLIndividual indi : synset) {
                if (indi.isNamed())
                    node.add(indi.asOWLNamedIndividual());
            }
            if (node.getSize() > 0)
                set.addNode(node);
        }
        return set;
    }

    protected Node<OWLNamedIndividual> convertToNamedIndividual(IndividualSynonyms synonyms) {
        OWLNamedIndividualNode node = new OWLNamedIndividualNode();
        for (OWLIndividual indi : synonyms) {
            if (indi.isNamed())
                node.add(indi.asOWLNamedIndividual());
        }
        return node;
    }

    protected NodeSet<OWLNamedIndividual> convertByNamePolicy(SetOfIndividuals individuals) {
        OWLNamedIndividualNodeSet set = new OWLNamedIndividualNodeSet();
        for (OWLIndividual indi : individuals) {
            if (indi.isNamed())
                set.addNode(new OWLNamedIndividualNode(indi.asOWLNamedIndividual()));
        }
        return set;

    }
@Override
public void precomputeInferences(InferenceType... inferenceTypes)
    throws ReasonerInterruptedException, TimeOutException, InconsistentOntologyException {
            classify();
            realise();
    }
@Override
public boolean isPrecomputed(InferenceType inferenceType) {
    return false;
}
@Override
public Set<InferenceType> getPrecomputableInferenceTypes() {
    return Collections.emptySet();
}
    /**
     * Classify.
     */
    public void classify() {
        Classify classify = new Classify(defaultKnowledgeBase);
        performRequestOWLAPI(classify);
    }

    /**
     * Realise.
     */
    public void realise() {
        Realize realize = new Realize(defaultKnowledgeBase);
        performRequestOWLAPI(realize);
    }

    protected void createDefaultKB() {
        CreateKB kb = new CreateKB();
        KB kbResponse = performRequest(kb);
        this.defaultKnowledgeBase = kbResponse.getKB();
    }

    protected Description getReasonerInfo() {
        GetDescription getDesc = new GetDescription();
        return performRequest(getDesc);
    }

    protected ResponseMessage performRequests(Request<?>... request) {
        //session.setReasonerURL(this.reasonerURL);
        return session.performRequests(request);
    }

    protected <R extends Response> R performRequest(Request<R> request) {
        ResponseMessage message = performRequests(request);
        return message.getResponse(request);
    }

    /**
     * If an OWLLink error occurs the error will be tried to be thrown as OWLAPI exception,
     * e.g., OWLlinkUnsatisfiableKBErrorResponseException as InconsistentOntologyException.
     *
     * @param request request
     * @param <R> type of response
     * @return response
     */
    protected <R extends Response> R performRequestOWLAPI(Request<R> request) {
        try {
            return performRequest(request);
        } catch (OWLlinkUnsatisfiableKBErrorResponseException exception) {
            throw new InconsistentOntologyException(exception);
        }
    }
}
