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

import java.util.*;

/**
 * Author: Olaf Noppens
 * Date: 24.11.2009
 * @param <E> type
 */
public abstract class AbstractSetOfImpl<E> implements Set<E> {
    final Set<E> delegateSet;
    final E single;

    /**
     * @param e e 
     */
    public AbstractSetOfImpl(E e) {
        delegateSet=Collections.singleton(e);
        single=e;
    }

    /**
     * @param elements elements 
     */
    public AbstractSetOfImpl(Collection<E> elements) {
        this.delegateSet = Collections.unmodifiableSet(new HashSet<>(elements));
        single=null;
    }

    protected Set<E> createSet(int size) {
        return new HashSet<>(size);
    }

    /** @return true if singleton */
    public boolean isSingleton() {
        return single!=null;
    }

    /** @return singleton */
    public E getSingletonElement() {
        if (!isSingleton()) {
            throw new RuntimeException("Not a singleton set");
        }
        return single;
    }

    /** @return singleton */
    public Optional<E> singleton() {
        return Optional.ofNullable(single);
    }

    @Override
    public int size() {
        return delegateSet.size();
    }

    @Override
    public boolean isEmpty() {
        return delegateSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegateSet.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return this.delegateSet.containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends E> es) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return delegateSet.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.delegateSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.delegateSet.toArray(a);
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
}

