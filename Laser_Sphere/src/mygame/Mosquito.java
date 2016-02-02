/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author TheFoxx
 */
public class Mosquito extends Node{
    
    Vector3f position;
    float time = 0;
    Vector3f distance;
     
    public Mosquito(SimpleApplication sa) {
        Sphere bug = new Sphere(10, 10, 0.15f);
        Geometry gBug = new Geometry("bug", bug);
        Material mBug = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        BugControl bControl = new BugControl();
        mBug.setColor("Color", ColorRGBA.randomColor());
        
        distance = getNewLocation();
        position = distance;
        gBug.setLocalTranslation(distance);
        gBug.setMaterial(mBug);
        gBug.addControl(bControl);
        this.attachChild(gBug);
        
    }
    
    private Vector3f getNewLocation() {
       
        return position = randomVector().normalizeLocal().mult(2f + FastMath.nextRandomFloat());
    }
    
    private class BugControl extends AbstractControl {
        
        private Vector3f randVector = new Vector3f(randomVector());
        
        @Override
        protected void controlUpdate(float tpf) {
            
            time += tpf;
            Vector3f pos;
            
            // Orbit
            Vector3f rotAxis = position.cross(randVector);
            rotAxis.normalizeLocal();
            Quaternion q = new Quaternion();
            q.fromAngleAxis(time, rotAxis);
            pos = q.mult(position);
            
            // Inward
            Vector3f gravity = position.scaleAdd(-0.3f * tpf, Vector3f.ZERO);
            pos = pos.addLocal(gravity);
            
            
            this.spatial.setLocalTranslation(pos);
            position = pos;
            
            
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
            // Not used
        }
    };
    
    private Vector3f randomVector() {
        Vector3f newVector = new Vector3f();
        newVector.x = FastMath.nextRandomFloat();
        newVector.y = FastMath.nextRandomFloat();
        newVector.z = FastMath.nextRandomFloat();
        
        int randNum = FastMath.nextRandomInt(0,7);
        
        if ((randNum & 1) == 0) {
            newVector.x *= -1;
        }
        if ((randNum & 2) == 0) {
            newVector.y *= -1;
        }
        if ((randNum & 4) == 0) {
            newVector.z *= -1;
        }
        
        return newVector;
    }
}
