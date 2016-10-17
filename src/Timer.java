public abstract class Timer {

	private static final Timer DEFAULT = new UserTimeClock();

	public abstract long getTick();

	public long getTime() {
		return System.currentTimeMillis();
	}

	public static Timer defaultClock() {
		return DEFAULT;
	}
	
	public static class UserTimeClock extends Timer {
        @Override
        public long getTick() {
            return System.nanoTime();
        }
    }
}
