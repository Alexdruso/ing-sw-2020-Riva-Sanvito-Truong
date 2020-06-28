package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import javafx.scene.Scene;

/**
 * This class is used to generate SceneLoader instances by using the Builder pattern
 */
public class SceneLoaderBuilder {
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
    double blurInDuration = 250;
    CSSFile cssFile;
    AbstractClientState state;

    /**
     * The class constructor
     * @param fxmlFile the path to the fxml to be loaded in the resources folder
     * @param client the Client instance
     */
    public SceneLoaderBuilder(String fxmlFile, Client client){
        this.fxmlFile = fxmlFile;
        this.client = client;
        this.mainScene = JavaFXGUI.getPrimaryScene();
    }

    /**
     * Sets the setFadeOut property for the SceneLoader
     * @param doApplyFadeout the value for the setFadeOut property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setFadeOut(boolean doApplyFadeout){
        this.doApplyFadeOut = doApplyFadeout;
        return this;
    }

    /**
     * Sets the doApplyFirstFadeOut property for the SceneLoader
     * @param doApplyFirstFadeOut the value for the doApplyFirstFadeOut property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setFirstFadeOut(boolean doApplyFirstFadeOut){
        this.doApplyFirstFadeOut = doApplyFirstFadeOut;
        return this;
    }

    /**
     * Sets the doApplyFadeIn property for the SceneLoader
     * @param doApplyFadeIn the value for the doApplyFadeIn property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setFadeIn(boolean doApplyFadeIn){
        this.doApplyFadeIn = doApplyFadeIn;
        return this;
    }

    /**
     * Sets the clientState property for the SceneLoader
     * @param clientState the value for the clientState property
     * @param state the value for the state property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setState(ClientState clientState, AbstractClientState state){
        this.clientState = clientState;
        this.state = state;
        return this;
    }

    /**
     * Sets the attemptLoadFromSaved property for the SceneLoader
     * @param attemptLoadFromSaved the value for the attemptLoadFromSaved property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setAttemptLoadFromSaved(boolean attemptLoadFromSaved){
        this.attemptLoadFromSaved = attemptLoadFromSaved;
        return this;
    }

    /**
     * Sets the fadeInDuration property for the SceneLoader
     * @param duration the value for the fadeInDuration property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setFadeInDuration(double duration){
        this.fadeInDuration = duration;
        return this;
    }

    /**
     * Sets the fadeOutDuration property for the SceneLoader
     * @param duration the value for the fadeOutDuration property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setFadeOutDuration(double duration){
        this.fadeOutDuration = duration;
        return this;
    }

    /**
     * Sets the blurInDuration property for the SceneLoader
     * @param duration the value for the blurInDuration property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setBlurInDuration(double duration){
        this.blurInDuration = duration;
        return this;
    }

    /**
     * Sets the replaceOldScene property for the SceneLoader
     * @param replaceOldScene the value for the replaceOldScene property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder setReplaceOldScene(boolean replaceOldScene){
        this.replaceOldScene = replaceOldScene;
        return this;
    }

    /**
     * Sets the cssFile property for the SceneLoader
     * @param cssFile the value for the cssFile property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder addCSSFile(CSSFile cssFile){
        this.cssFile = cssFile;
        return this;
    }

    /**
     * Sets the forceSceneChange property for the SceneLoader
     * @param doForce the value for the forceSceneChange property
     * @return the SceneLoaderFactory instance
     */
    public SceneLoaderBuilder forceSceneChange(boolean doForce){
        this.forceSceneChange = doForce;
        return this;
    }

    /**
     * This method is used to build the SceneLoader with all the settings saved inside the SceneLoaderFactory
     * @return the SceneLoader instance
     */
    public SceneLoader build(){
        return new SceneLoader(this);
    }
}
