package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.scene.canvas.GraphicsContext;

public class Human extends Player {

	Human(GraphicsContext gc, String color, int xInit, int yInit, String side, double speed) {
		super(gc, color, xInit, yInit, side, speed);
		// TODO Auto-generated constructor stub
	}
	
	/*@Override
	public void projectileDeplacement() {
		double radValue = Math.toRadians(this.getProjectile().getAngle()) + (Math.PI/2); //Utilise plus de mémoire mais moins de processus
		this.getProjectile().setY(-Math.sin(radValue) * this.getProjectile().getSpeed());
		this.getProjectile().setX(-Math.cos(radValue) * this.getProjectile().getSpeed());
		
	}*/
}
