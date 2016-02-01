/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Kyle O'Neill
 */
public class LaserBall extends Node{
    
    public LaserBall(SimpleApplication sa) {
        
        // Create Inner Ball
        Sphere insideSphere = new Sphere(20, 20, 1f);
        Geometry gInside = new Geometry("in", insideSphere);
        Material mInside = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        mInside.setTexture("ColorMap", sa.getAssetManager().loadTexture(
                "Textures/inner.jpg"));
        gInside.setMaterial(mInside);
        this.attachChild(gInside);
        
        // Create Outer Ball
        Sphere outsideSphere = new Sphere(20, 20, 3f);
        Geometry gOutside = new Geometry("out", outsideSphere);
        Material mOutside = new Material(sa.getAssetManager(),
       "Common/MatDefs/Misc/Unshaded.j3md");
        mOutside.setTexture("ColorMap", sa.getAssetManager().loadTexture(
                "Textures/inner.jpg"));
        mOutside.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        gOutside.setQueueBucket(Bucket.Transparent);
        gOutside.setMaterial(mOutside);
        this.attachChild(gOutside);
        
    }
    
}
