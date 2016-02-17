/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.system.AppSettings;

/**
 *
 * @author ko163
 */
public class Game extends AbstractAppState implements ActionListener{
    
    Main main;
    AppStateManager asm;
    Node PlanetNode;
    ObjectField objField;
    Oto oto;
    PlanetSurface planet;
    BitmapFont bmf;
    BitmapText scoreText, waitText, pauseText;
    AppSettings set;
    PlanetControl c;
    Vector3f startPos;
    int state, stateMemory;
    float time, score, waitTime, lineX, lineY;
    int numObjects = 30;
    int level = 1;
    boolean started, dead, waitTextVisible;
    final int WAIT = 0;
    final int PAUSE = 1;
    final int RUN = 2;
    private final int INITIALWAITTIME = 3;
    
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            if (name.equals("Quit")) {
                StartScreen s = new StartScreen(); 
                asm.detach(this);
                asm.attach(s);
            }
            if (name.equals("Pause")) {
                if (state == PAUSE) {
                    state = stateMemory;
                    main.getGuiNode().detachChild(pauseText);
                } else {
                    stateMemory = state; 
                    state = PAUSE;
                    main.getGuiNode().attachChild(pauseText);
                }
            }
        }
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        main = (Main) app;
        asm = stateManager;
        state = WAIT;
        set = main.getSettings();
        dead = false; started = false; waitTextVisible = false;
        time = 0; score = 0;
        waitTime = INITIALWAITTIME; //seconds
        
        // Initalizing planet and objects
        planet = new PlanetSurface(main);
        objField = new ObjectField(main, numObjects);
        
        // Attaching and adding movement to the planet
        PlanetNode = new Node();
        c = new PlanetControl();
        //PlanetNode.addControl(c);
        PlanetNode.attachChild(planet);
        PlanetNode.attachChild(objField);
        PlanetNode.setLocalTranslation(0, -200f, 0);
        
        Object o = new Object(main);
        o.setLocalTranslation(0, 0f, 0);
        main.getRootNode().attachChild(o);
         
        main.getRootNode().attachChild(PlanetNode);
        
        // Adding oto
        oto = new Oto(main, objField);
        startPos = oto.getLocation();
        
        // Listers for keyboard input
        InputManager inputManager = main.getInputManager();
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(this, "Quit", "Pause");
        
        // Adding score 
        bmf = main.getAssetManager().loadFont("Interface/Fonts/Console.fnt");
        scoreText = new BitmapText(bmf);
        scoreText.setSize(bmf.getCharSet().getRenderedSize() * 5);
        scoreText.setColor(ColorRGBA.White);
        scoreText.setLocalTranslation(20f, set.getHeight()-20f, 0f);
        scoreText.setText("Score: 0");
        main.getGuiNode().attachChild(scoreText);
        
        waitText = new BitmapText(bmf);
        waitText.setSize(bmf.getCharSet().getRenderedSize() * 10);
        waitText.setColor(ColorRGBA.White);
        waitText.setText("");
        AppSettings s = main.getSettings();
        lineY = s.getHeight() / 2;
        lineX = (s.getWidth() - waitText.getLineWidth()) / 2;
        waitText.setLocalTranslation(lineX, lineY, 0f);
        
        pauseText = new BitmapText(bmf);
        pauseText.setSize(bmf.getCharSet().getRenderedSize() * 10);
        pauseText.setColor(ColorRGBA.White);
        pauseText.setText("PAUSED");
        lineY = s.getHeight() / 2;
        lineX = (s.getWidth() - pauseText.getLineWidth()) / 2;
        pauseText.setLocalTranslation(lineX, lineY, 0f);
    }
    
    @Override
    public void update(float tpf) {
        
        // Add Score
        if (started && !dead) {
            score += tpf * 10;
            String str = String.format("Score: %d", (int)Math.round(score));
            scoreText.setText(str);
        }
        
        // Check to see if Oto died
        if (oto.dead) {
            dead = true;
        }
        
        // State Machine
        switch (state) {
            case WAIT:
                if (!waitTextVisible) {
                    waitTextVisible = true;
                    main.getGuiNode().attachChild(waitText);
                }
                waitTime -= tpf;
                if (waitTime <= 0f) {
                    state = RUN;
                    if (waitTextVisible) {
                        waitTextVisible = false;
                        main.getGuiNode().detachChild(waitText);
                    }
                } else {
                    waitText.setText("" + ((int) waitTime + 1));
                }
                break;
            case RUN:
                if (!started) {
                    oto.setGroundSpeed(2.5f);
                    PlanetNode.addControl(c);
                    started = true;
                }
                if (dead) {
                    lineY = set.getHeight() / 2 + scoreText.getHeight();
                    lineX = (set.getWidth() - scoreText.getLineWidth()) / 2;
                    scoreText.setSize(bmf.getCharSet().getRenderedSize() * 15);
                    scoreText.setColor(ColorRGBA.Red);
                    scoreText.setLocalTranslation(lineX, lineY, 0f);
                }
                
                break;
            case PAUSE:
                oto.setGroundSpeed(0f);
                PlanetNode.removeControl(c);
                started = false;
                break;
        }
    }
        
    
    
    public class PlanetControl extends AbstractControl {
        
        final float PLANET_SPEED = 0.3f;
        boolean reset = false;
        float resetTime = 0;
        @Override
        protected void controlUpdate(float tpf) {
            time += tpf * PLANET_SPEED;  
            Quaternion q = new Quaternion();
            q.fromAngleAxis(time, Vector3f.UNIT_X);
            this.spatial.setLocalRotation(q);
            
            if (FastMath.floor(time) % 360 >= 350 * FastMath.DEG_TO_RAD &&
                    !reset) {
                objField.addObj(20);
                reset = true;
                resetTime = time;
            }
            
            if (resetTime + 5f < time) {
                reset = false;
                resetTime = 0;
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }
        
    };
    
}
