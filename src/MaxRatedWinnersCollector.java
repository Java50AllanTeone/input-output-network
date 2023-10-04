import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

/**
 * This collector collects only those elements of Stream<T> 
 * which have the maximal rate due to specified 'rater' function.
 * The collector is built using static method MaxRatedWinnersCollector.of(...)
 */
public class MaxRatedWinnersCollector {
	private MaxRatedWinnersCollector() {} // hidden constructor

	/**
	 * Constructs the Collector using specified 'rater' instance
	 */
	public static <T> Collector<T, ?, List<T>> of(ToLongFunction<T> rater) {
		return Collector.of(
				() -> new RateAccumulator<T>(rater),
				RateAccumulator::accumulate,
				RateAccumulator::combine,
				RateAccumulator::getWinnersList,
				Characteristics.UNORDERED);
	}

	/**
	 * The internal accumulating class for implementation of Collector
	 */
	private static class RateAccumulator<T> {
		ToLongFunction<T> rater;
		List<T> list = new ArrayList<>();
		Long currentMax = Long.MIN_VALUE;

		/**
		 * Constructor
//		 * @param <T> rater - functional object, giving the long 'rate' value to each T object
		 */
		public RateAccumulator(ToLongFunction<T> rater) {
			this.rater = rater;
		}


		/**
		 * Accumulate 'champions', using the following algorithm:
		 * - calculates rate value, applying the 'rater' to specified 'value'
		 * - makes the decision:
		 *    - if rate < currentMax then do nothing
		 *    - if rate == currentMax then collect
		 *    - if rate > currentMax then make it new champion
		 * @param value - next element from stream
		 */
		public void accumulate(T value) {
			Long rate = rater.applyAsLong(value);

			if (rate > currentMax) {
				currentMax = rate;
				list.clear();
			}

			if (rate == currentMax)
				list.add(value);
		}

		/**
		 * Gets list of collected 'winners' - the rate champions
		 * @return returns list of winners (the T instances having max rate)
		 */
		public List<T> getWinnersList() {
			return list;
		}


		/**
		 * Updates this accumulator using data from other:
		 * - if one of rates are greater, then only greater remains
		 * - else this appends other's list of champions to it's own list
		 * @param other - other accumulator
		 * @return this accumulator
		 */
		public RateAccumulator<T> combine(RateAccumulator<T> other) {
			if (this.currentMax == other.currentMax) {
				this.list.addAll(other.list);
			} else if (other.currentMax > this.currentMax) {
				return other;
			}
				return this;
		}
	}
}
