/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author kyle
 */
public class Planet extends Node {
    
    public Planet(SimpleApplication sa) {
        Sphere planet = new Sphere(200, 200, 200f);
        Geometry gPlanet = new Geometry("Planet", planet);
        Material mPlanet = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        PlanetControl pControl = new PlanetControl();
        mPlanet.setTexture("ColorMap", sa.getAssetManager().loadTexture("Textures/planet.jpg"));
        gPlanet.setMaterial(mPlanet);
        
        gPlanet.setLocalTranslation(0, -200f, 0f);
        gPlanet.addControl(pControl);
        this.attachChild(gPlanet);
    }
    
    class PlanetControl extends AbstractControl {
        
        final float PLANET_SPEED = 0.5f; 
        float time = 0;
        
        @Override
        protected void controlUpdate(float tpf) {
            time += tpf;
            Quaternion q = new Quaternion();
            q.fromAngleAxis(time * PLANET_SPEED, Vector3f.UNIT_X);
            this.spatial.setLocalRotation(q);
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
           
        }
        
    };
    
}
