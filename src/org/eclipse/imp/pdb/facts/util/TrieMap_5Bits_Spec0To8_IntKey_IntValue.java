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
package org.eclipse.imp.pdb.facts.util;

import static org.eclipse.imp.pdb.facts.util.AbstractSpecialisedImmutableMap.entryOf;

import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("rawtypes")
public class TrieMap_5Bits_Spec0To8_IntKey_IntValue extends
				AbstractMap<java.lang.Integer, java.lang.Integer> implements
				ImmutableMap<java.lang.Integer, java.lang.Integer> {

	@SuppressWarnings("unchecked")
	private static final TrieMap_5Bits_Spec0To8_IntKey_IntValue EMPTY_MAP = new TrieMap_5Bits_Spec0To8_IntKey_IntValue(
					CompactMapNode.EMPTY_NODE, 0, 0);

	private static final boolean DEBUG = false;

	private final AbstractMapNode rootNode;
	private final int hashCode;
	private final int cachedSize;

	TrieMap_5Bits_Spec0To8_IntKey_IntValue(AbstractMapNode rootNode, int hashCode, int cachedSize) {
		this.rootNode = rootNode;
		this.hashCode = hashCode;
		this.cachedSize = cachedSize;
		if (DEBUG) {
			assert checkHashCodeAndSize(hashCode, cachedSize);
		}
	}

	@SuppressWarnings("unchecked")
	public static final ImmutableMap<java.lang.Integer, java.lang.Integer> of() {
		return TrieMap_5Bits_Spec0To8_IntKey_IntValue.EMPTY_MAP;
	}

	@SuppressWarnings("unchecked")
	public static final ImmutableMap<java.lang.Integer, java.lang.Integer> of(
					Object... keyValuePairs) {
		if (keyValuePairs.length % 2 != 0) {
			throw new IllegalArgumentException(
							"Length of argument list is uneven: no key/value pairs.");
		}

		ImmutableMap<java.lang.Integer, java.lang.Integer> result = TrieMap_5Bits_Spec0To8_IntKey_IntValue.EMPTY_MAP;

		for (int i = 0; i < keyValuePairs.length; i += 2) {
			final int key = (int) keyValuePairs[i];
			final int val = (int) keyValuePairs[i + 1];

			result = result.__put(key, val);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static final TransientMap<java.lang.Integer, java.lang.Integer> transientOf() {
		return TrieMap_5Bits_Spec0To8_IntKey_IntValue.EMPTY_MAP.asTransient();
	}

	@SuppressWarnings("unchecked")
	public static final TransientMap<java.lang.Integer, java.lang.Integer> transientOf(
					Object... keyValuePairs) {
		if (keyValuePairs.length % 2 != 0) {
			throw new IllegalArgumentException(
							"Length of argument list is uneven: no key/value pairs.");
		}

		final TransientMap<java.lang.Integer, java.lang.Integer> result = TrieMap_5Bits_Spec0To8_IntKey_IntValue.EMPTY_MAP
						.asTransient();

		for (int i = 0; i < keyValuePairs.length; i += 2) {
			final int key = (int) keyValuePairs[i];
			final int val = (int) keyValuePairs[i + 1];

			result.__put(key, val);
		}

		return result;
	}

	private boolean checkHashCodeAndSize(final int targetHash, final int targetSize) {
		int hash = 0;
		int size = 0;

		for (Iterator<Map.Entry<java.lang.Integer, java.lang.Integer>> it = entryIterator(); it
						.hasNext();) {
			final Map.Entry<java.lang.Integer, java.lang.Integer> entry = it.next();
			final int key = entry.getKey();
			final int val = entry.getValue();

			hash += (int) key ^ (int) val;
			size += 1;
		}

		return hash == targetHash && size == targetSize;
	}

	@Override
	public TrieMap_5Bits_Spec0To8_IntKey_IntValue __put(final java.lang.Integer key,
					final java.lang.Integer val) {
		final int keyHash = key.hashCode();
		final Result details = Result.unchanged();

		final CompactMapNode newRootNode = rootNode.updated(null, key, val, keyHash, 0, details);

		if (details.isModified()) {

			if (details.hasReplacedValue()) {
				final int valHashOld = (int) details.getReplacedValue();
				final int valHashNew = (int) val;

				return new TrieMap_5Bits_Spec0To8_IntKey_IntValue(newRootNode, hashCode
								+ (keyHash ^ valHashNew) - (keyHash ^ valHashOld), cachedSize);
			}

			final int valHash = (int) val;
			return new TrieMap_5Bits_Spec0To8_IntKey_IntValue(newRootNode, hashCode
							+ (keyHash ^ valHash), cachedSize + 1);

		}

		return this;
	}

	@Override
	public TrieMap_5Bits_Spec0To8_IntKey_IntValue __putEquivalent(final java.lang.Integer key,
					final java.lang.Integer val, final Comparator<Object> cmp) {
		final int keyHash = key.hashCode();
		final Result details = Result.unchanged();

		final CompactMapNode newRootNode = rootNode.updated(null, key, val, keyHash, 0, details,
						cmp);

		if (details.isModified()) {

			if (details.hasReplacedValue()) {
				final int valHashOld = (int) details.getReplacedValue();
				final int valHashNew = (int) val;

				return new TrieMap_5Bits_Spec0To8_IntKey_IntValue(newRootNode, hashCode
								+ (keyHash ^ valHashNew) - (keyHash ^ valHashOld), cachedSize);
			}

			final int valHash = (int) val;
			return new TrieMap_5Bits_Spec0To8_IntKey_IntValue(newRootNode, hashCode
							+ (keyHash ^ valHash), cachedSize + 1);

		}

		return this;
	}

	@Override
	public ImmutableMap<java.lang.Integer, java.lang.Integer> __remove(final java.lang.Integer key) {
		final int keyHash = key.hashCode();
		final Result details = Result.unchanged();

		final CompactMapNode newRootNode = rootNode.removed(null, key, keyHash, 0, details);

		if (details.isModified()) {

			assert details.hasReplacedValue();
			final int valHash = (int) details.getReplacedValue();

			return new TrieMap_5Bits_Spec0To8_IntKey_IntValue(newRootNode, hashCode
							- (keyHash ^ valHash), cachedSize - 1);

		}

		return this;
	}

	@Override
	public ImmutableMap<java.lang.Integer, java.lang.Integer> __removeEquivalent(
					final java.lang.Integer key, final Comparator<Object> cmp) {
		final int keyHash = key.hashCode();
		final Result details = Result.unchanged();

		final CompactMapNode newRootNode = rootNode.removed(null, key, keyHash, 0, details, cmp);

		if (details.isModified()) {

			assert details.hasReplacedValue();
			final int valHash = (int) details.getReplacedValue();

			return new TrieMap_5Bits_Spec0To8_IntKey_IntValue(newRootNode, hashCode
							- (keyHash ^ valHash), cachedSize - 1);

		}

		return this;
	}

	@Override
	public boolean containsKey(final java.lang.Object o) {
		try {
			@SuppressWarnings("unchecked")
			final int key = (int) o;
			return rootNode.containsKey(key, (int) key, 0);
		} catch (ClassCastException unused) {
			return false;
		}
	}

	@Override
	public boolean containsKeyEquivalent(final java.lang.Object o, final Comparator<Object> cmp) {
		try {
			@SuppressWarnings("unchecked")
			final int key = (int) o;
			return rootNode.containsKey(key, (int) key, 0, cmp);
		} catch (ClassCastException unused) {
			return false;
		}
	}

	@Override
	public boolean containsValue(final java.lang.Object o) {
		for (Iterator<java.lang.Integer> iterator = valueIterator(); iterator.hasNext();) {
			if (iterator.next().equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValueEquivalent(final java.lang.Object o, final Comparator<Object> cmp) {
		for (Iterator<java.lang.Integer> iterator = valueIterator(); iterator.hasNext();) {
			if (iterator.next().equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public java.lang.Integer get(final java.lang.Object o) {
		try {
			@SuppressWarnings("unchecked")
			final int key = (int) o;
			final Optional<java.lang.Integer> result = rootNode.findByKey(key, (int) key, 0);

			if (result.isPresent()) {
				return result.get();
			} else {
				return null;
			}
		} catch (ClassCastException unused) {
			return null;
		}
	}

	@Override
	public java.lang.Integer getEquivalent(final java.lang.Object o, final Comparator<Object> cmp) {
		try {
			@SuppressWarnings("unchecked")
			final int key = (int) o;
			final Optional<java.lang.Integer> result = rootNode.findByKey(key, (int) key, 0, cmp);

			if (result.isPresent()) {
				return result.get();
			} else {
				return null;
			}
		} catch (ClassCastException unused) {
			return null;
		}
	}

	@Override
	public ImmutableMap<java.lang.Integer, java.lang.Integer> __putAll(
					final Map<? extends java.lang.Integer, ? extends java.lang.Integer> map) {
		TransientMap<java.lang.Integer, java.lang.Integer> tmp = asTransient();
		tmp.__putAll(map);
		return tmp.freeze();
	}

	@Override
	public ImmutableMap<java.lang.Integer, java.lang.Integer> __putAllEquivalent(
					final Map<? extends java.lang.Integer, ? extends java.lang.Integer> map,
					final Comparator<Object> cmp) {
		TransientMap<java.lang.Integer, java.lang.Integer> tmp = asTransient();
		tmp.__putAllEquivalent(map, cmp);
		return tmp.freeze();
	}

	@Override
	public java.lang.Integer put(final java.lang.Integer key, final java.lang.Integer val) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public java.lang.Integer remove(final java.lang.Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(final Map<? extends java.lang.Integer, ? extends java.lang.Integer> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return cachedSize;
	}

	@Override
	public SupplierIterator<java.lang.Integer, java.lang.Integer> keyIterator() {
		return new MapKeyIterator(rootNode);
	}

	@Override
	public Iterator<java.lang.Integer> valueIterator() {
		return new MapValueIterator(rootNode);
	}

	@Override
	public Iterator<Map.Entry<java.lang.Integer, java.lang.Integer>> entryIterator() {
		return new MapEntryIterator(rootNode);
	}

	@Override
	public Set<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>> entrySet() {
		Set<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>> entrySet = null;

		if (entrySet == null) {
			entrySet = new AbstractSet<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>>() {
				@Override
				public Iterator<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>> iterator() {
					return new Iterator<Entry<java.lang.Integer, java.lang.Integer>>() {
						private final Iterator<Entry<java.lang.Integer, java.lang.Integer>> i = entryIterator();

						@Override
						public boolean hasNext() {
							return i.hasNext();
						}

						@Override
						public Entry<java.lang.Integer, java.lang.Integer> next() {
							return i.next();
						}

						@Override
						public void remove() {
							i.remove();
						}
					};
				}

				@Override
				public int size() {
					return TrieMap_5Bits_Spec0To8_IntKey_IntValue.this.size();
				}

				@Override
				public boolean isEmpty() {
					return TrieMap_5Bits_Spec0To8_IntKey_IntValue.this.isEmpty();
				}

				@Override
				public void clear() {
					TrieMap_5Bits_Spec0To8_IntKey_IntValue.this.clear();
				}

				@Override
				public boolean contains(Object k) {
					return TrieMap_5Bits_Spec0To8_IntKey_IntValue.this.containsKey(k);
				}
			};
		}
		return entrySet;
	}

	@Override
	public boolean isTransientSupported() {
		return true;
	}

	@Override
	public TransientMap<java.lang.Integer, java.lang.Integer> asTransient() {
		return new TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue(this);
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other == null) {
			return false;
		}

		if (other instanceof TrieMap_5Bits_Spec0To8_IntKey_IntValue) {
			TrieMap_5Bits_Spec0To8_IntKey_IntValue that = (TrieMap_5Bits_Spec0To8_IntKey_IntValue) other;

			if (this.size() != that.size()) {
				return false;
			}

			return rootNode.equals(that.rootNode);
		}

		return super.equals(other);
	}

	/*
	 * For analysis purposes only.
	 */
	protected AbstractMapNode getRootNode() {
		return rootNode;
	}

	/*
	 * For analysis purposes only.
	 */
	protected Iterator<AbstractMapNode> nodeIterator() {
		return new TrieMap_5Bits_Spec0To8_IntKey_IntValueNodeIterator(rootNode);
	}

	/*
	 * For analysis purposes only.
	 */
	protected int getNodeCount() {
		final Iterator<AbstractMapNode> it = nodeIterator();
		int sumNodes = 0;

		for (; it.hasNext(); it.next()) {
			sumNodes += 1;
		}

		return sumNodes;
	}

	/*
	 * For analysis purposes only. Payload X Node
	 */
	protected int[][] arityCombinationsHistogram() {
		final Iterator<AbstractMapNode> it = nodeIterator();
		final int[][] sumArityCombinations = new int[33][33];

		while (it.hasNext()) {
			final AbstractMapNode node = it.next();
			sumArityCombinations[node.payloadArity()][node.nodeArity()] += 1;
		}

		return sumArityCombinations;
	}

	/*
	 * For analysis purposes only.
	 */
	protected int[] arityHistogram() {
		final int[][] sumArityCombinations = arityCombinationsHistogram();
		final int[] sumArity = new int[33];

		final int maxArity = 32; // TODO: factor out constant

		for (int j = 0; j <= maxArity; j++) {
			for (int maxRestArity = maxArity - j, k = 0; k <= maxRestArity - j; k++) {
				sumArity[j + k] += sumArityCombinations[j][k];
			}
		}

		return sumArity;
	}

	/*
	 * For analysis purposes only.
	 */
	public void printStatistics() {
		final int[][] sumArityCombinations = arityCombinationsHistogram();
		final int[] sumArity = arityHistogram();
		final int sumNodes = getNodeCount();

		final int[] cumsumArity = new int[33];
		for (int cumsum = 0, i = 0; i < 33; i++) {
			cumsum += sumArity[i];
			cumsumArity[i] = cumsum;
		}

		final float threshhold = 0.01f; // for printing results
		for (int i = 0; i < 33; i++) {
			float arityPercentage = (float) (sumArity[i]) / sumNodes;
			float cumsumArityPercentage = (float) (cumsumArity[i]) / sumNodes;

			if (arityPercentage != 0 && arityPercentage >= threshhold) {
				// details per level
				StringBuilder bldr = new StringBuilder();
				int max = i;
				for (int j = 0; j <= max; j++) {
					for (int k = max - j; k <= max - j; k++) {
						float arityCombinationsPercentage = (float) (sumArityCombinations[j][k])
										/ sumNodes;

						if (arityCombinationsPercentage != 0
										&& arityCombinationsPercentage >= threshhold) {
							bldr.append(String.format("%d/%d: %s, ", j, k, new DecimalFormat(
											"0.00%").format(arityCombinationsPercentage)));
						}
					}
				}
				final String detailPercentages = bldr.toString();

				// overview
				System.out.println(String.format("%2d: %s\t[cumsum = %s]\t%s", i,
								new DecimalFormat("0.00%").format(arityPercentage),
								new DecimalFormat("0.00%").format(cumsumArityPercentage),
								detailPercentages));
			}
		}
	}

	abstract static class Optional<T> {
		private static final Optional EMPTY = new Optional() {
			@Override
			boolean isPresent() {
				return false;
			}

			@Override
			Object get() {
				return null;
			}
		};

		@SuppressWarnings("unchecked")
		static <T> Optional<T> empty() {
			return EMPTY;
		}

		static <T> Optional<T> of(T value) {
			return new Value<T>(value);
		}

		abstract boolean isPresent();

		abstract T get();

		private static final class Value<T> extends Optional<T> {
			private final T value;

			private Value(T value) {
				this.value = value;
			}

			@Override
			boolean isPresent() {
				return true;
			}

			@Override
			T get() {
				return value;
			}
		}
	}

	static final class Result {
		private int replacedValue;
		private boolean isModified;
		private boolean isReplaced;

		// update: inserted/removed single element, element count changed
		public void modified() {
			this.isModified = true;
		}

		public void updated(int replacedValue) {
			this.replacedValue = replacedValue;
			this.isModified = true;
			this.isReplaced = true;
		}

		// update: neither element, nor element count changed
		public static Result unchanged() {
			return new Result();
		}

		private Result() {
		}

		public boolean isModified() {
			return isModified;
		}

		public boolean hasReplacedValue() {
			return isReplaced;
		}

		public int getReplacedValue() {
			return replacedValue;
		}
	}

	protected static abstract class AbstractNode<K, V> {
	}

	protected static abstract class AbstractMapNode extends
					AbstractNode<java.lang.Integer, java.lang.Integer> {

		static final int TUPLE_LENGTH = 2;

		abstract boolean containsKey(final int key, final int keyHash, final int shift);

		abstract boolean containsKey(final int key, final int keyHash, final int shift,
						final Comparator<Object> cmp);

		abstract Optional<java.lang.Integer> findByKey(final int key, final int keyHash,
						final int shift);

		abstract Optional<java.lang.Integer> findByKey(final int key, final int keyHash,
						final int shift, final Comparator<Object> cmp);

		abstract CompactMapNode updated(final AtomicReference<Thread> mutator, final int key,
						final int val, final int keyHash, final int shift, final Result details);

		abstract CompactMapNode updated(final AtomicReference<Thread> mutator, final int key,
						final int val, final int keyHash, final int shift, final Result details,
						final Comparator<Object> cmp);

		abstract CompactMapNode removed(final AtomicReference<Thread> mutator, final int key,
						final int keyHash, final int shift, final Result details);

		abstract CompactMapNode removed(final AtomicReference<Thread> mutator, final int key,
						final int keyHash, final int shift, final Result details,
						final Comparator<Object> cmp);

		static final boolean isAllowedToEdit(AtomicReference<Thread> x, AtomicReference<Thread> y) {
			return x != null && y != null && (x == y || x.get() == y.get());
		}

		abstract AbstractMapNode getNode(final int index);

		abstract boolean hasNodes();

		abstract int nodeArity();

		@Deprecated
		Iterator<? extends AbstractMapNode> nodeIterator() {
			return new Iterator<AbstractMapNode>() {

				int nextIndex = 0;

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}

				@Override
				public AbstractMapNode next() {
					if (!hasNext())
						throw new NoSuchElementException();
					return AbstractMapNode.this.getNode(nextIndex++);
				}

				@Override
				public boolean hasNext() {
					return nextIndex < AbstractMapNode.this.nodeArity();
				}
			};
		}

		abstract int getKey(final int index);

		abstract int getValue(final int index);

		abstract java.util.Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(
						final int index);

		abstract boolean hasPayload();

		abstract int payloadArity();

		@Deprecated
		/**
		 * The arity of this trie node (i.e. number of values and nodes stored
		 * on this level).
		 * 
		 * @return sum of nodes and values stored within
		 */
		int arity() {
			return payloadArity() + nodeArity();
		}

		int size() {
			final SupplierIterator<java.lang.Integer, java.lang.Integer> it = new MapKeyIterator(
							this);

			int size = 0;
			while (it.hasNext()) {
				size += 1;
				it.next();
			}

			return size;
		}

	}

	private static abstract class CompactMapNode extends AbstractMapNode {

		static final int BIT_PARTITION_SIZE = 5;
		static final int BIT_PARTITION_MASK = 0b11111;

		abstract int nodeMap();

		abstract int dataMap();

		static final byte SIZE_EMPTY = 0b00;
		static final byte SIZE_ONE = 0b01;
		static final byte SIZE_MORE_THAN_ONE = 0b10;

		/**
		 * Abstract predicate over a node's size. Value can be either
		 * {@value #SIZE_EMPTY}, {@value #SIZE_ONE}, or
		 * {@value #SIZE_MORE_THAN_ONE}.
		 * 
		 * @return size predicate
		 */
		abstract byte sizePredicate();

		@Override
		abstract CompactMapNode getNode(final int index);

		boolean nodeInvariant() {
			boolean inv1 = (size() - payloadArity() >= 2 * (arity() - payloadArity()));
			boolean inv2 = (this.arity() == 0) ? sizePredicate() == SIZE_EMPTY : true;
			boolean inv3 = (this.arity() == 1 && payloadArity() == 1) ? sizePredicate() == SIZE_ONE
							: true;
			boolean inv4 = (this.arity() >= 2) ? sizePredicate() == SIZE_MORE_THAN_ONE : true;

			boolean inv5 = (this.nodeArity() >= 0) && (this.payloadArity() >= 0)
							&& ((this.payloadArity() + this.nodeArity()) == this.arity());

			return inv1 && inv2 && inv3 && inv4 && inv5;
		}

		abstract CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val);

		abstract CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator,
						final int bitpos, final int key, final int val);

		abstract CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos);

		abstract CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node);

		abstract CompactMapNode copyAndMigrateFromInlineToNode(
						final AtomicReference<Thread> mutator, final int bitpos,
						final CompactMapNode node);

		abstract CompactMapNode copyAndMigrateFromNodeToInline(
						final AtomicReference<Thread> mutator, final int bitpos,
						final CompactMapNode node);

		/*
		 * TODO: specialize removed(..) to remove this method from this
		 * interface
		 */

		CompactMapNode removeInplaceValueAndConvertToSpecializedNode(
						final AtomicReference<Thread> mutator, final int bitpos) {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("unchecked")
		static final CompactMapNode mergeNodes(final int key0, final int val0, int keyHash0,
						final int key1, final int val1, int keyHash1, int shift) {
			assert !(key0 == key1);

			if (keyHash0 == keyHash1) {
				return new HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue(keyHash0,
								(int[]) new int[] { key0, key1 }, (int[]) new int[] { val0, val1 });
			}

			final int mask0 = (keyHash0 >>> shift) & BIT_PARTITION_MASK;
			final int mask1 = (keyHash1 >>> shift) & BIT_PARTITION_MASK;

			if (mask0 != mask1) {
				// both nodes fit on same level
				final int dataMap = (int) (1L << mask0 | 1L << mask1);

				if (mask0 < mask1) {
					return nodeOf(null, (int) 0, dataMap, key0, val0, key1, val1);
				} else {
					return nodeOf(null, (int) 0, dataMap, key1, val1, key0, val0);
				}
			} else {
				// values fit on next level
				final CompactMapNode node = mergeNodes(key0, val0, keyHash0, key1, val1, keyHash1,
								shift + BIT_PARTITION_SIZE);

				final int nodeMap = (int) (1L << mask0);
				return nodeOf(null, nodeMap, (int) 0, node);
			}
		}

		static final CompactMapNode mergeNodes(CompactMapNode node0, int keyHash0, final int key1,
						final int val1, int keyHash1, int shift) {
			final int mask0 = (keyHash0 >>> shift) & BIT_PARTITION_MASK;
			final int mask1 = (keyHash1 >>> shift) & BIT_PARTITION_MASK;

			if (mask0 != mask1) {
				// both nodes fit on same level
				final int nodeMap = (int) (1L << mask0);
				final int dataMap = (int) (1L << mask1);

				// store values before node
				return nodeOf(null, nodeMap, dataMap, key1, val1, node0);
			} else {
				// values fit on next level
				final CompactMapNode node = mergeNodes(node0, keyHash0, key1, val1, keyHash1, shift
								+ BIT_PARTITION_SIZE);

				final int nodeMap = (int) (1L << mask0);
				return nodeOf(null, nodeMap, (int) 0, node);
			}
		}

		static final CompactMapNode EMPTY_NODE;

		static {

			EMPTY_NODE = new Map0To0Node_5Bits_Spec0To8_IntKey_IntValue(null, (int) 0, (int) 0);

		};

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final java.lang.Object[] nodes) {
			return new BitmapIndexedMapNode(mutator, nodeMap, dataMap, nodes);
		}

		@SuppressWarnings("unchecked")
		static final CompactMapNode nodeOf(AtomicReference<Thread> mutator) {
			return EMPTY_NODE;
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap) {
			return EMPTY_NODE;
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1) {
			return new Map0To1Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, node1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2) {
			return new Map0To2Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, node1,
							node2);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3) {
			return new Map0To3Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, node1,
							node2, node3);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4) {
			return new Map0To4Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, node1,
							node2, node3, node4);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5) {
			return new Map0To5Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, node1,
							node2, node3, node4, node5);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6) {
			return new Map0To6Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, node1,
							node2, node3, node4, node5, node6);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6, final CompactMapNode node7) {
			return new Map0To7Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, node1,
							node2, node3, node4, node5, node6, node7);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6, final CompactMapNode node7,
						final CompactMapNode node8) {
			return new Map0To8Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, node1,
							node2, node3, node4, node5, node6, node7, node8);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6, final CompactMapNode node7,
						final CompactMapNode node8, final CompactMapNode node9) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { node1, node2, node3, node4,
							node5, node6, node7, node8, node9 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1) {
			return new Map1To0Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1) {
			return new Map1To1Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, node1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2) {
			return new Map1To2Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, node1, node2);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3) {
			return new Map1To3Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, node1, node2, node3);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4) {
			return new Map1To4Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, node1, node2, node3, node4);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5) {
			return new Map1To5Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, node1, node2, node3, node4, node5);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5, final CompactMapNode node6) {
			return new Map1To6Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, node1, node2, node3, node4, node5, node6);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5, final CompactMapNode node6,
						final CompactMapNode node7) {
			return new Map1To7Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, node1, node2, node3, node4, node5, node6, node7);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5, final CompactMapNode node6,
						final CompactMapNode node7, final CompactMapNode node8) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { key1, val1, node1, node2,
							node3, node4, node5, node6, node7, node8 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2) {
			return new Map2To0Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1) {
			return new Map2To1Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, node1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2) {
			return new Map2To2Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, node1, node2);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3) {
			return new Map2To3Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, node1, node2, node3);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4) {
			return new Map2To4Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, node1, node2, node3, node4);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5) {
			return new Map2To5Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, node1, node2, node3, node4, node5);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6) {
			return new Map2To6Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, node1, node2, node3, node4, node5, node6);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6, final CompactMapNode node7) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { key1, val1, key2, val2, node1,
							node2, node3, node4, node5, node6, node7 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3) {
			return new Map3To0Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1) {
			return new Map3To1Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, node1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2) {
			return new Map3To2Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, node1, node2);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3) {
			return new Map3To3Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, node1, node2, node3);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4) {
			return new Map3To4Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, node1, node2, node3, node4);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5) {
			return new Map3To5Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, node1, node2, node3, node4, node5);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5, final CompactMapNode node6) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { key1, val1, key2, val2, key3,
							val3, node1, node2, node3, node4, node5, node6 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4) {
			return new Map4To0Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1) {
			return new Map4To1Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, node1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1,
						final CompactMapNode node2) {
			return new Map4To2Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, node1, node2);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3) {
			return new Map4To3Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, node1, node2, node3);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4) {
			return new Map4To4Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, node1, node2, node3, node4);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { key1, val1, key2, val2, key3,
							val3, key4, val4, node1, node2, node3, node4, node5 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5) {
			return new Map5To0Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final CompactMapNode node1) {
			return new Map5To1Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, node1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final CompactMapNode node1, final CompactMapNode node2) {
			return new Map5To2Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, node1, node2);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3) {
			return new Map5To3Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, node1, node2,
							node3);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { key1, val1, key2, val2, key3,
							val3, key4, val4, key5, val5, node1, node2, node3, node4 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6) {
			return new Map6To0Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, key6, val6);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final CompactMapNode node1) {
			return new Map6To1Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, key6, val6, node1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final CompactMapNode node1,
						final CompactMapNode node2) {
			return new Map6To2Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, key6, val6,
							node1, node2);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { key1, val1, key2, val2, key3,
							val3, key4, val4, key5, val5, key6, val6, node1, node2, node3 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7) {
			return new Map7To0Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, key6, val6, key7,
							val7);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7,
						final CompactMapNode node1) {
			return new Map7To1Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, key6, val6, key7,
							val7, node1);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7,
						final CompactMapNode node1, final CompactMapNode node2) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { key1, val1, key2, val2, key3,
							val3, key4, val4, key5, val5, key6, val6, key7, val7, node1, node2 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7,
						final int key8, final int val8) {
			return new Map8To0Node_5Bits_Spec0To8_IntKey_IntValue(mutator, nodeMap, dataMap, key1,
							val1, key2, val2, key3, val3, key4, val4, key5, val5, key6, val6, key7,
							val7, key8, val8);
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7,
						final int key8, final int val8, final CompactMapNode node1) {
			return nodeOf(mutator, nodeMap, dataMap,
							new Object[] { key1, val1, key2, val2, key3, val3, key4, val4, key5,
											val5, key6, val6, key7, val7, key8, val8, node1 });
		}

		static final CompactMapNode nodeOf(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7,
						final int key8, final int val8, final int key9, final int val9) {
			return nodeOf(mutator, nodeMap, dataMap, new Object[] { key1, val1, key2, val2, key3,
							val3, key4, val4, key5, val5, key6, val6, key7, val7, key8, val8, key9,
							val9 });
		}

		int dataIndex(final int bitpos) {
			return java.lang.Integer.bitCount(dataMap() & (bitpos - 1));
		}

		int nodeIndex(final int bitpos) {
			return java.lang.Integer.bitCount(nodeMap() & (bitpos - 1));
		}

		int keyAt(final int bitpos) {
			return getKey(dataIndex(bitpos));
		}

		int valAt(final int bitpos) {
			return getValue(dataIndex(bitpos));
		}

		CompactMapNode nodeAt(final int bitpos) {
			return getNode(nodeIndex(bitpos));
		}

		@Override
		boolean containsKey(final int key, final int keyHash, final int shift) {
			final int mask = (keyHash >>> shift) & BIT_PARTITION_MASK;
			final int bitpos = (int) (1L << mask);

			if ((dataMap() & bitpos) != 0) {
				return keyAt(bitpos) == key;
			}

			if ((nodeMap() & bitpos) != 0) {
				return nodeAt(bitpos).containsKey(key, keyHash, shift + BIT_PARTITION_SIZE);
			}

			return false;
		}

		@Override
		boolean containsKey(final int key, final int keyHash, final int shift,
						final Comparator<Object> cmp) {
			final int mask = (keyHash >>> shift) & BIT_PARTITION_MASK;
			final int bitpos = (int) (1L << mask);

			if ((dataMap() & bitpos) != 0) {
				return keyAt(bitpos) == key;
			}

			if ((nodeMap() & bitpos) != 0) {
				return nodeAt(bitpos).containsKey(key, keyHash, shift + BIT_PARTITION_SIZE, cmp);
			}

			return false;
		}

		@Override
		Optional<java.lang.Integer> findByKey(final int key, final int keyHash, final int shift) {
			final int mask = (keyHash >>> shift) & BIT_PARTITION_MASK;
			final int bitpos = (int) (1L << mask);

			if ((dataMap() & bitpos) != 0) { // inplace value
				if (keyAt(bitpos) == key) {
					final int _val = valAt(bitpos);

					return Optional.of(_val);
				}

				return Optional.empty();
			}

			if ((nodeMap() & bitpos) != 0) { // node (not value)
				final AbstractMapNode subNode = nodeAt(bitpos);

				return subNode.findByKey(key, keyHash, shift + BIT_PARTITION_SIZE);
			}

			return Optional.empty();
		}

		@Override
		Optional<java.lang.Integer> findByKey(final int key, final int keyHash, final int shift,
						final Comparator<Object> cmp) {
			final int mask = (keyHash >>> shift) & BIT_PARTITION_MASK;
			final int bitpos = (int) (1L << mask);

			if ((dataMap() & bitpos) != 0) { // inplace value
				if (keyAt(bitpos) == key) {
					final int _val = valAt(bitpos);

					return Optional.of(_val);
				}

				return Optional.empty();
			}

			if ((nodeMap() & bitpos) != 0) { // node (not value)
				final AbstractMapNode subNode = nodeAt(bitpos);

				return subNode.findByKey(key, keyHash, shift + BIT_PARTITION_SIZE, cmp);
			}

			return Optional.empty();
		}

		@Override
		CompactMapNode updated(final AtomicReference<Thread> mutator, final int key, final int val,
						final int keyHash, final int shift, final Result details) {
			final int mask = (keyHash >>> shift) & BIT_PARTITION_MASK;
			final int bitpos = (int) (1L << mask);

			if ((dataMap() & bitpos) != 0) { // inplace value
				final int dataIndex = dataIndex(bitpos);
				final int currentKey = getKey(dataIndex);

				if (currentKey == key) {
					final int currentVal = getValue(dataIndex);

					if (currentVal == val) {
						return this;
					} else {
						// update mapping
						details.updated(currentVal);
						return copyAndSetValue(mutator, bitpos, val);
					}
				} else {
					final int currentVal = getValue(dataIndex);
					final CompactMapNode subNodeNew = mergeNodes(currentKey, currentVal,
									(int) currentKey, key, val, keyHash, shift + BIT_PARTITION_SIZE);

					// final CompactMapNode thisNew =
					// copyAndRemoveValue(mutator,
					// bitpos).copyAndInsertNode(mutator, bitpos, nodeNew);
					// final CompactMapNode thisNew =
					// copyAndMigrateFromInlineToNode(mutator, bitpos, nodeNew);

					details.modified();
					return copyAndMigrateFromInlineToNode(mutator, bitpos, subNodeNew);

				}
			} else if ((nodeMap() & bitpos) != 0) { // node (not value)
				final CompactMapNode subNode = nodeAt(bitpos);
				final CompactMapNode subNodeNew = subNode.updated(mutator, key, val, keyHash, shift
								+ BIT_PARTITION_SIZE, details);

				if (details.isModified()) {
					return copyAndSetNode(mutator, bitpos, subNodeNew);
				} else {
					return this;
				}
			} else {
				// no value
				details.modified();
				return copyAndInsertValue(mutator, bitpos, key, val);
			}
		}

		@Override
		CompactMapNode updated(final AtomicReference<Thread> mutator, final int key, final int val,
						final int keyHash, final int shift, final Result details,
						final Comparator<Object> cmp) {
			final int mask = (keyHash >>> shift) & BIT_PARTITION_MASK;
			final int bitpos = (int) (1L << mask);

			if ((dataMap() & bitpos) != 0) { // inplace value
				final int dataIndex = dataIndex(bitpos);
				final int currentKey = getKey(dataIndex);

				if (currentKey == key) {
					final int currentVal = getValue(dataIndex);

					if (currentVal == val) {
						return this;
					} else {
						// update mapping
						details.updated(currentVal);
						return copyAndSetValue(mutator, bitpos, val);
					}
				} else {
					final int currentVal = getValue(dataIndex);
					final CompactMapNode subNodeNew = mergeNodes(currentKey, currentVal,
									(int) currentKey, key, val, keyHash, shift + BIT_PARTITION_SIZE);

					// final CompactMapNode thisNew =
					// copyAndRemoveValue(mutator,
					// bitpos).copyAndInsertNode(mutator, bitpos, nodeNew);
					// final CompactMapNode thisNew =
					// copyAndMigrateFromInlineToNode(mutator, bitpos, nodeNew);

					details.modified();
					return copyAndMigrateFromInlineToNode(mutator, bitpos, subNodeNew);

				}
			} else if ((nodeMap() & bitpos) != 0) { // node (not value)
				final CompactMapNode subNode = nodeAt(bitpos);
				final CompactMapNode subNodeNew = subNode.updated(mutator, key, val, keyHash, shift
								+ BIT_PARTITION_SIZE, details, cmp);

				if (details.isModified()) {
					return copyAndSetNode(mutator, bitpos, subNodeNew);
				} else {
					return this;
				}
			} else {
				// no value
				details.modified();
				return copyAndInsertValue(mutator, bitpos, key, val);
			}
		}

		@Override
		CompactMapNode removed(final AtomicReference<Thread> mutator, final int key,
						final int keyHash, final int shift, final Result details) {
			final int mask = (keyHash >>> shift) & BIT_PARTITION_MASK;
			final int bitpos = (int) (1L << mask);

			if ((dataMap() & bitpos) != 0) { // inplace value
				final int dataIndex = dataIndex(bitpos);

				if (getKey(dataIndex) == key) {
					final int currentVal = getValue(dataIndex);
					details.updated(currentVal);

					if (this.payloadArity() == 2 && this.nodeArity() == 0) {
						/*
						 * Create new node with remaining pair. The new node
						 * will a) either become the new root returned, or b)
						 * unwrapped and inlined during returning.
						 */
						final int newDataMap = (shift == 0) ? (int) (dataMap() ^ bitpos)
										: (int) (1L << (keyHash & BIT_PARTITION_MASK));

						if (dataIndex == 0) {
							return CompactMapNode.nodeOf(mutator, (int) 0, newDataMap, getKey(1),
											getValue(1));
						} else {
							return CompactMapNode.nodeOf(mutator, (int) 0, newDataMap, getKey(0),
											getValue(0));
						}
					} else if (this.arity() == 9) {
						return removeInplaceValueAndConvertToSpecializedNode(mutator, bitpos);
					} else {
						return copyAndRemoveValue(mutator, bitpos);
					}
				} else {
					return this;
				}
			} else if ((nodeMap() & bitpos) != 0) { // node (not value)
				final CompactMapNode subNode = nodeAt(bitpos);
				final CompactMapNode subNodeNew = subNode.removed(mutator, key, keyHash, shift
								+ BIT_PARTITION_SIZE, details);

				if (!details.isModified()) {
					return this;
				}

				switch (subNodeNew.sizePredicate()) {
				case 0: {
					throw new IllegalStateException("Sub-node must have at least one element.");
				}
				case 1: {
					// inline value (move to front)
					details.modified();
					return copyAndMigrateFromNodeToInline(mutator, bitpos, subNodeNew);
				}
				default: {
					// modify current node (set replacement node)
					return copyAndSetNode(mutator, bitpos, subNodeNew);
				}
				}
			}

			return this;
		}

		@Override
		CompactMapNode removed(final AtomicReference<Thread> mutator, final int key,
						final int keyHash, final int shift, final Result details,
						final Comparator<Object> cmp) {
			final int mask = (keyHash >>> shift) & BIT_PARTITION_MASK;
			final int bitpos = (int) (1L << mask);

			if ((dataMap() & bitpos) != 0) { // inplace value
				final int dataIndex = dataIndex(bitpos);

				if (getKey(dataIndex) == key) {
					final int currentVal = getValue(dataIndex);
					details.updated(currentVal);

					if (this.payloadArity() == 2 && this.nodeArity() == 0) {
						/*
						 * Create new node with remaining pair. The new node
						 * will a) either become the new root returned, or b)
						 * unwrapped and inlined during returning.
						 */
						final int newDataMap = (shift == 0) ? (int) (dataMap() ^ bitpos)
										: (int) (1L << (keyHash & BIT_PARTITION_MASK));

						if (dataIndex == 0) {
							return CompactMapNode.nodeOf(mutator, (int) 0, newDataMap, getKey(1),
											getValue(1));
						} else {
							return CompactMapNode.nodeOf(mutator, (int) 0, newDataMap, getKey(0),
											getValue(0));
						}
					} else if (this.arity() == 9) {
						return removeInplaceValueAndConvertToSpecializedNode(mutator, bitpos);
					} else {
						return copyAndRemoveValue(mutator, bitpos);
					}
				} else {
					return this;
				}
			} else if ((nodeMap() & bitpos) != 0) { // node (not value)
				final CompactMapNode subNode = nodeAt(bitpos);
				final CompactMapNode subNodeNew = subNode.removed(mutator, key, keyHash, shift
								+ BIT_PARTITION_SIZE, details, cmp);

				if (!details.isModified()) {
					return this;
				}

				switch (subNodeNew.sizePredicate()) {
				case 0: {
					throw new IllegalStateException("Sub-node must have at least one element.");
				}
				case 1: {
					// inline value (move to front)
					details.modified();
					return copyAndMigrateFromNodeToInline(mutator, bitpos, subNodeNew);
				}
				default: {
					// modify current node (set replacement node)
					return copyAndSetNode(mutator, bitpos, subNodeNew);
				}
				}
			}

			return this;
		}

		/**
		 * @return 0 <= mask <= 2^BIT_PARTITION_SIZE - 1
		 */
		static byte recoverMask(int map, byte i_th) {
			assert 1 <= i_th && i_th <= 32;

			byte cnt1 = 0;
			byte mask = 0;

			while (mask < 32) {
				if ((map & 0x01) == 0x01) {
					cnt1 += 1;

					if (cnt1 == i_th) {
						return mask;
					}
				}

				map = (int) (map >> 1);
				mask += 1;
			}

			assert cnt1 != i_th;
			throw new RuntimeException("Called with invalid arguments.");
		}

		@Override
		public String toString() {
			final StringBuilder bldr = new StringBuilder();
			bldr.append('[');

			for (byte i = 0; i < payloadArity(); i++) {
				final byte pos = recoverMask(dataMap(), (byte) (i + 1));
				bldr.append(String.format("@%d: %s", pos, getKey(i), getValue(i)));

				if (!((i + 1) == payloadArity())) {
					bldr.append(", ");
				}
			}

			if (payloadArity() > 0 && nodeArity() > 0) {
				bldr.append(", ");
			}

			for (byte i = 0; i < nodeArity(); i++) {
				final byte pos = recoverMask(nodeMap(), (byte) (i + 1));
				bldr.append(String.format("@%d: %s", pos, getNode(i)));

				if (!((i + 1) == nodeArity())) {
					bldr.append(", ");
				}
			}

			bldr.append(']');
			return bldr.toString();
		}

	}

	private static abstract class CompactMixedMapNode extends CompactMapNode {

		private final int nodeMap;
		private final int dataMap;

		CompactMixedMapNode(final AtomicReference<Thread> mutator, final int nodeMap,
						final int dataMap) {
			this.nodeMap = nodeMap;
			this.dataMap = dataMap;
		}

		@Override
		public int nodeMap() {
			return nodeMap;
		}

		@Override
		public int dataMap() {
			return dataMap;
		}

	}

	private static abstract class CompactNodesOnlyMapNode extends CompactMapNode {

		private final int nodeMap;

		CompactNodesOnlyMapNode(final AtomicReference<Thread> mutator, final int nodeMap,
						final int dataMap) {
			this.nodeMap = nodeMap;
		}

		@Override
		public int nodeMap() {
			return nodeMap;
		}

		@Override
		public int dataMap() {
			return 0;
		}

	}

	private static abstract class CompactValuesOnlyMapNode extends CompactMapNode {

		private final int dataMap;

		CompactValuesOnlyMapNode(final AtomicReference<Thread> mutator, final int nodeMap,
						final int dataMap) {
			this.dataMap = dataMap;
		}

		@Override
		public int nodeMap() {
			return 0;
		}

		@Override
		public int dataMap() {
			return dataMap;
		}

	}

	private static abstract class CompactEmptyMapNode extends CompactMapNode {

		CompactEmptyMapNode(final AtomicReference<Thread> mutator, final int nodeMap,
						final int dataMap) {
		}

		@Override
		public int nodeMap() {
			return 0;
		}

		@Override
		public int dataMap() {
			return 0;
		}

	}

	private static final class BitmapIndexedMapNode extends CompactMixedMapNode {

		final AtomicReference<Thread> mutator;
		final java.lang.Object[] nodes;

		private BitmapIndexedMapNode(final AtomicReference<Thread> mutator, final int nodeMap,
						final int dataMap, final java.lang.Object[] nodes) {
			super(mutator, nodeMap, dataMap);

			this.mutator = mutator;
			this.nodes = nodes;

			if (DEBUG) {

				assert (TUPLE_LENGTH * java.lang.Integer.bitCount(dataMap)
								+ java.lang.Integer.bitCount(nodeMap) == nodes.length);

				for (int i = 0; i < TUPLE_LENGTH * payloadArity(); i++) {
					assert ((nodes[i] instanceof CompactMapNode) == false);
				}
				for (int i = TUPLE_LENGTH * payloadArity(); i < nodes.length; i++) {
					assert ((nodes[i] instanceof CompactMapNode) == true);
				}
			}

			assert arity() > 8;
			assert nodeInvariant();
		}

		@Override
		int getKey(final int index) {
			return (int) nodes[TUPLE_LENGTH * index];
		}

		@Override
		int getValue(final int index) {
			return (int) nodes[TUPLE_LENGTH * index + 1];
		}

		@Override
		java.util.Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(final int index) {
			return entryOf((int) nodes[TUPLE_LENGTH * index], (int) nodes[TUPLE_LENGTH * index + 1]);
		}

		@SuppressWarnings("unchecked")
		@Override
		CompactMapNode getNode(final int index) {
			return (CompactMapNode) nodes[nodes.length - 1 - index];
		}

		@Override
		boolean hasPayload() {
			return dataMap() != 0;
		}

		@Override
		int payloadArity() {
			return java.lang.Integer.bitCount(dataMap());
		}

		@Override
		boolean hasNodes() {
			return nodeMap() != 0;
		}

		@Override
		int nodeArity() {
			return java.lang.Integer.bitCount(nodeMap());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 0;
			result = prime * result + ((int) dataMap());
			result = prime * result + ((int) dataMap());
			result = prime * result + Arrays.hashCode(nodes);
			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			BitmapIndexedMapNode that = (BitmapIndexedMapNode) other;
			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}
			if (!Arrays.equals(nodes, that.nodes)) {
				return false;
			}
			return true;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(final AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = TUPLE_LENGTH * dataIndex(bitpos) + 1;

			if (isAllowedToEdit(this.mutator, mutator)) {
				// no copying if already editable
				this.nodes[idx] = val;
				return this;
			} else {
				final java.lang.Object[] src = this.nodes;
				final java.lang.Object[] dst = (java.lang.Object[]) new Object[src.length];

				// copy 'src' and set 1 element(s) at position 'idx'
				System.arraycopy(src, 0, dst, 0, src.length);
				dst[idx + 0] = val;

				return nodeOf(mutator, nodeMap(), dataMap(), dst);
			}
		}

		@Override
		CompactMapNode copyAndSetNode(final AtomicReference<Thread> mutator, final int bitpos,
						final CompactMapNode node) {

			final int idx = this.nodes.length - 1 - nodeIndex(bitpos);

			if (isAllowedToEdit(this.mutator, mutator)) {
				// no copying if already editable
				this.nodes[idx] = node;
				return this;
			} else {
				final java.lang.Object[] src = this.nodes;
				final java.lang.Object[] dst = (java.lang.Object[]) new Object[src.length];

				// copy 'src' and set 1 element(s) at position 'idx'
				System.arraycopy(src, 0, dst, 0, src.length);
				dst[idx + 0] = node;

				return nodeOf(mutator, nodeMap(), dataMap(), dst);
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(final AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int idx = TUPLE_LENGTH * dataIndex(bitpos);

			final java.lang.Object[] src = this.nodes;
			final java.lang.Object[] dst = (java.lang.Object[]) new Object[src.length + 2];

			// copy 'src' and insert 2 element(s) at position 'idx'
			System.arraycopy(src, 0, dst, 0, idx);
			dst[idx + 0] = key;
			dst[idx + 1] = val;
			System.arraycopy(src, idx, dst, idx + 2, src.length - idx);

			return nodeOf(mutator, nodeMap(), (int) (dataMap() | bitpos), dst);
		}

		@Override
		CompactMapNode copyAndRemoveValue(final AtomicReference<Thread> mutator, final int bitpos) {
			final int idx = TUPLE_LENGTH * dataIndex(bitpos);

			final java.lang.Object[] src = this.nodes;
			final java.lang.Object[] dst = (java.lang.Object[]) new Object[src.length - 2];

			// copy 'src' and remove 2 element(s) at position 'idx'
			System.arraycopy(src, 0, dst, 0, idx);
			System.arraycopy(src, idx + 2, dst, idx, src.length - idx - 2);

			return nodeOf(mutator, nodeMap(), (int) (dataMap() ^ bitpos), dst);
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {

			final int idxOld = TUPLE_LENGTH * dataIndex(bitpos);
			final int idxNew = this.nodes.length - TUPLE_LENGTH - nodeIndex(bitpos);

			final java.lang.Object[] src = this.nodes;
			final java.lang.Object[] dst = new Object[src.length - 2 + 1];

			// copy 'src' and remove 2 element(s) at position 'idxOld' and
			// insert 1 element(s) at position 'idxNew' (TODO: carefully test)
			assert idxOld <= idxNew;
			System.arraycopy(src, 0, dst, 0, idxOld);
			System.arraycopy(src, idxOld + 2, dst, idxOld, idxNew - idxOld);
			dst[idxNew + 0] = node;
			System.arraycopy(src, idxNew + 2, dst, idxNew + 1, src.length - idxNew - 2);

			return nodeOf(mutator, (int) (nodeMap() | bitpos), (int) (dataMap() ^ bitpos), dst);
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {

			final int idxOld = this.nodes.length - 1 - nodeIndex(bitpos);
			final int idxNew = dataIndex(bitpos);

			final java.lang.Object[] src = this.nodes;
			final java.lang.Object[] dst = new Object[src.length - 1 + 2];

			// copy 'src' and remove 1 element(s) at position 'idxOld' and
			// insert 2 element(s) at position 'idxNew' (TODO: carefully test)
			assert idxOld >= idxNew;
			System.arraycopy(src, 0, dst, 0, idxNew);
			dst[idxNew + 0] = node.getKey(0);
			dst[idxNew + 1] = node.getValue(0);
			System.arraycopy(src, idxNew, dst, idxNew + 2, idxOld - idxNew);
			System.arraycopy(src, idxOld + 1, dst, idxOld + 2, src.length - idxOld - 1);

			return nodeOf(mutator, (int) (nodeMap() ^ bitpos), (int) (dataMap() | bitpos), dst);
		}

		@Override
		CompactMapNode removeInplaceValueAndConvertToSpecializedNode(
						final AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (payloadArity()) { // 0 <= payloadArity <= 9 // or ts.nMax
			case 1: {

				switch (valIndex) {
				case 0: {

					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				final CompactMapNode node1 = getNode(0);
				final CompactMapNode node2 = getNode(1);
				final CompactMapNode node3 = getNode(2);
				final CompactMapNode node4 = getNode(3);
				final CompactMapNode node5 = getNode(4);
				final CompactMapNode node6 = getNode(5);
				final CompactMapNode node7 = getNode(6);
				final CompactMapNode node8 = getNode(7);

				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node6,
								node7, node8);

			}
			case 2: {
				int key1;
				int val1;

				switch (valIndex) {
				case 0: {

					key1 = getKey(1);
					val1 = getValue(1);
					break;
				}
				case 1: {

					key1 = getKey(0);
					val1 = getValue(0);
					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				final CompactMapNode node1 = getNode(0);
				final CompactMapNode node2 = getNode(1);
				final CompactMapNode node3 = getNode(2);
				final CompactMapNode node4 = getNode(3);
				final CompactMapNode node5 = getNode(4);
				final CompactMapNode node6 = getNode(5);
				final CompactMapNode node7 = getNode(6);

				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node5, node6, node7);

			}
			case 3: {
				int key1;
				int val1;
				int key2;
				int val2;

				switch (valIndex) {
				case 0: {

					key1 = getKey(1);
					val1 = getValue(1);
					key2 = getKey(2);
					val2 = getValue(2);
					break;
				}
				case 1: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(2);
					val2 = getValue(2);
					break;
				}
				case 2: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				final CompactMapNode node1 = getNode(0);
				final CompactMapNode node2 = getNode(1);
				final CompactMapNode node3 = getNode(2);
				final CompactMapNode node4 = getNode(3);
				final CompactMapNode node5 = getNode(4);
				final CompactMapNode node6 = getNode(5);

				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node4, node5, node6);

			}
			case 4: {
				int key1;
				int val1;
				int key2;
				int val2;
				int key3;
				int val3;

				switch (valIndex) {
				case 0: {

					key1 = getKey(1);
					val1 = getValue(1);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					break;
				}
				case 1: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					break;
				}
				case 2: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(3);
					val3 = getValue(3);
					break;
				}
				case 3: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				final CompactMapNode node1 = getNode(0);
				final CompactMapNode node2 = getNode(1);
				final CompactMapNode node3 = getNode(2);
				final CompactMapNode node4 = getNode(3);
				final CompactMapNode node5 = getNode(4);

				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node3, node4, node5);

			}
			case 5: {
				int key1;
				int val1;
				int key2;
				int val2;
				int key3;
				int val3;
				int key4;
				int val4;

				switch (valIndex) {
				case 0: {

					key1 = getKey(1);
					val1 = getValue(1);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					break;
				}
				case 1: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					break;
				}
				case 2: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					break;
				}
				case 3: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(4);
					val4 = getValue(4);
					break;
				}
				case 4: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				final CompactMapNode node1 = getNode(0);
				final CompactMapNode node2 = getNode(1);
				final CompactMapNode node3 = getNode(2);
				final CompactMapNode node4 = getNode(3);

				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node2, node3, node4);

			}
			case 6: {
				int key1;
				int val1;
				int key2;
				int val2;
				int key3;
				int val3;
				int key4;
				int val4;
				int key5;
				int val5;

				switch (valIndex) {
				case 0: {

					key1 = getKey(1);
					val1 = getValue(1);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					break;
				}
				case 1: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					break;
				}
				case 2: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					break;
				}
				case 3: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					break;
				}
				case 4: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(5);
					val5 = getValue(5);
					break;
				}
				case 5: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				final CompactMapNode node1 = getNode(0);
				final CompactMapNode node2 = getNode(1);
				final CompactMapNode node3 = getNode(2);

				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node1, node2, node3);

			}
			case 7: {
				int key1;
				int val1;
				int key2;
				int val2;
				int key3;
				int val3;
				int key4;
				int val4;
				int key5;
				int val5;
				int key6;
				int val6;

				switch (valIndex) {
				case 0: {

					key1 = getKey(1);
					val1 = getValue(1);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					break;
				}
				case 1: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					break;
				}
				case 2: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					break;
				}
				case 3: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					break;
				}
				case 4: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					break;
				}
				case 5: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(6);
					val6 = getValue(6);
					break;
				}
				case 6: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(5);
					val6 = getValue(5);
					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				final CompactMapNode node1 = getNode(0);
				final CompactMapNode node2 = getNode(1);

				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, node1, node2);

			}
			case 8: {
				int key1;
				int val1;
				int key2;
				int val2;
				int key3;
				int val3;
				int key4;
				int val4;
				int key5;
				int val5;
				int key6;
				int val6;
				int key7;
				int val7;

				switch (valIndex) {
				case 0: {

					key1 = getKey(1);
					val1 = getValue(1);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					break;
				}
				case 1: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					break;
				}
				case 2: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					break;
				}
				case 3: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					break;
				}
				case 4: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					break;
				}
				case 5: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					break;
				}
				case 6: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(5);
					val6 = getValue(5);
					key7 = getKey(7);
					val7 = getValue(7);
					break;
				}
				case 7: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(5);
					val6 = getValue(5);
					key7 = getKey(6);
					val7 = getValue(6);
					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				final CompactMapNode node1 = getNode(0);

				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, node1);

			}
			case 9: {
				int key1;
				int val1;
				int key2;
				int val2;
				int key3;
				int val3;
				int key4;
				int val4;
				int key5;
				int val5;
				int key6;
				int val6;
				int key7;
				int val7;
				int key8;
				int val8;

				switch (valIndex) {
				case 0: {

					key1 = getKey(1);
					val1 = getValue(1);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					key8 = getKey(8);
					val8 = getValue(8);
					break;
				}
				case 1: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(2);
					val2 = getValue(2);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					key8 = getKey(8);
					val8 = getValue(8);
					break;
				}
				case 2: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(3);
					val3 = getValue(3);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					key8 = getKey(8);
					val8 = getValue(8);
					break;
				}
				case 3: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(4);
					val4 = getValue(4);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					key8 = getKey(8);
					val8 = getValue(8);
					break;
				}
				case 4: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(5);
					val5 = getValue(5);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					key8 = getKey(8);
					val8 = getValue(8);
					break;
				}
				case 5: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(6);
					val6 = getValue(6);
					key7 = getKey(7);
					val7 = getValue(7);
					key8 = getKey(8);
					val8 = getValue(8);
					break;
				}
				case 6: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(5);
					val6 = getValue(5);
					key7 = getKey(7);
					val7 = getValue(7);
					key8 = getKey(8);
					val8 = getValue(8);
					break;
				}
				case 7: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(5);
					val6 = getValue(5);
					key7 = getKey(6);
					val7 = getValue(6);
					key8 = getKey(8);
					val8 = getValue(8);
					break;
				}
				case 8: {

					key1 = getKey(0);
					val1 = getValue(0);
					key2 = getKey(1);
					val2 = getValue(1);
					key3 = getKey(2);
					val3 = getValue(2);
					key4 = getKey(3);
					val4 = getValue(3);
					key5 = getKey(4);
					val5 = getValue(4);
					key6 = getKey(5);
					val6 = getValue(5);
					key7 = getKey(6);
					val7 = getValue(6);
					key8 = getKey(7);
					val8 = getValue(7);
					break;
				}
				default:
					throw new IllegalStateException("Index out of range.");
				}

				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, key8, val8);

			}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

	}

	private static final class HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMapNode {
		private final int[] keys;
		private final int[] vals;
		private final int hash;

		HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue(final int hash, final int[] keys,
						final int[] vals) {
			this.keys = keys;
			this.vals = vals;
			this.hash = hash;

			assert payloadArity() >= 2;
		}

		@Override
		boolean containsKey(final int key, final int keyHash, final int shift) {

			if (this.hash == keyHash) {
				for (int k : keys) {
					if (k == key) {
						return true;
					}
				}
			}
			return false;

		}

		@Override
		boolean containsKey(final int key, final int keyHash, final int shift,
						final Comparator<Object> cmp) {

			if (this.hash == keyHash) {
				for (int k : keys) {
					if (k == key) {
						return true;
					}
				}
			}
			return false;

		}

		@Override
		Optional<java.lang.Integer> findByKey(final int key, final int keyHash, final int shift) {

			for (int i = 0; i < keys.length; i++) {
				final int _key = keys[i];
				if (key == _key) {
					final int _val = vals[i];
					return Optional.of(_val);
				}
			}
			return Optional.empty();

		}

		@Override
		Optional<java.lang.Integer> findByKey(final int key, final int keyHash, final int shift,
						final Comparator<Object> cmp) {

			for (int i = 0; i < keys.length; i++) {
				final int _key = keys[i];
				if (key == _key) {
					final int _val = vals[i];
					return Optional.of(_val);
				}
			}
			return Optional.empty();

		}

		@Override
		CompactMapNode updated(final AtomicReference<Thread> mutator, final int key, final int val,
						final int keyHash, final int shift, final Result details) {
			if (this.hash != keyHash) {
				details.modified();
				return mergeNodes(this, this.hash, key, val, keyHash, shift);
			}

			for (int idx = 0; idx < keys.length; idx++) {
				if (keys[idx] == key) {

					final int currentVal = vals[idx];

					if (currentVal == val) {
						return this;
					}

					final int[] src = this.vals;
					final int[] dst = new int[src.length];

					// copy 'src' and set 1 element(s) at position 'idx'
					System.arraycopy(src, 0, dst, 0, src.length);
					dst[idx + 0] = val;

					final CompactMapNode thisNew = new HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue(
									this.hash, this.keys, dst);

					details.updated(currentVal);
					return thisNew;

				}
			}

			final int[] keysNew = new int[this.keys.length + 1];

			// copy 'this.keys' and insert 1 element(s) at position
			// 'keys.length'
			System.arraycopy(this.keys, 0, keysNew, 0, keys.length);
			keysNew[keys.length + 0] = key;
			System.arraycopy(this.keys, keys.length, keysNew, keys.length + 1, this.keys.length
							- keys.length);

			final int[] valsNew = new int[this.vals.length + 1];

			// copy 'this.vals' and insert 1 element(s) at position
			// 'vals.length'
			System.arraycopy(this.vals, 0, valsNew, 0, vals.length);
			valsNew[vals.length + 0] = val;
			System.arraycopy(this.vals, vals.length, valsNew, vals.length + 1, this.vals.length
							- vals.length);

			details.modified();
			return new HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue(keyHash, keysNew,
							valsNew);
		}

		@Override
		CompactMapNode updated(final AtomicReference<Thread> mutator, final int key, final int val,
						final int keyHash, final int shift, final Result details,
						final Comparator<Object> cmp) {
			if (this.hash != keyHash) {
				details.modified();
				return mergeNodes(this, this.hash, key, val, keyHash, shift);
			}

			for (int idx = 0; idx < keys.length; idx++) {
				if (keys[idx] == key) {

					final int currentVal = vals[idx];

					if (currentVal == val) {
						return this;
					}

					final int[] src = this.vals;
					final int[] dst = new int[src.length];

					// copy 'src' and set 1 element(s) at position 'idx'
					System.arraycopy(src, 0, dst, 0, src.length);
					dst[idx + 0] = val;

					final CompactMapNode thisNew = new HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue(
									this.hash, this.keys, dst);

					details.updated(currentVal);
					return thisNew;

				}
			}

			final int[] keysNew = new int[this.keys.length + 1];

			// copy 'this.keys' and insert 1 element(s) at position
			// 'keys.length'
			System.arraycopy(this.keys, 0, keysNew, 0, keys.length);
			keysNew[keys.length + 0] = key;
			System.arraycopy(this.keys, keys.length, keysNew, keys.length + 1, this.keys.length
							- keys.length);

			final int[] valsNew = new int[this.vals.length + 1];

			// copy 'this.vals' and insert 1 element(s) at position
			// 'vals.length'
			System.arraycopy(this.vals, 0, valsNew, 0, vals.length);
			valsNew[vals.length + 0] = val;
			System.arraycopy(this.vals, vals.length, valsNew, vals.length + 1, this.vals.length
							- vals.length);

			details.modified();
			return new HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue(keyHash, keysNew,
							valsNew);
		}

		@Override
		CompactMapNode removed(final AtomicReference<Thread> mutator, final int key,
						final int keyHash, final int shift, final Result details) {

			for (int idx = 0; idx < keys.length; idx++) {
				if (keys[idx] == key) {
					final int currentVal = vals[idx];
					details.updated(currentVal);

					if (this.arity() == 1) {
						return nodeOf(mutator);
					} else if (this.arity() == 2) {
						/*
						 * Create root node with singleton element. This node
						 * will be a) either be the new root returned, or b)
						 * unwrapped and inlined.
						 */
						final int theOtherKey = (idx == 0) ? keys[1] : keys[0];
						final int theOtherVal = (idx == 0) ? vals[1] : vals[0];
						return CompactMapNode.nodeOf(mutator).updated(mutator, theOtherKey,
										theOtherVal, keyHash, 0, details);
					} else {
						final int[] keysNew = new int[this.keys.length - 1];

						// copy 'this.keys' and remove 1 element(s) at position
						// 'idx'
						System.arraycopy(this.keys, 0, keysNew, 0, idx);
						System.arraycopy(this.keys, idx + 1, keysNew, idx, this.keys.length - idx
										- 1);

						final int[] valsNew = new int[this.vals.length - 1];

						// copy 'this.vals' and remove 1 element(s) at position
						// 'idx'
						System.arraycopy(this.vals, 0, valsNew, 0, idx);
						System.arraycopy(this.vals, idx + 1, valsNew, idx, this.vals.length - idx
										- 1);

						return new HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue(keyHash,
										keysNew, valsNew);
					}
				}
			}
			return this;

		}

		@Override
		CompactMapNode removed(final AtomicReference<Thread> mutator, final int key,
						final int keyHash, final int shift, final Result details,
						final Comparator<Object> cmp) {

			for (int idx = 0; idx < keys.length; idx++) {
				if (keys[idx] == key) {
					final int currentVal = vals[idx];
					details.updated(currentVal);

					if (this.arity() == 1) {
						return nodeOf(mutator);
					} else if (this.arity() == 2) {
						/*
						 * Create root node with singleton element. This node
						 * will be a) either be the new root returned, or b)
						 * unwrapped and inlined.
						 */
						final int theOtherKey = (idx == 0) ? keys[1] : keys[0];
						final int theOtherVal = (idx == 0) ? vals[1] : vals[0];
						return CompactMapNode.nodeOf(mutator).updated(mutator, theOtherKey,
										theOtherVal, keyHash, 0, details, cmp);
					} else {
						final int[] keysNew = new int[this.keys.length - 1];

						// copy 'this.keys' and remove 1 element(s) at position
						// 'idx'
						System.arraycopy(this.keys, 0, keysNew, 0, idx);
						System.arraycopy(this.keys, idx + 1, keysNew, idx, this.keys.length - idx
										- 1);

						final int[] valsNew = new int[this.vals.length - 1];

						// copy 'this.vals' and remove 1 element(s) at position
						// 'idx'
						System.arraycopy(this.vals, 0, valsNew, 0, idx);
						System.arraycopy(this.vals, idx + 1, valsNew, idx, this.vals.length - idx
										- 1);

						return new HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue(keyHash,
										keysNew, valsNew);
					}
				}
			}
			return this;

		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return keys.length;
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		int arity() {
			return payloadArity();
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		int getKey(int index) {
			return keys[index];
		}

		@Override
		int getValue(int index) {
			return vals[index];
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			return entryOf(keys[index], vals[index]);
		}

		@Override
		public CompactMapNode getNode(int index) {
			throw new IllegalStateException("Is leaf node.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 0;
			result = prime * result + hash;
			result = prime * result + Arrays.hashCode(keys);
			result = prime * result + Arrays.hashCode(vals);
			return result;
		}

		@Override
		public boolean equals(Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}

			HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue that = (HashCollisionMapNode_5Bits_Spec0To8_IntKey_IntValue) other;

			if (hash != that.hash) {
				return false;
			}

			if (arity() != that.arity()) {
				return false;
			}

			/*
			 * Linear scan for each key, because of arbitrary element order.
			 */
			outerLoop: for (int i = 0; i < that.payloadArity(); i++) {
				final int otherKey = that.getKey(i);
				final int otherVal = that.getValue(i);

				for (int j = 0; j < keys.length; j++) {
					final int key = keys[j];
					final int val = vals[j];

					if (key == otherKey && val == otherVal) {
						continue outerLoop;
					}
				}
				return false;
			}

			return true;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new UnsupportedOperationException();
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			throw new UnsupportedOperationException();
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new UnsupportedOperationException();
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new UnsupportedOperationException();
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new UnsupportedOperationException();
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new UnsupportedOperationException();
		}

		@Override
		CompactMapNode removeInplaceValueAndConvertToSpecializedNode(
						final AtomicReference<Thread> mutator, final int bitpos) {
			throw new UnsupportedOperationException();
		}

		@Override
		int nodeMap() {
			throw new UnsupportedOperationException();
		}

		@Override
		int dataMap() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Iterator skeleton that uses a fixed stack in depth.
	 */
	private static abstract class AbstractMapIterator {

		// TODO: verify maximum deepness
		private static final int MAX_DEPTH = 8;

		protected int currentValueCursor;
		protected int currentValueLength;
		protected AbstractMapNode currentValueNode;

		private int currentStackLevel;
		private final int[] nodeCursorsAndLengths = new int[MAX_DEPTH * 2];

		@SuppressWarnings("unchecked")
		AbstractMapNode[] nodes = new AbstractMapNode[MAX_DEPTH];

		AbstractMapIterator(AbstractMapNode rootNode) {
			currentStackLevel = 0;

			currentValueNode = rootNode;
			currentValueCursor = 0;
			currentValueLength = rootNode.payloadArity();

			nodes[0] = rootNode;
			nodeCursorsAndLengths[0] = 0;
			nodeCursorsAndLengths[1] = rootNode.nodeArity();
		}

		public boolean hasNext() {
			if (currentValueCursor < currentValueLength) {
				return true;
			} else {
				/*
				 * search for next node that contains values
				 */
				while (currentStackLevel >= 0) {
					final int currentCursorIndex = currentStackLevel * 2;
					final int currentLengthIndex = currentCursorIndex + 1;

					final int nodeCursor = nodeCursorsAndLengths[currentCursorIndex];
					final int nodeLength = nodeCursorsAndLengths[currentLengthIndex];

					if (nodeCursor < nodeLength) {
						final AbstractMapNode nextNode = nodes[currentStackLevel]
										.getNode(nodeCursor);
						nodeCursorsAndLengths[currentCursorIndex]++;

						if (nextNode.hasNodes()) {
							/*
							 * put node on next stack level for depth-first
							 * traversal
							 */
							final int nextStackLevel = ++currentStackLevel;
							final int nextCursorIndex = nextStackLevel * 2;
							final int nextLengthIndex = nextCursorIndex + 1;

							nodes[nextStackLevel] = nextNode;
							nodeCursorsAndLengths[nextCursorIndex] = 0;
							nodeCursorsAndLengths[nextLengthIndex] = nextNode.nodeArity();
						}

						if (nextNode.hasPayload()) {
							/*
							 * found for next node that contains values
							 */
							currentValueNode = nextNode;
							currentValueCursor = 0;
							currentValueLength = nextNode.payloadArity();
							return true;
						}
					} else {
						currentStackLevel--;
					}
				}
			}

			return false;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private static final class MapKeyIterator extends AbstractMapIterator implements
					SupplierIterator<java.lang.Integer, java.lang.Integer> {

		MapKeyIterator(AbstractMapNode rootNode) {
			super(rootNode);
		}

		@Override
		public java.lang.Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				return currentValueNode.getKey(currentValueCursor++);
			}
		}

		@Override
		public java.lang.Integer get() {
			throw new UnsupportedOperationException();
		}
	}

	private static final class MapValueIterator extends AbstractMapIterator implements
					SupplierIterator<java.lang.Integer, java.lang.Integer> {

		MapValueIterator(AbstractMapNode rootNode) {
			super(rootNode);
		}

		@Override
		public java.lang.Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				return currentValueNode.getValue(currentValueCursor++);
			}
		}

		@Override
		public java.lang.Integer get() {
			throw new UnsupportedOperationException();
		}
	}

	private static final class MapEntryIterator extends AbstractMapIterator
					implements
					SupplierIterator<Map.Entry<java.lang.Integer, java.lang.Integer>, java.lang.Integer> {

		MapEntryIterator(AbstractMapNode rootNode) {
			super(rootNode);
		}

		@Override
		public Map.Entry<java.lang.Integer, java.lang.Integer> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				return currentValueNode.getKeyValueEntry(currentValueCursor++);
			}
		}

		@Override
		public java.lang.Integer get() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Iterator that first iterates over inlined-values and then continues depth
	 * first recursively.
	 */
	private static class TrieMap_5Bits_Spec0To8_IntKey_IntValueNodeIterator implements
					Iterator<AbstractMapNode> {

		final Deque<Iterator<? extends AbstractMapNode>> nodeIteratorStack;

		TrieMap_5Bits_Spec0To8_IntKey_IntValueNodeIterator(AbstractMapNode rootNode) {
			nodeIteratorStack = new ArrayDeque<>();
			nodeIteratorStack.push(Collections.singleton(rootNode).iterator());
		}

		@Override
		public boolean hasNext() {
			while (true) {
				if (nodeIteratorStack.isEmpty()) {
					return false;
				} else {
					if (nodeIteratorStack.peek().hasNext()) {
						return true;
					} else {
						nodeIteratorStack.pop();
						continue;
					}
				}
			}
		}

		@Override
		public AbstractMapNode next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			AbstractMapNode innerNode = nodeIteratorStack.peek().next();

			if (innerNode.hasNodes()) {
				nodeIteratorStack.push(innerNode.nodeIterator());
			}

			return innerNode;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	static final class TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue extends
					AbstractMap<java.lang.Integer, java.lang.Integer> implements
					TransientMap<java.lang.Integer, java.lang.Integer> {
		final private AtomicReference<Thread> mutator;
		private AbstractMapNode rootNode;
		private int hashCode;
		private int cachedSize;

		TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue(
						TrieMap_5Bits_Spec0To8_IntKey_IntValue trieMap_5Bits_Spec0To8_IntKey_IntValue) {
			this.mutator = new AtomicReference<Thread>(Thread.currentThread());
			this.rootNode = trieMap_5Bits_Spec0To8_IntKey_IntValue.rootNode;
			this.hashCode = trieMap_5Bits_Spec0To8_IntKey_IntValue.hashCode;
			this.cachedSize = trieMap_5Bits_Spec0To8_IntKey_IntValue.cachedSize;
			if (DEBUG) {
				assert checkHashCodeAndSize(hashCode, cachedSize);
			}
		}

		private boolean checkHashCodeAndSize(final int targetHash, final int targetSize) {
			int hash = 0;
			int size = 0;

			for (Iterator<Map.Entry<java.lang.Integer, java.lang.Integer>> it = entryIterator(); it
							.hasNext();) {
				final Map.Entry<java.lang.Integer, java.lang.Integer> entry = it.next();
				final int key = entry.getKey();
				final int val = entry.getValue();

				hash += (int) key ^ (int) val;
				size += 1;
			}

			return hash == targetHash && size == targetSize;
		}

		@Override
		public boolean containsKey(Object o) {
			try {
				@SuppressWarnings("unchecked")
				final int key = (int) o;
				return rootNode.containsKey(key, (int) key, 0);
			} catch (ClassCastException unused) {
				return false;
			}
		}

		@Override
		public boolean containsKeyEquivalent(Object o, Comparator<Object> cmp) {
			try {
				@SuppressWarnings("unchecked")
				final int key = (int) o;
				return rootNode.containsKey(key, (int) key, 0, cmp);
			} catch (ClassCastException unused) {
				return false;
			}
		}

		@Override
		public java.lang.Integer get(Object o) {
			try {
				@SuppressWarnings("unchecked")
				final int key = (int) o;
				final Optional<java.lang.Integer> result = rootNode.findByKey(key, (int) key, 0);

				if (result.isPresent()) {
					return result.get();
				} else {
					return null;
				}
			} catch (ClassCastException unused) {
				return null;
			}
		}

		@Override
		public java.lang.Integer getEquivalent(Object o, Comparator<Object> cmp) {
			try {
				@SuppressWarnings("unchecked")
				final int key = (int) o;
				final Optional<java.lang.Integer> result = rootNode.findByKey(key, (int) key, 0,
								cmp);

				if (result.isPresent()) {
					return result.get();
				} else {
					return null;
				}
			} catch (ClassCastException unused) {
				return null;
			}
		}

		@Override
		public java.lang.Integer __put(final java.lang.Integer key, final java.lang.Integer val) {
			if (mutator.get() == null) {
				throw new IllegalStateException("Transient already frozen.");
			}

			final int keyHash = key.hashCode();
			final Result details = Result.unchanged();

			final CompactMapNode newRootNode = rootNode.updated(mutator, key, val, keyHash, 0,
							details);

			if (details.isModified()) {
				rootNode = newRootNode;

				if (details.hasReplacedValue()) {
					final int old = details.getReplacedValue();

					final int valHashOld = (int) old;
					final int valHashNew = (int) val;

					hashCode += keyHash ^ valHashNew;
					hashCode -= keyHash ^ valHashOld;
					// cachedSize remains same

					if (DEBUG) {
						assert checkHashCodeAndSize(hashCode, cachedSize);
					}
					return old;
				} else {
					final int valHashNew = (int) val;

					hashCode += keyHash ^ valHashNew;
					cachedSize += 1;

					if (DEBUG) {
						assert checkHashCodeAndSize(hashCode, cachedSize);
					}
					return null;
				}
			}

			if (DEBUG) {
				assert checkHashCodeAndSize(hashCode, cachedSize);
			}
			return null;
		}

		@Override
		public java.lang.Integer __putEquivalent(final java.lang.Integer key,
						final java.lang.Integer val, final Comparator<Object> cmp) {
			if (mutator.get() == null) {
				throw new IllegalStateException("Transient already frozen.");
			}

			final int keyHash = key.hashCode();
			final Result details = Result.unchanged();

			final CompactMapNode newRootNode = rootNode.updated(mutator, key, val, keyHash, 0,
							details, cmp);

			if (details.isModified()) {
				rootNode = newRootNode;

				if (details.hasReplacedValue()) {
					final int old = details.getReplacedValue();

					final int valHashOld = (int) old;
					final int valHashNew = (int) val;

					hashCode += keyHash ^ valHashNew;
					hashCode -= keyHash ^ valHashOld;
					// cachedSize remains same

					if (DEBUG) {
						assert checkHashCodeAndSize(hashCode, cachedSize);
					}
					return old;
				} else {
					final int valHashNew = (int) val;

					hashCode += keyHash ^ valHashNew;
					cachedSize += 1;

					if (DEBUG) {
						assert checkHashCodeAndSize(hashCode, cachedSize);
					}
					return null;
				}
			}

			if (DEBUG) {
				assert checkHashCodeAndSize(hashCode, cachedSize);
			}
			return null;
		}

		@Override
		public boolean __putAll(
						final Map<? extends java.lang.Integer, ? extends java.lang.Integer> map) {
			boolean modified = false;

			for (Entry<? extends java.lang.Integer, ? extends java.lang.Integer> entry : map
							.entrySet()) {
				final boolean isPresent = containsKey(entry.getKey());
				final java.lang.Integer replaced = __put(entry.getKey(), entry.getValue());

				if (!isPresent || replaced != null) {
					modified = true;
				}
			}

			return modified;
		}

		@Override
		public boolean __putAllEquivalent(
						final Map<? extends java.lang.Integer, ? extends java.lang.Integer> map,
						final Comparator<Object> cmp) {
			boolean modified = false;

			for (Entry<? extends java.lang.Integer, ? extends java.lang.Integer> entry : map
							.entrySet()) {
				final boolean isPresent = containsKeyEquivalent(entry.getKey(), cmp);
				final java.lang.Integer replaced = __putEquivalent(entry.getKey(),
								entry.getValue(), cmp);

				if (!isPresent || replaced != null) {
					modified = true;
				}
			}

			return modified;
		}

		@Override
		public boolean __remove(final java.lang.Integer key) {
			if (mutator.get() == null) {
				throw new IllegalStateException("Transient already frozen.");

			}

			final int keyHash = key.hashCode();
			final Result details = Result.unchanged();

			final CompactMapNode newRootNode = rootNode.removed(mutator, key, keyHash, 0, details);

			if (details.isModified()) {

				assert details.hasReplacedValue();
				final int valHash = (int) details.getReplacedValue();

				rootNode = newRootNode;
				hashCode -= keyHash ^ valHash;
				cachedSize -= 1;

				if (DEBUG) {
					assert checkHashCodeAndSize(hashCode, cachedSize);
				}
				return true;

			}

			if (DEBUG) {
				assert checkHashCodeAndSize(hashCode, cachedSize);
			}
			return false;
		}

		@Override
		public boolean __removeEquivalent(final java.lang.Integer key, Comparator<Object> cmp) {
			if (mutator.get() == null) {
				throw new IllegalStateException("Transient already frozen.");
			}

			final int keyHash = key.hashCode();
			final Result details = Result.unchanged();

			final CompactMapNode newRootNode = rootNode.removed(mutator, key, keyHash, 0, details,
							cmp);

			if (details.isModified()) {

				assert details.hasReplacedValue();
				final int valHash = (int) details.getReplacedValue();

				rootNode = newRootNode;
				hashCode -= keyHash ^ valHash;
				cachedSize -= 1;

				if (DEBUG) {
					assert checkHashCodeAndSize(hashCode, cachedSize);
				}
				return true;

			}

			if (DEBUG) {
				assert checkHashCodeAndSize(hashCode, cachedSize);
			}
			return false;
		}

		@Override
		public Set<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>> entrySet() {
			Set<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>> entrySet = null;

			if (entrySet == null) {
				entrySet = new AbstractSet<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>>() {
					@Override
					public Iterator<java.util.Map.Entry<java.lang.Integer, java.lang.Integer>> iterator() {
						return new Iterator<Entry<java.lang.Integer, java.lang.Integer>>() {
							private final Iterator<Entry<java.lang.Integer, java.lang.Integer>> i = entryIterator();

							@Override
							public boolean hasNext() {
								return i.hasNext();
							}

							@Override
							public Entry<java.lang.Integer, java.lang.Integer> next() {
								return i.next();
							}

							@Override
							public void remove() {
								i.remove();
							}
						};
					}

					@Override
					public int size() {
						return TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue.this.size();
					}

					@Override
					public boolean isEmpty() {
						return TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue.this.isEmpty();
					}

					@Override
					public void clear() {
						TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue.this.clear();
					}

					@Override
					public boolean contains(Object k) {
						return TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue.this.containsKey(k);
					}
				};
			}
			return entrySet;
		}

		@Override
		public int size() {
			return cachedSize;
		}

		@Override
		public SupplierIterator<java.lang.Integer, java.lang.Integer> keyIterator() {
			return new TransientMapKeyIterator(this);
		}

		@Override
		public Iterator<java.lang.Integer> valueIterator() {
			// return new TrieMapValueIterator(keyIterator());
			return new MapValueIterator(rootNode); // TODO: iterator does not
													// support removal
		}

		@Override
		public Iterator<Map.Entry<java.lang.Integer, java.lang.Integer>> entryIterator() {
			// return new TrieMapEntryIterator(keyIterator());
			return new MapEntryIterator(rootNode); // TODO: iterator does not
													// support removal
		}

		/**
		 * Iterator that first iterates over inlined-values and then continues
		 * depth first recursively.
		 */
		private static class TransientMapKeyIterator extends AbstractMapIterator implements
						SupplierIterator<java.lang.Integer, java.lang.Integer> {

			final TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue transientTrieMap_5Bits_Spec0To8_IntKey_IntValue;
			java.lang.Integer lastKey;

			TransientMapKeyIterator(
							TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue transientTrieMap_5Bits_Spec0To8_IntKey_IntValue) {
				super(transientTrieMap_5Bits_Spec0To8_IntKey_IntValue.rootNode);
				this.transientTrieMap_5Bits_Spec0To8_IntKey_IntValue = transientTrieMap_5Bits_Spec0To8_IntKey_IntValue;
			}

			@Override
			public java.lang.Integer next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				} else {
					lastKey = currentValueNode.getKey(currentValueCursor++);
					return lastKey;
				}
			}

			@Override
			public java.lang.Integer get() {
				throw new UnsupportedOperationException();
			}

			/*
			 * TODO: test removal with iteration rigorously
			 */
			@Override
			public void remove() {
				boolean success = transientTrieMap_5Bits_Spec0To8_IntKey_IntValue.__remove(lastKey);

				if (!success) {
					throw new IllegalStateException("Key from iteration couldn't be deleted.");
				}
			}
		}

		@Override
		public boolean equals(Object other) {
			if (other == this) {
				return true;
			}
			if (other == null) {
				return false;
			}

			if (other instanceof TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue) {
				TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue that = (TransientTrieMap_5Bits_Spec0To8_IntKey_IntValue) other;

				if (this.size() != that.size()) {
					return false;
				}

				return rootNode.equals(that.rootNode);
			}

			return super.equals(other);
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public ImmutableMap<java.lang.Integer, java.lang.Integer> freeze() {
			if (mutator.get() == null) {
				throw new IllegalStateException("Transient already frozen.");
			}

			mutator.set(null);
			return new TrieMap_5Bits_Spec0To8_IntKey_IntValue(rootNode, hashCode, cachedSize);
		}
	}

	private static final class Map0To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactEmptyMapNode {

		Map0To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap) {
			super(mutator, nodeMap, dataMap);

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_EMPTY;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			int result = 1;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}

			return true;
		}

	}

	private static final class Map0To1Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactNodesOnlyMapNode {

		private final CompactMapNode node1;

		Map0To1Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1) {
			super(mutator, nodeMap, dataMap);
			this.node1 = node1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 1;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + node1.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map0To1Node_5Bits_Spec0To8_IntKey_IntValue that = (Map0To1Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(node1.equals(that.node1))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map0To2Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactNodesOnlyMapNode {

		private final CompactMapNode node1;
		private final CompactMapNode node2;

		Map0To2Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2) {
			super(mutator, nodeMap, dataMap);
			this.node1 = node1;
			this.node2 = node2;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 2;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, node1, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map0To2Node_5Bits_Spec0To8_IntKey_IntValue that = (Map0To2Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map0To3Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactNodesOnlyMapNode {

		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;

		Map0To3Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3) {
			super(mutator, nodeMap, dataMap);
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 3;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, node1, node, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map0To3Node_5Bits_Spec0To8_IntKey_IntValue that = (Map0To3Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map0To4Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactNodesOnlyMapNode {

		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;

		Map0To4Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4) {
			super(mutator, nodeMap, dataMap);
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 4;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node, node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, node1, node, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node2, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map0To4Node_5Bits_Spec0To8_IntKey_IntValue that = (Map0To4Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map0To5Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactNodesOnlyMapNode {

		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;

		Map0To5Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5) {
			super(mutator, nodeMap, dataMap);
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 5;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
								node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node, node2, node3, node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, node1, node, node3, node4, node5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node4, node5);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node, node5);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node2, node3, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node3, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map0To5Node_5Bits_Spec0To8_IntKey_IntValue that = (Map0To5Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map0To6Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactNodesOnlyMapNode {

		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;
		private final CompactMapNode node6;

		Map0To6Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6) {
			super(mutator, nodeMap, dataMap);
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;
			this.node6 = node6;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			case 5:
				return node6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 6;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
								node5, node6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node, node2, node3, node4, node5, node6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, node1, node, node3, node4, node5, node6);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node4, node5, node6);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node, node5, node6);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node, node6);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node2, node3, node4, node5,
									node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node3, node4, node5,
									node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node4, node5,
									node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node5,
									node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();
			result = prime * result + node6.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map0To6Node_5Bits_Spec0To8_IntKey_IntValue that = (Map0To6Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}
			if (!(node6.equals(that.node6))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map0To7Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactNodesOnlyMapNode {

		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;
		private final CompactMapNode node6;
		private final CompactMapNode node7;

		Map0To7Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6, final CompactMapNode node7) {
			super(mutator, nodeMap, dataMap);
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;
			this.node6 = node6;
			this.node7 = node7;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			case 5:
				return node6;
			case 6:
				return node7;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 7;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
								node5, node6, node7);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node, node2, node3, node4, node5, node6,
								node7);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, node1, node, node3, node4, node5, node6,
								node7);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node4, node5, node6,
								node7);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node, node5, node6,
								node7);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node, node6,
								node7);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node,
								node7);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node6,
								node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node2, node3, node4, node5,
									node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node3, node4, node5,
									node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node4, node5,
									node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node5,
									node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node5, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 6:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();
			result = prime * result + node6.hashCode();
			result = prime * result + node7.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map0To7Node_5Bits_Spec0To8_IntKey_IntValue that = (Map0To7Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}
			if (!(node6.equals(that.node6))) {
				return false;
			}
			if (!(node7.equals(that.node7))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map0To8Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactNodesOnlyMapNode {

		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;
		private final CompactMapNode node6;
		private final CompactMapNode node7;
		private final CompactMapNode node8;

		Map0To8Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6, final CompactMapNode node7,
						final CompactMapNode node8) {
			super(mutator, nodeMap, dataMap);
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;
			this.node6 = node6;
			this.node7 = node7;
			this.node8 = node8;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			case 5:
				return node6;
			case 6:
				return node7;
			case 7:
				return node8;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getValue(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 8;
		}

		@Override
		boolean hasPayload() {
			return false;
		}

		@Override
		int payloadArity() {
			return 0;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
								node5, node6, node7, node8);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node, node2, node3, node4, node5, node6,
								node7, node8);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, node1, node, node3, node4, node5, node6,
								node7, node8);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node4, node5, node6,
								node7, node8);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node, node5, node6,
								node7, node8);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node, node6,
								node7, node8);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node,
								node7, node8);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node6,
								node, node8);
			case 7:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node6,
								node7, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node2, node3, node4, node5,
									node6, node7, node8);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node3, node4, node5,
									node6, node7, node8);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node4, node5,
									node6, node7, node8);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node5,
									node6, node7, node8);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node6, node7, node8);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node5, node7, node8);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 6:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node5, node6, node8);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 7:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, node1, node2, node3, node4,
									node5, node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();
			result = prime * result + node6.hashCode();
			result = prime * result + node7.hashCode();
			result = prime * result + node8.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map0To8Node_5Bits_Spec0To8_IntKey_IntValue that = (Map0To8Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}
			if (!(node6.equals(that.node6))) {
				return false;
			}
			if (!(node7.equals(that.node7))) {
				return false;
			}
			if (!(node8.equals(that.node8))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map1To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactValuesOnlyMapNode {

		private final int key1;
		private final int val1;

		Map1To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 1;
		}

		@Override
		byte sizePredicate() {
			return SIZE_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map1To0Node_5Bits_Spec0To8_IntKey_IntValue that = (Map1To0Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}

			return true;
		}

	}

	private static final class Map1To1Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final CompactMapNode node1;

		Map1To1Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.node1 = node1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 1;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 1;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;

			result = prime * result + node1.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map1To1Node_5Bits_Spec0To8_IntKey_IntValue that = (Map1To1Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map1To2Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final CompactMapNode node1;
		private final CompactMapNode node2;

		Map1To2Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.node1 = node1;
			this.node2 = node2;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 2;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 1;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map1To2Node_5Bits_Spec0To8_IntKey_IntValue that = (Map1To2Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map1To3Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;

		Map1To3Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 3;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 1;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map1To3Node_5Bits_Spec0To8_IntKey_IntValue that = (Map1To3Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map1To4Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;

		Map1To4Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 4;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 1;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, node1, node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2, node3,
								node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2, node3,
								node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, node, node1, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, node1, node, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node2, node3,
									node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node2, node3,
									node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node3,
									node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node3,
									node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map1To4Node_5Bits_Spec0To8_IntKey_IntValue that = (Map1To4Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map1To5Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;

		Map1To5Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 5;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 1;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, node1, node2, node3, node4,
								node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2, node3,
								node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2, node3,
								node4, node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node2, node3, node4,
								node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node3, node4,
								node5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node, node4,
								node5);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node,
								node5);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, node, node1, node2, node3, node4,
									node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, node1, node, node2, node3, node4,
									node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node3, node4,
									node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node, node4,
									node5);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node,
									node5);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5,
									node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node2, node3,
									node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node2, node3,
									node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node3,
									node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node3,
									node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map1To5Node_5Bits_Spec0To8_IntKey_IntValue that = (Map1To5Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map1To6Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;
		private final CompactMapNode node6;

		Map1To6Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5, final CompactMapNode node6) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;
			this.node6 = node6;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			case 5:
				return node6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 6;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 1;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, node1, node2, node3, node4,
								node5, node6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2, node3,
								node4, node5, node6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2, node3,
								node4, node5, node6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node2, node3, node4,
								node5, node6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node3, node4,
								node5, node6);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node, node4,
								node5, node6);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node,
								node5, node6);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node, node6);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node5, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, node, node1, node2, node3, node4,
									node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, node1, node, node2, node3, node4,
									node5, node6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node3, node4,
									node5, node6);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node, node4,
									node5, node6);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node,
									node5, node6);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5,
									node, node6);
				case 6:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5,
									node6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node2, node3,
									node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node2, node3,
									node4, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node3,
									node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node3,
									node4, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node4, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node4, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node4, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();
			result = prime * result + node6.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map1To6Node_5Bits_Spec0To8_IntKey_IntValue that = (Map1To6Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}
			if (!(node6.equals(that.node6))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map1To7Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;
		private final CompactMapNode node6;
		private final CompactMapNode node7;

		Map1To7Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5, final CompactMapNode node6,
						final CompactMapNode node7) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;
			this.node6 = node6;
			this.node7 = node7;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			case 5:
				return node6;
			case 6:
				return node7;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 7;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 1;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, node1, node2, node3, node4,
								node5, node6, node7);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2, node3,
								node4, node5, node6, node7);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2, node3,
								node4, node5, node6, node7);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5, node6,
								node7);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node2, node3, node4,
								node5, node6, node7);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node3, node4,
								node5, node6, node7);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node, node4,
								node5, node6, node7);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node,
								node5, node6, node7);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node, node6, node7);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node5, node, node7);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node5, node6, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, node, node1, node2, node3, node4,
									node5, node6, node7);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, node1, node, node2, node3, node4,
									node5, node6, node7);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node, node3, node4,
									node5, node6, node7);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node, node4,
									node5, node6, node7);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node,
									node5, node6, node7);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5,
									node, node6, node7);
				case 6:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5,
									node6, node, node7);
				case 7:
					return nodeOf(mutator, nodeMap, dataMap, node1, node2, node3, node4, node5,
									node6, node7, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node2, node3,
									node4, node5, node6, node7);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node2, node3,
									node4, node5, node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node3,
									node4, node5, node6, node7);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node3,
									node4, node5, node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node4, node5, node6, node7);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node4, node5, node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node5, node6, node7);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node5, node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node4, node6, node7);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node4, node6, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node4, node5, node7);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node4, node5, node7);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 6:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, node1, node2,
									node3, node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, node1, node2,
									node3, node4, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();
			result = prime * result + node6.hashCode();
			result = prime * result + node7.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map1To7Node_5Bits_Spec0To8_IntKey_IntValue that = (Map1To7Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}
			if (!(node6.equals(that.node6))) {
				return false;
			}
			if (!(node7.equals(that.node7))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map2To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactValuesOnlyMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;

		Map2To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 2;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map2To0Node_5Bits_Spec0To8_IntKey_IntValue that = (Map2To0Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}

			return true;
		}

	}

	private static final class Map2To1Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final CompactMapNode node1;

		Map2To1Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.node1 = node1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 1;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 2;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;

			result = prime * result + node1.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map2To1Node_5Bits_Spec0To8_IntKey_IntValue that = (Map2To1Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map2To2Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final CompactMapNode node1;
		private final CompactMapNode node2;

		Map2To2Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.node1 = node1;
			this.node2 = node2;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 2;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 2;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, node1,
								node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, node1,
								node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, node1,
								node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map2To2Node_5Bits_Spec0To8_IntKey_IntValue that = (Map2To2Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map2To3Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;

		Map2To3Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 3;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 2;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, node1,
								node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, node1,
								node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, node1,
								node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map2To3Node_5Bits_Spec0To8_IntKey_IntValue that = (Map2To3Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map2To4Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;

		Map2To4Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 4;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 2;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, node1, node2,
								node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, node1, node2,
								node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, node1,
								node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, node1,
								node2, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, node1,
								node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node2,
								node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node,
								node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node, node1, node2, node3,
									node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node, node2, node3,
									node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node, node3,
									node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3, node,
									node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3,
									node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node1, node2, node3,
									node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node2, node3,
									node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node, node3,
									node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node,
									node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3,
									node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node2, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map2To4Node_5Bits_Spec0To8_IntKey_IntValue that = (Map2To4Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map2To5Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;

		Map2To5Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 5;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 2;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, node1, node2,
								node3, node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, node1, node2,
								node3, node4, node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, node1,
								node2, node3, node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, node1,
								node2, node3, node4, node5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, node1,
								node2, node3, node4, node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3, node4,
								node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node2,
								node3, node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node,
								node3, node4, node5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node, node4, node5);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node, node5);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node4, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node, node1, node2, node3,
									node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node, node2, node3,
									node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node, node3,
									node4, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3, node,
									node4, node5);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3,
									node4, node, node5);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3,
									node4, node5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node1, node2, node3,
									node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node2, node3,
									node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node, node3,
									node4, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node,
									node4, node5);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3,
									node4, node, node5);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3,
									node4, node5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node2, node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node2, node3, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node2, node3, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node3, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node3, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node3, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node3, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node3, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map2To5Node_5Bits_Spec0To8_IntKey_IntValue that = (Map2To5Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map2To6Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;
		private final CompactMapNode node6;

		Map2To6Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4, final CompactMapNode node5,
						final CompactMapNode node6) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;
			this.node6 = node6;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			case 5:
				return node6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 6;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 2;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, node1, node2,
								node3, node4, node5, node6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, node1, node2,
								node3, node4, node5, node6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, node1,
								node2, node3, node4, node5, node6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, node1,
								node2, node3, node4, node5, node6);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, node1,
								node2, node3, node4, node5, node6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3, node4,
								node5, node6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node4,
								node5, node6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node2,
								node3, node4, node5, node6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node,
								node3, node4, node5, node6);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node, node4, node5, node6);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node, node5, node6);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node4, node, node6);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node4, node5, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node, node1, node2, node3,
									node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node, node2, node3,
									node4, node5, node6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node, node3,
									node4, node5, node6);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3, node,
									node4, node5, node6);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3,
									node4, node, node5, node6);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3,
									node4, node5, node, node6);
				case 6:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, node1, node2, node3,
									node4, node5, node6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node, node1, node2, node3,
									node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node, node2, node3,
									node4, node5, node6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node, node3,
									node4, node5, node6);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3, node,
									node4, node5, node6);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3,
									node4, node, node5, node6);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3,
									node4, node5, node, node6);
				case 6:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, node1, node2, node3,
									node4, node5, node6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node2, node3, node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node2, node3, node4, node5, node6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node2, node3, node4, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node3, node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node3, node4, node5, node6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node3, node4, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node4, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node4, node5, node6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node4, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node3, node5, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node3, node5, node6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node3, node5, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node3, node4, node6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node3, node4, node6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node3, node4, node6);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									node1, node2, node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									node1, node2, node3, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									node1, node2, node3, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();
			result = prime * result + node6.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map2To6Node_5Bits_Spec0To8_IntKey_IntValue that = (Map2To6Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}
			if (!(node6.equals(that.node6))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map3To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactValuesOnlyMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;

		Map3To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 3;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map3To0Node_5Bits_Spec0To8_IntKey_IntValue that = (Map3To0Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}

			return true;
		}

	}

	private static final class Map3To1Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final CompactMapNode node1;

		Map3To1Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.node1 = node1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 1;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 3;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;

			result = prime * result + node1.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map3To1Node_5Bits_Spec0To8_IntKey_IntValue that = (Map3To1Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map3To2Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final CompactMapNode node1;
		private final CompactMapNode node2;

		Map3To2Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.node1 = node1;
			this.node2 = node2;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 2;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 3;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, node1,
								node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, node1,
								node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, node1,
								node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node,
								node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node, node1,
									node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node,
									node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node, node1,
									node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node,
									node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node1,
									node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node,
									node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node2);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map3To2Node_5Bits_Spec0To8_IntKey_IntValue that = (Map3To2Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map3To3Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;

		Map3To3Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 3;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 3;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, node1,
								node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, node1,
								node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, node1,
								node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, node1, node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, node1, node2, node3);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
								node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
								node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node,
								node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node, node1,
									node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node,
									node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node, node1,
									node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node,
									node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node1,
									node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node,
									node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node2, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node2);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map3To3Node_5Bits_Spec0To8_IntKey_IntValue that = (Map3To3Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map3To4Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;

		Map3To4Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 4;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 3;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, node1,
								node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, node1,
								node2, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, node1,
								node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, node1, node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, node1, node2, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, node1, node2, node3, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, node1, node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
								node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
								node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node,
								node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node3, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node, node1,
									node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node,
									node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node3, node, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node3, node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node, node1,
									node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node,
									node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node3, node, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node3, node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node1,
									node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node,
									node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node3, node, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node3, node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node2, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node2, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node2, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node2, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node2, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node2, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node2, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map3To4Node_5Bits_Spec0To8_IntKey_IntValue that = (Map3To4Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map3To5Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;
		private final CompactMapNode node5;

		Map3To5Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3, final CompactMapNode node4,
						final CompactMapNode node5) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;
			this.node5 = node5;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			case 4:
				return node5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 5;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 3;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, node1,
								node2, node3, node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, node1,
								node2, node3, node4, node5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, node1,
								node2, node3, node4, node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, node1, node2, node3, node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, node1, node2, node3, node4, node5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, node1, node2, node3, node4, node5);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, node1, node2, node3, node4, node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
								node3, node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
								node3, node4, node5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
								node3, node4, node5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node,
								node2, node3, node4, node5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node, node3, node4, node5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node, node4, node5);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node3, node, node5);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node3, node4, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node, node1,
									node2, node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node,
									node2, node3, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node, node3, node4, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node3, node, node4, node5);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node3, node4, node, node5);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, node1, node2,
									node3, node4, node5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node, node1,
									node2, node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node,
									node2, node3, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node, node3, node4, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node3, node, node4, node5);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node3, node4, node, node5);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, node1, node2,
									node3, node4, node5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node, node1,
									node2, node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node,
									node2, node3, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node, node3, node4, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node3, node, node4, node5);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node3, node4, node, node5);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, node1, node2,
									node3, node4, node5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node2, node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node2, node3, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node2, node3, node4, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node2, node3, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node3, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node3, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node3, node4, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node3, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node2, node4, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node2, node4, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node2, node4, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node2, node4, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node2, node3, node5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node2, node3, node5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node2, node3, node5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node2, node3, node5);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, node1, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, node1, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, node1, node2, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, node1, node2, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();
			result = prime * result + node5.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map3To5Node_5Bits_Spec0To8_IntKey_IntValue that = (Map3To5Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}
			if (!(node5.equals(that.node5))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map4To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactValuesOnlyMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;

		Map4To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 4;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map4To0Node_5Bits_Spec0To8_IntKey_IntValue that = (Map4To0Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}

			return true;
		}

	}

	private static final class Map4To1Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final CompactMapNode node1;

		Map4To1Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.node1 = node1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 1;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 4;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;

			result = prime * result + node1.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map4To1Node_5Bits_Spec0To8_IntKey_IntValue that = (Map4To1Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map4To2Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final CompactMapNode node1;
		private final CompactMapNode node2;

		Map4To2Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1,
						final CompactMapNode node2) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.node1 = node1;
			this.node2 = node2;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 2;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 4;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, node1, node2);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, node1,
								node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, node1,
								node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, node1,
								node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node2);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node2);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node1);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node1);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node1);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node1);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map4To2Node_5Bits_Spec0To8_IntKey_IntValue that = (Map4To2Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map4To3Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;

		Map4To3Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 3;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 4;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, node1, node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, node1, node2, node3);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, node1, node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, node1, node2, node3);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, node1, node2, node3);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, node1,
								node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, node1,
								node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, node1,
								node2, node3);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node2, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node2, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node2, node3);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node1, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node1, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node1, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node1, node3);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node1, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node1, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node1, node2);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node1, node2);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node1, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map4To3Node_5Bits_Spec0To8_IntKey_IntValue that = (Map4To3Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map4To4Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;
		private final CompactMapNode node4;

		Map4To4Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final CompactMapNode node1,
						final CompactMapNode node2, final CompactMapNode node3,
						final CompactMapNode node4) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;
			this.node4 = node4;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			case 3:
				return node4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 4;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 4;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, node1, node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, node1, node2, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, node1, node2, node3, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, node1, node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, node1, node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, node1, node2, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, node1, node2, node3, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, node1, node2, node3, node4);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, node1, node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, node1,
								node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, node1,
								node2, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, node1,
								node2, node3, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, node1,
								node2, node3, node4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node, node2, node3, node4);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node, node3, node4);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node2, node, node4);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node2, node3, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node, node1, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node2, node, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node2, node3, node, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									node1, node2, node3, node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node, node1, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node2, node, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node2, node3, node, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									node1, node2, node3, node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node, node1, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node2, node, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node2, node3, node, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									node1, node2, node3, node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node, node1, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node2, node, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node2, node3, node, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									node1, node2, node3, node4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node2, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node2, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node2, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node2, node3, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node2, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node1, node3, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node1, node3, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node1, node3, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node1, node3, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node1, node3, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node1, node2, node4);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node1, node2, node4);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node1, node2, node4);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node1, node2, node4);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node1, node2, node4);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, node1, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, node1, node2, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, node1, node2, node3);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, node1, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();
			result = prime * result + node4.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map4To4Node_5Bits_Spec0To8_IntKey_IntValue that = (Map4To4Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}
			if (!(node4.equals(that.node4))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map5To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactValuesOnlyMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;

		Map5To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 5;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map5To0Node_5Bits_Spec0To8_IntKey_IntValue that = (Map5To0Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}

			return true;
		}

	}

	private static final class Map5To1Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final CompactMapNode node1;

		Map5To1Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final CompactMapNode node1) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.node1 = node1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 1;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 5;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, node1);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;

			result = prime * result + node1.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map5To1Node_5Bits_Spec0To8_IntKey_IntValue that = (Map5To1Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map5To2Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final CompactMapNode node1;
		private final CompactMapNode node2;

		Map5To2Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final CompactMapNode node1, final CompactMapNode node2) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.node1 = node1;
			this.node2 = node2;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 2;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 5;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, node1, node2);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, node1, node2);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, node1, node2);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, node1, node2);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node1, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, node2);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, node2);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, node2);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, node1);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, node1);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, node1);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, node1);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, node1);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map5To2Node_5Bits_Spec0To8_IntKey_IntValue that = (Map5To2Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map5To3Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final CompactMapNode node1;
		private final CompactMapNode node2;
		private final CompactMapNode node3;

		Map5To3Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final CompactMapNode node1, final CompactMapNode node2,
						final CompactMapNode node3) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.node1 = node1;
			this.node2 = node2;
			this.node3 = node3;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			case 2:
				return node3;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 3;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 5;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, node1, node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, node1, node2, node3);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, node1, node2, node3);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, node1, node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, node1, node2, node3);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, node1, node2, node3);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, node1, node2, node3);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, node1, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, node1, node2, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, node1, node2, node3);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, node1, node2, node3);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, node1, node2, node3);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node, node2, node3);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node1, node, node3);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node1, node2, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node, node1, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node1, node, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node1, node2, node, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, node1, node2, node3, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, node2, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, node2, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, node2, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, node2, node3);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, node2, node3);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, node2, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, node1, node3);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, node1, node3);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, node1, node3);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, node1, node3);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, node1, node3);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, node1, node3);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, node1, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, node1, node2);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, node1, node2);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, node1, node2);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, node1, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();
			result = prime * result + node3.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map5To3Node_5Bits_Spec0To8_IntKey_IntValue that = (Map5To3Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}
			if (!(node3.equals(that.node3))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map6To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactValuesOnlyMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final int key6;
		private final int val6;

		Map6To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.key6 = key6;
			this.val6 = val6;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			case 5:
				return key6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			case 5:
				return val6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			case 5:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key6,
								val6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 6;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, key6, val6);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, key6, val6);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, key6, val6);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, key6, val6);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, key6, val6);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, key6, val6);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, key6, val6);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, key6, val6);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, key6, val6);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, key6, val6);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, key6, val6);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, key6, val6);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key6, val6);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;
			result = prime * result + (int) key6;
			result = prime * result + (int) val6;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map6To0Node_5Bits_Spec0To8_IntKey_IntValue that = (Map6To0Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(key6 == that.key6)) {
				return false;
			}
			if (!(val6 == that.val6)) {
				return false;
			}

			return true;
		}

	}

	private static final class Map6To1Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final int key6;
		private final int val6;
		private final CompactMapNode node1;

		Map6To1Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final CompactMapNode node1) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.key6 = key6;
			this.val6 = val6;
			this.node1 = node1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			case 5:
				return key6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			case 5:
				return val6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			case 5:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key6,
								val6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 1;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 6;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, key6, val6, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, key6, val6, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, key6, val6, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, key6, val6, node1);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, key6, val6, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, key6, val6, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, key6, val6, node1);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, key6, val6, node1);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, key6, val6, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, key6, val6, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, key6, val6, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, key6, val6, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key6, val6, node1);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, key6, val6);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, key6, val6);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, key6, val6);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, key6, val6);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, key6, val6);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, key6, val6);
				case 6:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, key, val);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;
			result = prime * result + (int) key6;
			result = prime * result + (int) val6;

			result = prime * result + node1.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map6To1Node_5Bits_Spec0To8_IntKey_IntValue that = (Map6To1Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(key6 == that.key6)) {
				return false;
			}
			if (!(val6 == that.val6)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map6To2Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final int key6;
		private final int val6;
		private final CompactMapNode node1;
		private final CompactMapNode node2;

		Map6To2Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final CompactMapNode node1,
						final CompactMapNode node2) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.key6 = key6;
			this.val6 = val6;
			this.node1 = node1;
			this.node2 = node2;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			case 1:
				return node2;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			case 5:
				return key6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			case 5:
				return val6;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			case 5:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key6,
								val6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 2;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 6;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, key6, val6, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, key6, val6, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, key6, val6, node1, node2);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, key6, val6, node1, node2);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, key6, val6, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, key6, val6, node1, node2);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, key6, val6, node1, node2);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, key6, val6, node1, node2);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key, val, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, key6, val6, node1, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, key6, val6, node1, node2);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, key6, val6, node1, node2);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, key6, val6, node1, node2);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key6, val6, node1, node2);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, node1, node2);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, node, node2);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, node1, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, node, node1, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, node1, node, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, node1, node2, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, key6, val6, node2);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, key6, val6, node2);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, key6, val6, node2);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, key6, val6, node2);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, key6, val6, node2);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, key6, val6, node2);
				case 6:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, key, val, node2);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, key6, val6, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, key6, val6, node1);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, key6, val6, node1);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, key6, val6, node1);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, key6, val6, node1);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, key6, val6, node1);
				case 6:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, key, val, node1);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;
			result = prime * result + (int) key6;
			result = prime * result + (int) val6;

			result = prime * result + node1.hashCode();
			result = prime * result + node2.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map6To2Node_5Bits_Spec0To8_IntKey_IntValue that = (Map6To2Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(key6 == that.key6)) {
				return false;
			}
			if (!(val6 == that.val6)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}
			if (!(node2.equals(that.node2))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map7To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactValuesOnlyMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final int key6;
		private final int val6;
		private final int key7;
		private final int val7;

		Map7To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.key6 = key6;
			this.val6 = val6;
			this.key7 = key7;
			this.val7 = val7;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			case 5:
				return key6;
			case 6:
				return key7;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			case 5:
				return val6;
			case 6:
				return val7;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			case 5:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key6,
								val6);
			case 6:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key7,
								val7);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 7;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, key6, val6, key7, val7);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, key6, val6, key7, val7);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, key6, val6, key7, val7);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val, key7, val7);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, key6, val6, key7, val7);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, key6, val6, key7, val7);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, key6, val6, key7, val7);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key, val, key7, val7);
			case 7:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, key, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, key6, val6, key7, val7);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, key6, val6, key7, val7);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, key6, val6, key7, val7);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, key6, val6, key7, val7);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key6, val6, key7, val7);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key7, val7);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, key7, val7, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, key7, val7, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, key7, val7, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, key7, val7, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, key7, val7, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key7, val7, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 6:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;
			result = prime * result + (int) key6;
			result = prime * result + (int) val6;
			result = prime * result + (int) key7;
			result = prime * result + (int) val7;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map7To0Node_5Bits_Spec0To8_IntKey_IntValue that = (Map7To0Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(key6 == that.key6)) {
				return false;
			}
			if (!(val6 == that.val6)) {
				return false;
			}
			if (!(key7 == that.key7)) {
				return false;
			}
			if (!(val7 == that.val7)) {
				return false;
			}

			return true;
		}

	}

	private static final class Map7To1Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactMixedMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final int key6;
		private final int val6;
		private final int key7;
		private final int val7;
		private final CompactMapNode node1;

		Map7To1Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7,
						final CompactMapNode node1) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.key6 = key6;
			this.val6 = val6;
			this.key7 = key7;
			this.val7 = val7;
			this.node1 = node1;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			switch (index) {
			case 0:
				return node1;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			case 5:
				return key6;
			case 6:
				return key7;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			case 5:
				return val6;
			case 6:
				return val7;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			case 5:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key6,
								val6);
			case 6:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key7,
								val7);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return true;
		}

		@Override
		int nodeArity() {
			return 1;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 7;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, key6, val6, key7, val7, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, key6, val6, key7, val7, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, key6, val6, key7, val7, node1);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val, key7, val7, node1);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, key6, val6, key7, val7, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, key6, val6, key7, val7, node1);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, key6, val6, key7, val7, node1);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key, val, key7, val7, node1);
			case 7:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, key, val, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, key6, val6, key7, val7, node1);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, key6, val6, key7, val7, node1);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, key6, val6, key7, val7, node1);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, key6, val6, key7, val7, node1);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key6, val6, key7, val7, node1);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key7, val7, node1);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, node1);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			final int index = nodeIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (index) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, node);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, key7, val7, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, key7, val7, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, key7, val7, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, key7, val7, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, key7, val7, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, key7, val7, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, key7, val7, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, key7, val7, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, key7, val7, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, key7, val7, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key7, val7, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key7, val7, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 6:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, node, node1);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, node1, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() ^ bitpos);
			final int dataMap = (int) (this.dataMap() | bitpos);

			final int key = node.getKey(0);
			final int val = node.getValue(0);

			switch (bitIndex) {
			case 0:
				switch (valIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2,
									key3, val3, key4, val4, key5, val5, key6, val6, key7, val7);
				case 1:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2,
									key3, val3, key4, val4, key5, val5, key6, val6, key7, val7);
				case 2:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val,
									key3, val3, key4, val4, key5, val5, key6, val6, key7, val7);
				case 3:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key, val, key4, val4, key5, val5, key6, val6, key7, val7);
				case 4:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key, val, key5, val5, key6, val6, key7, val7);
				case 5:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key, val, key6, val6, key7, val7);
				case 6:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, key, val, key7, val7);
				case 7:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, key7, val7, key, val);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;
			result = prime * result + (int) key6;
			result = prime * result + (int) val6;
			result = prime * result + (int) key7;
			result = prime * result + (int) val7;

			result = prime * result + node1.hashCode();

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map7To1Node_5Bits_Spec0To8_IntKey_IntValue that = (Map7To1Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(key6 == that.key6)) {
				return false;
			}
			if (!(val6 == that.val6)) {
				return false;
			}
			if (!(key7 == that.key7)) {
				return false;
			}
			if (!(val7 == that.val7)) {
				return false;
			}
			if (!(node1.equals(that.node1))) {
				return false;
			}

			return true;
		}

	}

	private static final class Map8To0Node_5Bits_Spec0To8_IntKey_IntValue extends
					CompactValuesOnlyMapNode {

		private final int key1;
		private final int val1;
		private final int key2;
		private final int val2;
		private final int key3;
		private final int val3;
		private final int key4;
		private final int val4;
		private final int key5;
		private final int val5;
		private final int key6;
		private final int val6;
		private final int key7;
		private final int val7;
		private final int key8;
		private final int val8;

		Map8To0Node_5Bits_Spec0To8_IntKey_IntValue(final AtomicReference<Thread> mutator,
						final int nodeMap, final int dataMap, final int key1, final int val1,
						final int key2, final int val2, final int key3, final int val3,
						final int key4, final int val4, final int key5, final int val5,
						final int key6, final int val6, final int key7, final int val7,
						final int key8, final int val8) {
			super(mutator, nodeMap, dataMap);
			this.key1 = key1;
			this.val1 = val1;
			this.key2 = key2;
			this.val2 = val2;
			this.key3 = key3;
			this.val3 = val3;
			this.key4 = key4;
			this.val4 = val4;
			this.key5 = key5;
			this.val5 = val5;
			this.key6 = key6;
			this.val6 = val6;
			this.key7 = key7;
			this.val7 = val7;
			this.key8 = key8;
			this.val8 = val8;

			assert nodeInvariant();
		}

		@Override
		CompactMapNode getNode(int index) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		int getKey(int index) {
			switch (index) {
			case 0:
				return key1;
			case 1:
				return key2;
			case 2:
				return key3;
			case 3:
				return key4;
			case 4:
				return key5;
			case 5:
				return key6;
			case 6:
				return key7;
			case 7:
				return key8;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		int getValue(int index) {
			switch (index) {
			case 0:
				return val1;
			case 1:
				return val2;
			case 2:
				return val3;
			case 3:
				return val4;
			case 4:
				return val5;
			case 5:
				return val6;
			case 6:
				return val7;
			case 7:
				return val8;
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		Map.Entry<java.lang.Integer, java.lang.Integer> getKeyValueEntry(int index) {
			switch (index) {
			case 0:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key1,
								val1);
			case 1:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key2,
								val2);
			case 2:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key3,
								val3);
			case 3:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key4,
								val4);
			case 4:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key5,
								val5);
			case 5:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key6,
								val6);
			case 6:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key7,
								val7);
			case 7:
				return (java.util.Map.Entry<java.lang.Integer, java.lang.Integer>) entryOf(key8,
								val8);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		boolean hasNodes() {
			return false;
		}

		@Override
		int nodeArity() {
			return 0;
		}

		@Override
		boolean hasPayload() {
			return true;
		}

		@Override
		int payloadArity() {
			return 8;
		}

		@Override
		byte sizePredicate() {
			return SIZE_MORE_THAN_ONE;
		}

		@Override
		CompactMapNode copyAndSetValue(AtomicReference<Thread> mutator, final int bitpos,
						final int val) {
			final int idx = dataIndex(bitpos);

			final int nodeMap = this.nodeMap();
			final int dataMap = this.dataMap();

			switch (idx) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key1, val, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, key8, val8);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, key8, val8);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val, key4,
								val4, key5, val5, key6, val6, key7, val7, key8, val8);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val, key5, val5, key6, val6, key7, val7, key8, val8);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val, key6, val6, key7, val7, key8, val8);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val, key7, val7, key8, val8);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val, key8, val8);
			case 7:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, key8, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndInsertValue(AtomicReference<Thread> mutator, final int bitpos,
						final int key, final int val) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() | bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key, val, key1, val1, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7, key8, val8);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key, val, key2, val2, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7, key8, val8);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key, val, key3,
								val3, key4, val4, key5, val5, key6, val6, key7, val7, key8, val8);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key,
								val, key4, val4, key5, val5, key6, val6, key7, val7, key8, val8);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key, val, key5, val5, key6, val6, key7, val7, key8, val8);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key, val, key6, val6, key7, val7, key8, val8);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key, val, key7, val7, key8, val8);
			case 7:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, key, val, key8, val8);
			case 8:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7, key8, val8, key, val);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndRemoveValue(AtomicReference<Thread> mutator, final int bitpos) {
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap());
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4, key5,
								val5, key6, val6, key7, val7, key8, val8);
			case 1:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4, key5,
								val5, key6, val6, key7, val7, key8, val8);
			case 2:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4, key5,
								val5, key6, val6, key7, val7, key8, val8);
			case 3:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key5,
								val5, key6, val6, key7, val7, key8, val8);
			case 4:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key6, val6, key7, val7, key8, val8);
			case 5:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key7, val7, key8, val8);
			case 6:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key8, val8);
			case 7:
				return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3, key4,
								val4, key5, val5, key6, val6, key7, val7);
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndSetNode(AtomicReference<Thread> mutator, final int bitpos,
						CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		CompactMapNode copyAndMigrateFromInlineToNode(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			final int bitIndex = nodeIndex(bitpos);
			final int valIndex = dataIndex(bitpos);

			final int nodeMap = (int) (this.nodeMap() | bitpos);
			final int dataMap = (int) (this.dataMap() ^ bitpos);

			switch (valIndex) {
			case 0:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key2, val2, key3, val3, key4, val4,
									key5, val5, key6, val6, key7, val7, key8, val8, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 1:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key3, val3, key4, val4,
									key5, val5, key6, val6, key7, val7, key8, val8, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 2:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key4, val4,
									key5, val5, key6, val6, key7, val7, key8, val8, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 3:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key5, val5, key6, val6, key7, val7, key8, val8, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 4:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key6, val6, key7, val7, key8, val8, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 5:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key7, val7, key8, val8, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 6:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, key8, val8, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			case 7:
				switch (bitIndex) {
				case 0:
					return nodeOf(mutator, nodeMap, dataMap, key1, val1, key2, val2, key3, val3,
									key4, val4, key5, val5, key6, val6, key7, val7, node);
				default:
					throw new IllegalStateException("Index out of range.");
				}
			default:
				throw new IllegalStateException("Index out of range.");
			}
		}

		@Override
		CompactMapNode copyAndMigrateFromNodeToInline(final AtomicReference<Thread> mutator,
						final int bitpos, final CompactMapNode node) {
			throw new IllegalStateException("Index out of range.");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			result = prime * result + ((int) nodeMap());
			result = prime * result + ((int) dataMap());

			result = prime * result + (int) key1;
			result = prime * result + (int) val1;
			result = prime * result + (int) key2;
			result = prime * result + (int) val2;
			result = prime * result + (int) key3;
			result = prime * result + (int) val3;
			result = prime * result + (int) key4;
			result = prime * result + (int) val4;
			result = prime * result + (int) key5;
			result = prime * result + (int) val5;
			result = prime * result + (int) key6;
			result = prime * result + (int) val6;
			result = prime * result + (int) key7;
			result = prime * result + (int) val7;
			result = prime * result + (int) key8;
			result = prime * result + (int) val8;

			return result;
		}

		@Override
		public boolean equals(final java.lang.Object other) {
			if (null == other) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Map8To0Node_5Bits_Spec0To8_IntKey_IntValue that = (Map8To0Node_5Bits_Spec0To8_IntKey_IntValue) other;

			if (nodeMap() != that.nodeMap()) {
				return false;
			}
			if (dataMap() != that.dataMap()) {
				return false;
			}

			if (!(key1 == that.key1)) {
				return false;
			}
			if (!(val1 == that.val1)) {
				return false;
			}
			if (!(key2 == that.key2)) {
				return false;
			}
			if (!(val2 == that.val2)) {
				return false;
			}
			if (!(key3 == that.key3)) {
				return false;
			}
			if (!(val3 == that.val3)) {
				return false;
			}
			if (!(key4 == that.key4)) {
				return false;
			}
			if (!(val4 == that.val4)) {
				return false;
			}
			if (!(key5 == that.key5)) {
				return false;
			}
			if (!(val5 == that.val5)) {
				return false;
			}
			if (!(key6 == that.key6)) {
				return false;
			}
			if (!(val6 == that.val6)) {
				return false;
			}
			if (!(key7 == that.key7)) {
				return false;
			}
			if (!(val7 == that.val7)) {
				return false;
			}
			if (!(key8 == that.key8)) {
				return false;
			}
			if (!(val8 == that.val8)) {
				return false;
			}

			return true;
		}

	}

}