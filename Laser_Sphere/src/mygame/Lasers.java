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
import com.jme3.scene.shape.Cylinder;
import java.util.Date;

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
        
        if (readyToFire) {
            Cylinder rLaser = new Cylinder(10, 10, 0.05f, target.length());
            Cylinder lLaser = new Cylinder(10, 10, 0.05f, target.length());

            Geometry gRL = new Geometry("rlaser", rLaser);
            Geometry gLL = new Geometry("llaser", lLaser);

            Material mlaser = new Material(sApp.getAssetManager(),
           "Common/MatDefs/Misc/Unshaded.j3md");
            mlaser.setColor("Color", ColorRGBA.Red);
            Material mlaser2 = new Material(sApp.getAssetManager(),
           "Common/MatDefs/Misc/Unshaded.j3md");
            mlaser.setColor("Color", ColorRGBA.Green);

            gRL.setMaterial(mlaser);
            gLL.setMaterial(mlaser2);

            Quaternion rotRight = getQuaternion(target, new Vector3f(1f, 0, 1f).normalizeLocal().subtract(rightPos));
            Quaternion rotLeft = getQuaternion(target, new Vector3f(-1f, 0, 1f).normalizeLocal().subtract(leftPos));

            gRL.setLocalRotation(rotRight);
            gLL.setLocalRotation(rotLeft);

            gRL.setLocalTranslation(target);
            gLL.setLocalTranslation(target);

            this.attachChild(gRL);
            this.attachChild(gLL);
        
            readyToFire = false;
        }
        
    }
    
    private Quaternion getQuaternion(Vector3f target, Vector3f start) {
        Quaternion q = new Quaternion();
        Vector3f rotAxis;
        float sinAlpha, cosAlpha, alpha;
        
        start.normalizeLocal();
        target.normalizeLocal();
        
        rotAxis = start.cross(target);      // Rotational Axis
        
        sinAlpha = rotAxis.length();
        cosAlpha = start.dot(target);
        alpha = FastMath.atan2(sinAlpha, cosAlpha);
        q.fromAngleAxis(alpha, rotAxis);
        return q;
    }
    
     private class laserControl extends AbstractControl {

         protected void controlUpdate(float tpf) {
            if (!readyToFire) {
                  try {
                    Thread.sleep(1000);
                  } catch (InterruptedException ie) {
                      //Handle exception
                  }
                  this.spatial.removeFromParent();
                  readyToFire = true;
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
            
        }
         
     }
}
