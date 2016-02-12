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
 * @author ko163
 */
public class Object extends Node {
    
    public Object(SimpleApplication sa) {
        Sphere sphere = new Sphere(5,7, FastMath.nextRandomInt(2, 4) * 1f);
        Geometry gSphere = new Geometry("object", sphere);
        Material mSphere = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        
        mSphere.setTexture("ColorMap", sa.getAssetManager().loadTexture("Textures/rock.jpg"));
        gSphere.setMaterial(mSphere);
        gSphere.setLocalTranslation(randomVectorOnPlanet());
        this.attachChild(gSphere);
    }
    
    public Vector3f randomVectorOnPlanet() {
        Vector3f loc = new Vector3f(0f,200f,0f);
        return loc;
    }
  
}
