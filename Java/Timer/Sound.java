package myTimer;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	private Clip mySound;
	private boolean once;
	private String fileName;

	public Sound(String fileName) {
		this.fileName = fileName;
		create(fileName);
	}
	
	private void create(String fileName) {
		once = true;
		try {
			mySound = AudioSystem.getClip();
			mySound.open(AudioSystem.getAudioInputStream(new File(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		if(!once)
			return;
		if(!mySound.isRunning()) {
			mySound.start();
			once = false;
		}
	}

	public void stop() {
		if(mySound.isRunning()) 
			mySound.stop();
		create(fileName);
	}
} 