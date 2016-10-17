import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MurmurHash {

	private MurmurHash() {
	}

	public static int hash(byte[] data, int seed) {
		return hash(ByteBuffer.wrap(data), seed);
	}
	
	public static int hash(ByteBuffer buf, int seed) {
	    ByteOrder byteOrder = buf.order();
	    buf.order(ByteOrder.LITTLE_ENDIAN);

	    int m = 0x5bd1e995;
	    int r = 24;

	    int h = seed ^ buf.remaining();

	    while (buf.remaining() >= 4) {
	      int k = buf.getInt();

	      k *= m;
	      k ^= k >>> r;
	      k *= m;

	      h *= m;
	      h ^= k;
	    }

	    if (buf.remaining() > 0) {
	      ByteBuffer finish = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
	      finish.put(buf).rewind();
	      h ^= finish.getInt();
	      h *= m;
	    }

	    h ^= h >>> 13;
	    h *= m;
	    h ^= h >>> 15;

	    buf.order(byteOrder);
	    return h;
	  }
}
