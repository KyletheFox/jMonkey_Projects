/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
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
public class ObjectField extends Node {
    
    float angle;
    SimpleApplication sApp;
    int totalObjects;
    
    public ObjectField(SimpleApplication sa, int numberOfObjects) {
        this.totalObjects = numberOfObjects;
        this.sApp = sa;
        for (int i=0; i<numberOfObjects; i++) {

            // Create New Object
            Object obj = new Object(sApp);
            
            // Generate Random Angle
            angle = (FastMath.nextRandomInt(-250, 80) * FastMath.DEG_TO_RAD);
            
            // Place object on random location on planet
            obj.setLocalTranslation(randLocationOnPlanet(angle));
           
            this.attachChild(obj);
            this.setLocalTranslation(0, -200f, 0);

        }
        
    }
    
    private Vector3f randLocationOnPlanet(float angle) {
        float xMove = -12.5f + FastMath.nextRandomInt(0, 25);
        return new Vector3f(xMove, 200f*FastMath.sin(angle), 200f*FastMath.cos(angle));
    }
    
    public void addObj(int numObjs) {
        
        totalObjects += numObjs;
        
        for (int i=0; i<numObjs; i++) {

            // Create New Object
            Object obj = new Object(sApp);
            
            // Generate Random Angle
            angle = (FastMath.nextRandomInt(135, 430) * FastMath.DEG_TO_RAD);
            
            // Place object on random location on planet
            obj.setLocalTranslation(randLocationOnPlanet(angle));
           
            this.attachChild(obj);
            this.setLocalTranslation(0, -200f, 0);

        }
    }
    
    class ObjFieldControl extends AbstractControl {
        
        final float PLANET_SPEED = 0.5f; 
        float time = 0;
        
        @Override
        protected void controlUpdate(float tpf) {
            time += tpf;
//            Quaternion q = new Quaternion();
//            q.fromAngleAxis(time * PLANET_SPEED, Vector3f.UNIT_X);
//            this.spatial.setLocalRotation(q);
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
           
        }
        
    };
        
}
