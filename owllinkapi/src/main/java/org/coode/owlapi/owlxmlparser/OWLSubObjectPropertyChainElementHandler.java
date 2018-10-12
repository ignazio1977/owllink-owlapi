package org.coode.owlapi.owlxmlparser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 03-Apr-2007
 */
public class OWLSubObjectPropertyChainElementHandler extends
        AbstractOWLElementHandler<List<OWLObjectPropertyExpression>> {

    private List<OWLObjectPropertyExpression> propertyList;

    /**
     * @param handler
     *        owlxml handler
     */
    public OWLSubObjectPropertyChainElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        propertyList = new ArrayList<>();
    }

    @Override
    public void endElement() throws OWLParserException,
            UnloadableImportException {
        getParentHandler().handleChild(this);
    }

    @Override
    public List<OWLObjectPropertyExpression> getOWLObject() {
        return propertyList;
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        propertyList.add(handler.getOWLObject());
    }
}
