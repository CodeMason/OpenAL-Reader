package warlord.sound;

import java.io.IOException;

import warlord.opengl.Logger;

public class Music {

	private Audio clip;
	
	public Music(String filename){
		if(MusicLoader.getClip(filename) != null){
			clip = MusicLoader.getClip(filename);
		} else {
			try {
				clip = AudioLoader.getAudio("OGG", AudioLoader.getResourceAsStream(filename), filename);
			} catch (IOException e) {
				Logger.print(e);
			}
		}
	}
	
	public static Music get(String filename){
		return new Music(filename);
	}
	
	// Manage this OggClip
	
	public void play(){
		clip.playAsMusic(1.0f, 1.0f, true);
	}
	
	public void pause(){
		clip.stop();
	}
	
	public void stop(){
		clip.stop();
	}
	
}
