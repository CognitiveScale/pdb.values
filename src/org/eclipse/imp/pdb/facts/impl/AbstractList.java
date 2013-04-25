/*******************************************************************************
 * Copyright (c) 2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *   * Michael Steindorfer - Michael.Steindorfer@cwi.nl - CWI
 *******************************************************************************/
package org.eclipse.imp.pdb.facts.impl;

import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListRelation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.impl.func.ListFunctions;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;

public abstract class AbstractList extends Value implements IList {

    public AbstractList(Type collectionType) {
        super(collectionType);
    }

    protected static TypeFactory getTypeFactory() {
        return TypeFactory.getInstance();
    }

    /*
     * TODO: get rid of code duplication (@see AbstractSet.inferSetOrRelType)
     */
    protected static Type inferListOrRelType(final Type elementType, final Iterable<IValue> content) {
        final Type inferredElementType;
        final Type inferredCollectionType;

        // is collection empty?
        if (content.iterator().hasNext() == false) {
            inferredElementType = getTypeFactory().voidType();
        } else {
            inferredElementType = elementType;
        }

        // consists collection out of tuples?
        if (inferredElementType.isTupleType()) {
            inferredCollectionType = getTypeFactory().lrelTypeFromTuple(inferredElementType);
        } else {
            inferredCollectionType = getTypeFactory().listType(inferredElementType);
        }

        return inferredCollectionType;
    }

    protected abstract IValueFactory getValueFactory();

    @Override
    public Type getElementType() {
        return getType().getElementType();
    }

    @Override
    public IList reverse() {
        return ListFunctions.reverse(getValueFactory(), this);
    }

    @Override
    public IList append(IValue e) {
        return ListFunctions.append(getValueFactory(), this, e);
    }

    @Override
    public IList insert(IValue e) {
        return ListFunctions.insert(getValueFactory(), this, e);
    }

    @Override
    public IList concat(IList that) {
        return ListFunctions.concat(getValueFactory(), this, that);
    }

    @Override
    public IList put(int i, IValue e) {
        return ListFunctions.put(getValueFactory(), this, i, e);
    }

    @Override
    public IList replace(int first, int second, int end, IList repl) {
        return ListFunctions.replace(getValueFactory(), this, first, second, end, repl);
    }

    @Override
    public IList sublist(int offset, int length) {
        return ListFunctions.sublist(getValueFactory(), this, offset, length);
    }

    @Override
    public boolean contains(IValue e) {
        return ListFunctions.contains(getValueFactory(), this, e);
    }

    @Override
    public IList delete(IValue e) {
        return ListFunctions.delete(getValueFactory(), this, e);
    }

    @Override
    public IList delete(int i) {
        return ListFunctions.delete(getValueFactory(), this, i);

    }

    @Override
    public IListRelation product(IList that) {
        return (IListRelation) ListFunctions.product(getValueFactory(), this, that);
    }

    @Override
    public IList intersect(IList that) {
        return ListFunctions.intersect(getValueFactory(), this, that);
    }

    @Override
    public IList subtract(IList that) {
        return ListFunctions.subtract(getValueFactory(), this, that);
    }

    @Override
    public boolean isSubListOf(IList that) {
        return ListFunctions.isSubListOf(getValueFactory(), this, that);
    }

    @Override
    public boolean isEqual(IValue that) {
        return ListFunctions.isEqual(getValueFactory(), this, that);
    }

    @Override
    public boolean equals(Object that) {
        return ListFunctions.equals(getValueFactory(), this, that);
    }

    @Override
    public <T> T accept(IValueVisitor<T> v) throws VisitorException {
//        if (getElementType().isTupleType()) {
//            return v.visitListRelation(this);
//        } else {
//            return v.visitList(this);
//        }
        return v.visitList(this);
    }

}