package com.fmontalvoo.assets;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	private final Clip clip;
	private final FloatControl volume;

	public Sound(Clip clip) {
		this.clip = clip;
		this.volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}

	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}

	public void loop() {
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

	public void changeVolume(float value) {
		volume.setValue(value);
	}

	public int getFramePosition() {
		return clip.getFramePosition();
	}

}
