package game;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip mySound;
	long curPosition;

	/*
	 * Construct new sound thread for Mario game.
	 */
	public Sound(String fileName) {
		curPosition = 0;
		try {
			mySound = AudioSystem.getClip();
			mySound.open(AudioSystem.getAudioInputStream(new File(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		if(!mySound.isRunning())
			mySound.start();
	}
	
	public void pause() {
		if(mySound==null)
			return;
		if(mySound.isRunning())
			curPosition = mySound.getMicrosecondPosition();
		stop();
	}
	
	public void resume() {
		if(mySound==null)
			return;
		if(!mySound.isRunning() && curPosition!=0) {
			mySound.setMicrosecondPosition(curPosition);
			curPosition = 0;
			start();
		}	
	}

	public void stop() {
		if(mySound.isRunning())
			mySound.stop();
	}
} 