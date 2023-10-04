import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
		List<T> list = new ArrayList<>();
		Long currentMax = Long.MIN_VALUE;

		public RateAccumulator(ToLongFunction<T> rater) {
			this.rater = rater;
		}


		public void accumulate(T value) {
			long rate = rater.applyAsLong(value);

			if (rate > currentMax) {
				currentMax = rate;
				list.clear();
			}

			if (rate == currentMax)
				list.add(value);
		}


		public List<T> getWinnersList() {
			return list;
		}


		public RateAccumulator<T> combine(RateAccumulator<T> other) {
			if (Objects.equals(currentMax, other.currentMax))
				list.addAll(other.list);

			return currentMax >= other.currentMax ? this : other;
		}
	}
}
