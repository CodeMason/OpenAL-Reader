package warlord.sound;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A utility to provide a simple and rational interface to the
 * slick internals
 * 
 * @author kevin
 */
public class AudioLoader {
	/** OGG Format Indicator */
	private static final String OGG = "OGG";

	/** True if the audio loader has be initialised */
	private static boolean inited = false;
	
	/**
	 * Initialise the audio loader 
	 */
	private static void init() {
		if (!inited) {
			SoundStore.get().init();
			inited = true;
		}
	}
	
	/**
	 * Get a resource
	 * @param ref Filename to search for
	 * @return Input stream for the resource
	 */
	public static InputStream getResourceAsStream(String ref){
		return AudioLoader.class.getResourceAsStream(ref);
	}
	
	/**
	 * Get audio data in a playable state by loading the complete audio into 
	 * memory.
	 * 
	 * @param format The format of the audio to be loaded (something like "XM" or "OGG")
	 * @param in The input stream from which to load the audio data
	 * @return An object representing the audio data 
	 * @throws IOException Indicates a failure to access the audio data
	 */
	public static Audio getAudio(String format, InputStream in, String name) throws IOException {
		init();
		
		if (format.equals(OGG)) {
			return SoundStore.get().getOgg(name, in);
		}
		
		throw new IOException("Unsupported format for non-streaming Audio: "+format);
	}
	
	/**
	 * Get audio data in a playable state by setting up a stream that can be piped into
	 * OpenAL - i.e. streaming audio
	 * 
	 * @param format The format of the audio to be loaded (something like "XM" or "OGG")
	 * @param url The location of the data that should be streamed
	 * @return An object representing the audio data 
	 * @throws IOException Indicates a failure to access the audio data
	 */
	public static Audio getStreamingAudio(String format, URL url) throws IOException {
		init();
		
		if (format.equals(OGG)) {
			return SoundStore.get().getOggStream(url);
		}
		
		throw new IOException("Unsupported format for streaming Audio: "+format);
	}
	
	/**
	 * Allow the streaming system to update itself
	 */
	public static void update() {
		init();
		
		SoundStore.get().poll(0);
	}
}
