package lwjgl.debug;

public class Timer {
	
	public static final Timer DEBUG = new Timer();
	
	private long time = System.nanoTime();
	
	public void mark() {
		time = System.nanoTime();
	}
	
	public void measure(String message) {
		long t = System.nanoTime();
		System.out.format("%s\t%.3f ms%n", message, (t - time) * 0.000001f);
		time = t;
	}
	
	public void measure(String message, int factor) {
		long t = System.nanoTime();
		System.out.format("%s\t%.3f ms%n", message, (t - time) * 0.000001f / factor);
		time = t;
	}
	
}
