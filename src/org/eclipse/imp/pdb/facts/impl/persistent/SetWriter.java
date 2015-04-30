/*******************************************************************************
 * Copyright (c) 2013-2014 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *   * Michael Steindorfer - Michael.Steindorfer@cwi.nl - CWI
 *******************************************************************************/
package org.eclipse.imp.pdb.facts.impl.persistent;

import java.util.Comparator;
import java.util.function.BiFunction;

import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.exceptions.UnexpectedElementTypeException;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.util.AbstractTypeBag;
import org.eclipse.imp.pdb.facts.util.EqualityUtils;
import org.eclipse.imp.pdb.facts.util.ImmutableSet;
import org.eclipse.imp.pdb.facts.util.ImmutableSetMultimap;
import org.eclipse.imp.pdb.facts.util.ImmutableSetMultimapAsImmutableSetView;
import org.eclipse.imp.pdb.facts.util.TransientSet;
import org.eclipse.imp.pdb.facts.util.DefaultTrieSet;
import org.eclipse.imp.pdb.facts.util.TrieSetMultimap_BleedingEdge;

class SetWriter implements ISetWriter {

	@SuppressWarnings("unchecked")
	private static final Comparator<Object> equivalenceComparator = EqualityUtils
					.getEquivalenceComparator();

	protected AbstractTypeBag elementTypeBag;
	protected TransientSet<IValue> setContent;

	protected final boolean checkUpperBound;
	protected final Type upperBoundType;
	protected ISet constructedSet;

	SetWriter(Type upperBoundType) {
		super();

		this.checkUpperBound = true;
		this.upperBoundType = upperBoundType;

		elementTypeBag = AbstractTypeBag.of();
//		setContent = DefaultTrieSet.transientOf();
		constructedSet = null;
	}

	SetWriter() {
		super();

		this.checkUpperBound = false;
		this.upperBoundType = null;

		elementTypeBag = AbstractTypeBag.of();
//		setContent = DefaultTrieSet.transientOf();
		constructedSet = null;
	}

	private void put(IValue element) {
		final Type elementType = element.getType();

		if (checkUpperBound && !elementType.isSubtypeOf(upperBoundType)) {
			throw new UnexpectedElementTypeException(upperBoundType, elementType);
		}
		
		/*
		 * EXPERIMENTAL: Enforce that binary relations always are backed by
		 * multi-maps (instead of being represented as a set of tuples).
		 */
		if (setContent == null) {
			if ((elementType.isTuple() && elementType.getArity() == 2) == true) {
				final ImmutableSetMultimap<IValue, IValue> multimap = TrieSetMultimap_BleedingEdge.<IValue, IValue>of();
	
				final BiFunction<IValue, IValue, ITuple> tupleOf = (first, second) -> org.eclipse.imp.pdb.facts.impl.fast.Tuple
								.newTuple(first, second);
	
				final BiFunction<ITuple, Integer, Object> tupleElementAt = (tuple, position) -> {
					switch (position) {
					case 0:
						return ((ITuple) tuple).get(0);
					case 1:
						return ((ITuple) tuple).get(1);
					default:
						throw new IllegalStateException();
					}
				};
	
				setContent = (TransientSet) new ImmutableSetMultimapAsImmutableSetView<IValue, IValue, ITuple>(
								multimap, tupleOf, tupleElementAt).asTransient();
			} else {
				setContent = org.eclipse.imp.pdb.facts.util.DefaultTrieSet.transientOf();
			}
		}
		
		try {
			boolean result = setContent.__insertEquivalent(element, equivalenceComparator);
			if (result) {
				elementTypeBag = elementTypeBag.increase(elementType);
			}
		} catch(ClassCastException | ArrayIndexOutOfBoundsException e) {
			// Conversion from ImmutableSetMultimapAsImmutableSetView to DefaultTrieSet
			// TODO: use elementTypeBag for deciding upon conversion and not exception
			
			TransientSet<IValue> convertedSetContent = DefaultTrieSet.transientOf();
			convertedSetContent.__insertAll(setContent);			
			setContent = convertedSetContent;
			
			// repeat try-block
			boolean result = setContent.__insertEquivalent(element, equivalenceComparator);
			if (result) {
				elementTypeBag = elementTypeBag.increase(elementType);
			}			
		}
	}

	@Override
	public void insert(IValue... values) throws FactTypeUseException {
		checkMutation();

		for (IValue item : values) {
			put(item);
		}
	}

	@Override
	public void insertAll(Iterable<? extends IValue> collection) throws FactTypeUseException {
		checkMutation();

		for (IValue item : collection) {
			put(item);
		}
	}

	@Override
	public ISet done() {
		if (setContent == null) {
			setContent = DefaultTrieSet.transientOf();
		}
		
		if (constructedSet == null) {
			constructedSet = new PDBPersistentHashSet(elementTypeBag, setContent.freeze());
		}

		return constructedSet;
	}

	private void checkMutation() {
		if (constructedSet != null) {
			throw new UnsupportedOperationException("Mutation of a finalized set is not supported.");
		}
	}

	@Override
	public String toString() {
		return setContent.toString();
	}

}
