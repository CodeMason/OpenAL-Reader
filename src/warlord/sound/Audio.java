package warlord.sound;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

import warlord.opengl.Game;

/** 
 * The description of of audio data loaded by the AudioLoader
 * 
 * @author kevin
 * @author Nathan Sweet <misc@n4te.com>
 */
public class Audio {

	public static boolean isPlayable(){
		return Game.isSoundEnabled();
	}
	
	/** The store from which this sound was loaded */
	private SoundStore store;
	/** The buffer containing the sound */
	private int buffer;
	/** The index of the source being used to play this sound */
	private int index = -1;
	
	/** The length of the audio */
	private float length;
	
	/**
	 * Create a new sound
	 * 
	 * @param store The sound store from which the sound was created
	 * @param buffer The buffer containing the sound data
	 */
	Audio(SoundStore store, int buffer) {
		this.store = store;
		this.buffer = buffer;
		
		int bytes = AL10.alGetBufferi(buffer, AL10.AL_SIZE);
		int bits = AL10.alGetBufferi(buffer, AL10.AL_BITS);
		int channels = AL10.alGetBufferi(buffer, AL10.AL_CHANNELS);
		int freq = AL10.alGetBufferi(buffer, AL10.AL_FREQUENCY);
		
		int samples = bytes / (bits / 8);
		length = (samples / (float) freq) / channels;
	}
	
	/**
	 * Get the ID of the OpenAL buffer holding this data (if any). This method
	 * is not valid with streaming resources.
	 * 
	 * @return The ID of the OpenAL buffer holding this data 
	 */
	public int getBufferID() {
		return buffer;
	}
	
	/**
	 *
	 */
	protected Audio() {
		
	}
	
	/**
	 * Stop the sound effect
	 */
	public void stop() {
		if (index != -1) {
			store.stopSource(index);
			index = -1;
		}
	}
	
	/**
	 * Check if the sound is playing as sound fx
	 * 
	 * @return True if the sound is playing
	 */
	public boolean isPlaying() {
		if (index != -1) {
			return store.isPlaying(index);
		}
		
		return false;
	}
	
	/**
	 * Play this sound as a sound effect
	 * 
	 * @param pitch The pitch of the play back
	 * @param gain The gain of the play back
	 * @param loop True if we should loop
	 * @return The ID of the source playing the sound
	 */
	public int playAsSoundEffect(float pitch, float gain, boolean loop) {
		index = store.playAsSound(buffer, pitch, gain, loop);
		return store.getSource(index);
	}


	/**
	 * Play this sound as a sound effect
	 * 
	 * @param pitch The pitch of the play back
	 * @param gain The gain of the play back
	 * @param loop True if we should loop
	 * @param x The x position of the sound
	 * @param y The y position of the sound
	 * @param z The z position of the sound
	 * @return The ID of the source playing the sound
	 */
	public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z) {
		index = store.playAsSoundAt(buffer, pitch, gain, loop, x, y, z);
		return store.getSource(index);
	}
	
	/**
	 * Play this sound as music
	 * 
	 * @param pitch The pitch of the play back
	 * @param gain The gain of the play back
	 * @param loop True if we should loop
	 * @return The ID of the source playing the sound
	 */
	public int playAsMusic(float pitch, float gain, boolean loop) {
		store.playAsMusic(buffer, pitch, gain, loop);
		index = 0;
		return store.getSource(0);
	}
	
	/**
	 * Pause the music currently being played
	 */
	public static void pauseMusic() {
		SoundStore.get().pauseLoop();
	}

	/**
	 * Restart the music currently being paused
	 */
	public static void restartMusic() {
		SoundStore.get().restartLoop();
	}
	
	/**
	 * Seeks to a position in the music.
	 * 
	 * @param position Position in seconds.
	 * @return True if the setting of the position was successful
	 */
	public boolean setPosition(float position) {
		position = position % length;
		
		AL10.alSourcef(store.getSource(index), AL11.AL_SEC_OFFSET, position);
		if (AL10.alGetError() != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Return the current playing position in the sound
	 * 
	 * @return The current position in seconds.
	 */
	public float getPosition() {
		return AL10.alGetSourcef(store.getSource(index), AL11.AL_SEC_OFFSET);
	}
	
}
