package org.silnith.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Table<R, C, V> {

	private final Map<R, Map<C, V>> values;

	public Table() {
		super();
		this.values = new HashMap<>();
	}

	protected Map<C, V> getRow(final R r) {
		if (values.containsKey(r)) {
			return values.get(r);
		} else {
			final Map<C, V> emptyMap = new HashMap<>();
			values.put(r, emptyMap);
			return emptyMap;
		}
	}

	public boolean containsEntry(final R r, final C c) {
		return getRow(r).containsKey(c);
	}

	public V get(final R r, final C c) {
		return getRow(r).get(c);
	}

	public V put(final R r, final C c, final V v) {
		return getRow(r).put(c, v);
	}

	public V remove(final R r, final C c) {
		final Map<C, V> row = getRow(r);
		final V previousValue = row.remove(c);
		if (row.isEmpty()) {
			values.remove(r);
		}
		return previousValue;
	}

	public void printTable() {
		final Set<C> headings = new HashSet<>();
		for (final Map<C, V> value : values.values()) {
			headings.addAll(value.keySet());
		}
		System.out.print("Row");
		System.out.print('\t');
		final List<C> headingList = new ArrayList<>(headings);
		for (final C c : headingList) {
			System.out.print(c);
			System.out.print('\t');
		}
		System.out.println();
		for (final Map.Entry<R, Map<C, V>> rowEntry : values.entrySet()) {
			final R r = rowEntry.getKey();
			System.out.print(r);
			System.out.print('\t');
			final Map<C, V> row = rowEntry.getValue();
			for (final C column : headingList) {
				System.out.print(row.get(column));
				System.out.print('\t');
			}
			System.out.println();
		}
	}

	public void printTableLong() {
		System.out.println();
		for (final Map.Entry<R, Map<C, V>> rowEntry : values.entrySet()) {
			final R r = rowEntry.getKey();
			System.out.print("Row: ");
			System.out.println(r);
			for (final Map.Entry<C, V> entry : rowEntry.getValue().entrySet()) {
				final C c = entry.getKey();
				final V v = entry.getValue();
				System.out.print("Entry Key: ");
				System.out.println(c);
				System.out.print("Value: ");
				System.out.println(v);
			}
		}
	}

}
