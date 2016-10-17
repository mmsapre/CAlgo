import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SlidingTimeWindowReservoir implements Reservoir {

	private final Timer clock;
	private final ConcurrentSkipListMap<Long, Long> distributions;
	private final long window;
	private final AtomicLong lastTick;
	private final AtomicLong count;

	private static final int COLLISION_BUFFER = 256;
	private static final int TRIM_THRESHOLD = 256;

	public SlidingTimeWindowReservoir(long window, TimeUnit windowUnit) {
		this(window, windowUnit, Timer.defaultClock());
	}

	public SlidingTimeWindowReservoir(long window, TimeUnit windowUnit,
			Timer clock) {
		this.clock = clock;
		this.distributions = new ConcurrentSkipListMap<Long, Long>();
		this.window = windowUnit.toNanos(window) * COLLISION_BUFFER;
		this.lastTick = new AtomicLong(clock.getTick() * COLLISION_BUFFER);
		this.count = new AtomicLong();
	}

	@Override
	public int size() {
		return distributions.size();
	}

	@Override
	public void update(long value) {
		if (count.incrementAndGet() % TRIM_THRESHOLD == 0) {
			trim();
		}
		distributions.put(getTick(), value);

	}

	@Override
	public Snapshot getSnapshot() {
		trim();
		return new UniformSnapshot(distributions.values());
	}

	private long getTick() {
		for (;;) {
			final long oldTick = lastTick.get();
			final long tick = clock.getTick() * COLLISION_BUFFER;
			final long newTick = tick - oldTick > 0 ? tick : oldTick + 1;
			if (lastTick.compareAndSet(oldTick, newTick)) {
				return newTick;
			}
		}
	}

	private void trim() {
		distributions.headMap(getTick() - window).clear();
	}

}
