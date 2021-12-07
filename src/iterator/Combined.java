package iterator;

import java.util.Iterator;

public class Combined<E> implements Iterable<E> {
	private Iterable<E> first, second;

	public Combined(Iterable<E> first, Iterable<E> second) {
		this.first = first;
		this.second = second;
	}

	private class Iterator2 implements Iterator<E> {
		int i = 0;
		Iterator<E> A = first.iterator(), B = second.iterator();

		@Override
		public boolean hasNext() {
			return (A.hasNext() || B.hasNext());
		}

		@Override
		public E next() {
			if (A.hasNext() && B.hasNext()) {
				if (i == 0) {
					i = 1;
					return A.next();
				}
				i = 0;
				return B.next();
			}
			if (A.hasNext()) {
				return A.next();
			} else return B.next();
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator2();
	}
}