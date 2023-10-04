import java.util.*;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

public class MaxRatedWinnersCollector {
	private MaxRatedWinnersCollector() {} // hidden constructor

	public static <T> Collector<T, ?, List<T>> of(ToLongFunction<T> rater) {
		return Collector.of(
				() -> new RateAccumulator<T>(rater),
				RateAccumulator::accumulate,
				RateAccumulator::combine,
				RateAccumulator::getWinnersList,
				Characteristics.UNORDERED);
	}

	private static class RateAccumulator<T> {
		ToLongFunction<T> rater;
		List<T> listOfWinners = new ArrayList<>();
		long currentMax = Long.MIN_VALUE;

		public RateAccumulator(ToLongFunction<T> rater) {
			this.rater = rater;
		}

		public void accumulate(T value) {
			long rate = rater.applyAsLong(value);

			if (rate > currentMax) {
				currentMax = rate;
				listOfWinners.clear();
			}

			if (rate == currentMax)
				listOfWinners.add(value);
		}

		public List<T> getWinnersList() {
			return listOfWinners;
		}

		public RateAccumulator<T> combine(RateAccumulator<T> other) {
			if (Objects.equals(currentMax, other.currentMax))
				listOfWinners.addAll(other.listOfWinners);

			return currentMax >= other.currentMax ? this : other;
		}
	}
}
