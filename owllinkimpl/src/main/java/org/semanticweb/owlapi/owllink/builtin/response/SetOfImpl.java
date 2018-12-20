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

package org.semanticweb.owlapi.owllink.builtin.response;

import java.util.Collection;

/**
 * Author: Olaf Noppens
 * Date: 22.10.2009
 * @param <E> object type
 */
public abstract class SetOfImpl<E> extends AbstractSetOfImpl<E> implements SetOfX<E> {
    private String warning;

    /**
     * @param e e 
     */
    public SetOfImpl(E e) {
        this(e, null);
    }

    /**
     * @param e e 
     * @param warning warning 
     */
    public SetOfImpl(E e, String warning) {
        super(e);
        this.warning = warning;
    }

    /**
     * @param elements elements 
     */
    public SetOfImpl(Collection<E> elements) {
        this(elements, null);
    }

    /**
     * @param elements elements 
     * @param warning warning 
     */
    public SetOfImpl(Collection<E> elements, String warning) {
        super(elements);
        this.warning = warning;
    }

    @Override
    public boolean hasWarning() {
        return getWarning() != null;
    }

    @Override
    public String getWarning() {
        return this.warning;
    }
}
