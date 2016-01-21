package org.silnith.grammar;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IntegerSet extends AbstractSet<Integer> {

	private final List<Long> elements;

	public IntegerSet() {
		super();
		this.elements = new ArrayList<>();
//		this.elements.add(0L);
	}

	@Override
	public boolean contains(final Object o) {
		if (o instanceof Integer) {
			final Integer i = (Integer) o;
			final int arrayIndex = i >>> 6;
			if (arrayIndex >= elements.size()) {
				return false;
			}
			final int bitOffset = i & 0x3F;
			final long word = elements.get(arrayIndex);
			final long bitMask = 1L << bitOffset;
			return (word & bitMask) == bitMask;
		} else {
			return false;
		}
	}

	@Override
	public boolean add(final Integer e) {
		final int toAdd = e;
		if (toAdd < 0) {
			throw new IllegalArgumentException();
		}
		final int arrayIndex = toAdd >>> 6;
		while (elements.size() <= arrayIndex) {
			elements.add(0L);
		}
		final int bitOffset = toAdd & 0x3F;
		final long bitMaskToAdd = 1L << bitOffset;
		final long currentBitMask = elements.get(arrayIndex);
		if ((currentBitMask & bitMaskToAdd) == bitMaskToAdd) {
			return false;
		}
		final long newBitMask = currentBitMask | bitMaskToAdd;
		elements.set(arrayIndex, newBitMask);
		return true;
	}

	@Override
	public boolean remove(final Object o) {
		if (o instanceof Integer) {
			final int toRemove = (int) o;
			final int arrayIndex = toRemove >>> 6;
			if (arrayIndex >= elements.size()) {
				return false;
			}
			final int bitOffset = toRemove & 0x3F;
			final long bitMaskToRemove = 1L << bitOffset;
			final long currentBitMask = elements.get(arrayIndex);
			if ((currentBitMask & bitMaskToRemove) == 0) {
				return false;
			}
			final long newBitMask = currentBitMask & ~bitMaskToRemove;
			elements.set(arrayIndex, newBitMask);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		int count = 0;
		for (final long bitMask : elements) {
			count += Long.bitCount(bitMask);
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		for (final long word : elements) {
			if (word != 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void clear() {
		elements.clear();
	}

	@Override
	public boolean addAll(final Collection<? extends Integer> c) {
		if (c instanceof IntegerSet) {
			final int startSize = size();
			final IntegerSet other = (IntegerSet) c;
			final Iterator<Long> otherIter = other.elements.iterator();
			for (int i = 0; i < elements.size() && otherIter.hasNext(); i++) {
				final long otherWord = otherIter.next();
				final Long previousValue = elements.get(i);
				elements.set(i, previousValue | otherWord);
			}
			return startSize != size();
		} else {
			return super.addAll(c);
		}
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		if (c instanceof IntegerSet) {
			final int startSize = size();
			final IntegerSet other = (IntegerSet) c;
			final Iterator<Long> otherIter = other.elements.iterator();
			for (int i = 0; i < elements.size() && otherIter.hasNext(); i++) {
				final long otherWord = otherIter.next();
				final Long previousValue = elements.get(i);
				elements.set(i, previousValue & otherWord);
			}
			return startSize != size();
		} else {
			return super.removeAll(c);
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof IntegerSet) {
			final IntegerSet other = (IntegerSet) o;
			return elements.equals(other.elements);
		} else {
			return super.equals(o);
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iter();
	}

	private class Iter implements Iterator<Integer> {

		private int number;

		private Iter() {
			super();
			this.number = -1;
		}

		@Override
		public boolean hasNext() {
			int nextNumber = number;
			int arrayIndex;
			long bitMask;
			do {
				nextNumber++;
				arrayIndex = nextNumber >>> 6;
				if (arrayIndex >= elements.size()) {
					return false;
				}
				final int bitOffset = nextNumber & 0x3F;
				bitMask = 1L << bitOffset;
			} while ((elements.get(arrayIndex) & bitMask) == 0);
			return true;
		}

		private boolean advance() {
			int arrayIndex;
			long bitMask;
			do {
				number++;
				arrayIndex = number >>> 6;
				if (arrayIndex >= elements.size()) {
						return false;
				}
				final int bitOffset = number & 0x3F;
				bitMask = 1L << bitOffset;
			} while ((elements.get(arrayIndex) & bitMask) == 0);
			return true;
		}

		@Override
		public Integer next() {
			if ( !advance()) {
				throw new NoSuchElementException();
			}
			return number;
		}

		@Override
		public void remove() {
			IntegerSet.this.remove(number);
		}

	}

}
