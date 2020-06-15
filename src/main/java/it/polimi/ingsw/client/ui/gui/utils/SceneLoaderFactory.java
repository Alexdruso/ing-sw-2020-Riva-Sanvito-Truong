package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import javafx.scene.Scene;

public class SceneLoaderFactory {
    final String fxmlFile;
    final Client client;
    final Scene mainScene;
    ClientState clientState;
    boolean doApplyFadeOut = true;
    boolean doApplyFirstFadeOut = true;
    boolean doApplyFadeIn = true;
    boolean attemptLoadFromSaved = true;
    boolean forceSceneChange = false;
    boolean replaceOldScene = true;
    double fadeInDuration = 500;
    double fadeOutDuration = 500;
    double blurInDuration = 500;
    double blurOutDuration = 500;
    CSSFile cssFile;
    AbstractClientState state;

    public SceneLoaderFactory(String fxmlFile, Client client){
        this.fxmlFile = fxmlFile;
        this.client = client;
        this.mainScene = JavaFXGUI.getPrimaryScene();
    }

    public SceneLoaderFactory setFadeOut(boolean doApplyFadeout){
        this.doApplyFadeOut = doApplyFadeout;
        return this;
    }

    public SceneLoaderFactory setFirstFadeOut(boolean doApplyFirstFadeOut){
        this.doApplyFirstFadeOut = doApplyFirstFadeOut;
        return this;
    }

    public SceneLoaderFactory setFadeIn(boolean doApplyFadeIn){
        this.doApplyFadeIn = doApplyFadeIn;
        return this;
    }

    public SceneLoaderFactory setState(ClientState clientState, AbstractClientState state){
        this.clientState = clientState;
        this.state = state;
        return this;
    }

    public SceneLoaderFactory setAttemptLoadFromSaved(boolean attemptLoadFromSaved){
        this.attemptLoadFromSaved = attemptLoadFromSaved;
        return this;
    }

    public SceneLoaderFactory setFadeInDuration(double duration){
        this.fadeInDuration = duration;
        return this;
    }

    public SceneLoaderFactory setFadeOutDuration(double duration){
        this.fadeOutDuration = duration;
        return this;
    }

    public SceneLoaderFactory setBlurInDuration(double duration){
        this.blurInDuration = duration;
        return this;
    }

    public SceneLoaderFactory setBlurOutDuration(double duration){
        this.blurOutDuration = duration;
        return this;
    }

    public SceneLoaderFactory setReplaceOldScene(boolean replaceOldScene){
        this.replaceOldScene = replaceOldScene;
        return this;
    }

    public SceneLoaderFactory addCSSFile(CSSFile cssFile){
        this.cssFile = cssFile;
        return this;
    }

    public SceneLoaderFactory forceSceneChange(boolean doForce){
        this.forceSceneChange = doForce;
        return this;
    }

    public SceneLoader build(){
        return new SceneLoader(this);
    }
}
