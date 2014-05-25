import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Tone {
	public static void sound(int hz, int msecs, double vol)
			throws IllegalArgumentException, LineUnavailableException {

		if (vol > 1.0 || vol < 0.0)
			throw new IllegalArgumentException("Volume out of range 0.0- 1.0");

		byte[] buf = new byte[msecs * 8];

		for (int i = 0; i < buf.length; i++) {
			double angle = i / (8000.0 / hz) * 2.0 * Math.PI;
			buf[i] = (byte) (Math.sin(angle) * 127.0 * vol);
		}
		
		AudioFormat af = new AudioFormat(8000f, 8, 1, true, false);
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		sdl.write(buf, 0, buf.length);
		sdl.drain();
		sdl.close();
	}
}