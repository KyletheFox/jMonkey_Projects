/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author TheFoxx
 */
public class Mosquito extends Node{
     
    public Mosquito(SimpleApplication sa) {
        Sphere bug = new Sphere(10, 10, 0.2f);
        Geometry gBug = new Geometry("bug", bug);
        Material mBug = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        
        mBug.setColor("Color", ColorRGBA.randomColor());
        gBug.setLocalTranslation(getNewLocation());
        gBug.setMaterial(mBug);
        this.attachChild(gBug);
        
    }
    
    private Vector3f getNewLocation() {
        Vector3f loc = new Vector3f();
        int randNum = FastMath.nextRandomInt(0,7);
        
        loc.x = FastMath.nextRandomFloat();
        loc.y = FastMath.nextRandomFloat();
        loc.z = FastMath.nextRandomFloat();
        
        if ((randNum & 1) == 0) {
            loc.x *= -1;
        }
        if ((randNum & 2) == 0) {
            loc.y *= -1;
        }
        if ((randNum & 4) == 0) {
            loc.z *= -1;
        }
        
        while (loc.length() < 2) {
            loc = loc.mult(2);
        }
        
        return loc;
    }
}
