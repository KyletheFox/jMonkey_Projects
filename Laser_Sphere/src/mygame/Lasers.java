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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

/**
 *
 * @author kyle
 */
public class Lasers extends Node {
    
    private static Vector3f rightPos = new Vector3f(1,0,0);
    private static Vector3f leftPos = new Vector3f(-1,0,0);
    private boolean readyToFire;
    private SimpleApplication sApp;
    
    public Lasers(SimpleApplication sa) {
        readyToFire = true;
        this.sApp = sa;
    }
    
    public void fireLasers(Vector3f target) {
        
        Cylinder rLaser = new Cylinder(10, 10, 0.05f, target.length()*2);
        Cylinder lLaser = new Cylinder(10, 10, 0.05f, target.length()*2);
        
        Geometry gRL = new Geometry("rlaser", rLaser);
        Geometry gLL = new Geometry("llaser", lLaser);
        
        Material mlaser = new Material(sApp.getAssetManager(),
       "Common/MatDefs/Misc/Unshaded.j3md");
        mlaser.setColor("Color", ColorRGBA.Red);
        
        gRL.setMaterial(mlaser);
        gLL.setMaterial(mlaser);
        
        Quaternion rotLeft = getQuaternion(target, leftPos);
        Quaternion rotRight = getQuaternion(target, rightPos);
        
        gRL.setLocalRotation(rotRight);
        gLL.setLocalRotation(rotLeft);
        
        gRL.setLocalTranslation(rightPos);
        gLL.setLocalTranslation(leftPos);
        
        this.attachChild(gRL);
        this.attachChild(gLL);
        
    }
    
    private Quaternion getQuaternion(Vector3f target, Vector3f start) {
        Quaternion q = new Quaternion();
        Vector3f rotAxis;
        float sinAlpha, cosAlpha, alpha;
        
        start.normalizeLocal();
        target.normalizeLocal();
        
        rotAxis = Vector3f.UNIT_Z.cross(target);      // Rotational Axis
        
        sinAlpha = rotAxis.length();
        cosAlpha = Vector3f.UNIT_Z.dot(target);
        alpha = FastMath.atan2(sinAlpha, cosAlpha);
        q.fromAngleAxis(alpha, rotAxis);
        return q;
    }
}
