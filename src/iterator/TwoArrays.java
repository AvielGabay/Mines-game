package iterator;

import java.util.Iterator;

public class TwoArrays implements Iterable<Integer> {
	private int[] a1, a2;

	public TwoArrays(int[] a1, int[] a2) {
		this.a1 = a1;
		this.a2 = a2;
	}

	private class Iterator1 implements Iterator<Integer> {
		int cnt1 = 0, cnt2 = 0;

		@Override
		public boolean hasNext() {
			return (cnt1 < a1.length || cnt2 < a2.length);
		}

		@Override
		public Integer next() {
			if (cnt1 == 0) return a1[cnt1++];
			if (cnt1 < a1.length && cnt2 < a2.length) {
				return (cnt1 > cnt2) ? a2[cnt2++] : a1[cnt1++];
			} else if (cnt1 >= a1.length) return a2[cnt2++];
			return a1[cnt1++];
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator1();
	}
}