package com.ashleyecs.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AshleyAssetManager {

    public final AssetManager manager = new AssetManager();

    // Sounds
    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";

    // Music
   	public final String playingSong = "music/Rolemusic_-_pl4y1ng.mp3";

   	// Skin
   	public final String skin = "skin/flat-earth-ui.json";



   	// Atlas
   	public final String gameImages = "images/game.atlas";
   	public final String loadingImages = "images/loading.atlas";

    /*------------------------------------------------------------------*\
    |*							Public Methods
    \*------------------------------------------------------------------*/

    public void queueAddImages() {
    	manager.load(gameImages, TextureAtlas.class);
    }

    public void queueAddSkin(){
   		SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("skin/flat-earth-ui.atlas");
   		manager.load(skin, Skin.class, params);

   	}

   	public void queueAddMusic(){
   		manager.load(playingSong, Music.class);
   	}

   	public void queueAddSounds(){
   		manager.load(boingSound, Sound.class);
   		manager.load(pingSound, Sound.class);
   	}

   	// a small set of images used by the loading screen
   	public void queueAddLoadingImages(){
		manager.load(loadingImages, TextureAtlas.class);
   	}

	public void queueAddFonts() {

	}

	public void queueAddParticleEffects() {

	}
}
