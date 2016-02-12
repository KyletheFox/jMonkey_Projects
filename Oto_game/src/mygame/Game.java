/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

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
    float time = 0;
    int numObjects = 50;
    int level = 1;
    
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            if (name.equals("Quit")) {
                StartScreen s = new StartScreen() ; // false: real game, not a demo
                asm.detach(this);
                asm.attach(s);
            }
        }
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        main = (Main) app;
        asm = stateManager;
        
        planet = new PlanetSurface(main);
        
        PlanetNode = new Node();
        PlanetControl c = new PlanetControl();
        PlanetNode.addControl(c);
        PlanetNode.attachChild(planet);
        
        objField = new ObjectField(main, numObjects);
        PlanetNode.attachChild(objField);
        PlanetNode.setLocalTranslation(0, -200f, 0);
         
        main.getRootNode().attachChild(PlanetNode);
        
        oto = new Oto(main, objField);
        oto.setGroundSpeed(2.5f);
        
        InputManager inputManager = main.getInputManager();
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addListener(this, "Quit");
    }
    
    public class PlanetControl extends AbstractControl {
        
        boolean reset = false;
        float resetTime = 0;
        @Override
        protected void controlUpdate(float tpf) {
            time += tpf;  
            Quaternion q = new Quaternion();
            q.fromAngleAxis(time * 0.4f, Vector3f.UNIT_X);
            this.spatial.setLocalRotation(q);
            
            if (FastMath.floor(time)%360 >= 40f && !reset) {
                objField.addObj(100);
                reset = true;
                resetTime = time * 0.4f;
            }
            
            if (resetTime + 5f < time * 0.4f) {
                reset = false;
                resetTime = 0;
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }
        
    };
    
}
