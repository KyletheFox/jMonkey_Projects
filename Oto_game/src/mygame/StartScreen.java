
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;


/**
 *
 * @author Kyle O'Neill
 */
public class StartScreen extends AbstractAppState implements ActionListener{
    
    Main main;
    ParticleEmitter debrisEffect;
    AppStateManager asm;
    BitmapText text, blinkText;
    AppSettings s;
    float time;
    boolean gameStarted, blinkFlag;
    
    
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            if (name.equals("start") && !gameStarted) {
                Game g = new Game();
                asm.detach(this);
                asm.attach(g);
                gameStarted = true;
            }
            if (name.equals("quit")) {
                System.exit(0);
            }
        }
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        main = (Main) app;
        asm = stateManager;
        s = main.getSettings();
        time = 0;
        gameStarted = false;
        blinkFlag = true;
        Node explode = new Node();
        Main.clearJMonkey(main);
           
        main.getViewPort().setBackgroundColor(ColorRGBA.LightGray);
        
        // Start Screen Text 
        BitmapFont bmf = app.getAssetManager().loadFont("Interface/Fonts/Console.fnt");
        text = new BitmapText(bmf);
        text.setSize(bmf.getCharSet().getRenderedSize() * 10);
        text.setColor(ColorRGBA.Red);
        text.setText("Oto: The Game");
        text.setLocalTranslation(getCenterGui().add(-text.getLineWidth()/2f,s.getHeight()/4f,0));
        main.getGuiNode().attachChild(text);
        
        blinkText = new BitmapText(bmf);
        blinkText.setSize(bmf.getCharSet().getRenderedSize() * 3);
        blinkText.setColor(ColorRGBA.Red);
        blinkText.setText("Press Space to start the Game");
        blinkText.setLocalTranslation(getCenterGui().add(-blinkText.getLineWidth()/2f,s.getHeight()*-0.1f,0));
        main.getGuiNode().attachChild(blinkText);
        
        
        // Input Listeners
        InputManager inputManager = main.getInputManager();
        inputManager.clearMappings();
        inputManager.addMapping("start", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("quit", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addListener(this, "start", "quit");
        
            /** Explosion effect. Uses Texture from jme3-test-data library! */ 
        debrisEffect = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 700);
        Material debrisMat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        debrisMat.setTexture("Texture", main.getAssetManager().loadTexture("Effects/Explosion/Debris.png"));
        debrisEffect.setMaterial(debrisMat);
        debrisEffect.setImagesX(3); debrisEffect.setImagesY(3); // 3x3 texture animation
        debrisEffect.setRotateSpeed(4);
        debrisEffect.setSelectRandomImage(true);
        debrisEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 10f, 0));
        debrisEffect.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
        debrisEffect.setGravity(0f,-3f,0f);
        debrisEffect.getParticleInfluencer().setVelocityVariation(.60f);
        
        explode.attachChild(debrisEffect);
        explode.setLocalTranslation(0, -10f, 0);
        main.getRootNode().attachChild(explode);
        
        debrisEffect.emitAllParticles();
        


    }
    
    @Override
    public void cleanup() {
        main.getViewPort().setBackgroundColor(ColorRGBA.Black);
        Main.clearJMonkey(main);
    }

     @Override
    public void update(float tpf) {
        time += tpf;
        if (FastMath.floor(time) % 2 == 0) {
            blinkText.setText("Press Space to start the Game");
        }
        else {
            blinkText.setText("");
        }
    }
     
    private Vector3f getCenterGui() {
        return new Vector3f((s.getWidth())/2, s.getHeight()/2, 0);
    }
}
