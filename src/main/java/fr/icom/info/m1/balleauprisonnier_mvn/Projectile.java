package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Projectile {
	private double speed,x,y,angle;
	private boolean running;
	private Image ball;
	private BoundingBox hitbox;
	private Player owner;
	private String direction;
	private static final int size = 30;
	
	public Projectile() {
		this.ball = new Image("assets/titou.jpg",size,size,false,false);
		this.speed = 2;
		this.hitbox = new BoundingBox(x,y,size,size);	
	}
	
	public void fire() {
		this.angle = owner.getAngle();
		this.running = true;
		this.x = owner.getX();
		this.y = owner.getY();
	}
	
	public Image getImage() {
		return this.ball;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public BoundingBox getHitBox() {
		return this.hitbox;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	public Player getOwner() {
		return this.owner;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isRunning() {
		return running;
	}
	public void setHitbox() {
		this.hitbox = new BoundingBox(x,y,size,size);
	}
	
	public void setOwner(Player p) {
		this.owner = p;
	}
	
	public void update(Player [] equipe1, Player [] equipe2) {
		if(running) {
				
			double radValue = Math.toRadians(angle) + (Math.PI/2); //Utilise plus de mémoire mais moins de processus

			if(!owner.isEquipe1()) {
				y += Math.sin(radValue)*speed;
				x += Math.cos(radValue)*speed;				
				direction = Math.cos(radValue) > 0 ? "droite" : "gauche";
			}else {
				y -= Math.sin(radValue)*speed;
				x -= Math.cos(radValue)*speed;
				direction = Math.cos(radValue) > 0 ? "gauche" : "droite";
			}
			
			
			setHitbox();
			
			if(!owner.isEquipe1()) {
				for(int i = 0 ; i < equipe1.length;i++) {
	                if(getHitBox().intersects(equipe1[i].sprite.getBoundsInParent()) && equipe1[i] != owner) {
		                	this.running = false;
		                    equipe1[i].setVisible();
		                    equipe1[i].sprite.setVisible(false);
		                    this.owner = null;
	                }
	            }
			}
			else {
				for(int i = 0 ; i < equipe2.length;i++) {
	                if(getHitBox().intersects(equipe2[i].sprite.getBoundsInParent()) && equipe2[i] != owner) {
	                	this.running = false;
	                    equipe2[i].setVisible();
	                    equipe2[i].sprite.setVisible(false);
	                    this.owner = null;
	                }
	            }
			}
			
			if((int) x <= 0 || (int) x >= Field.width) {
				x = 50 + Math.random() * (Field.width-50);
				y = owner.isEquipe1() ? equipe2[0].getY() : equipe1[0].getY();
				running = false;
				owner = null;
				setHitbox();
            }
			
			if(y <= equipe2[0].getY() || y >= equipe1[0].getY()) {
				running = false;
				owner = null;
				setHitbox();
			}
			
		}else if(owner == null) {
			for(int i = 0 ; i < equipe1.length;i++) {
                if(getHitBox().intersects(equipe1[i].sprite.getBoundsInParent()) && equipe1[i].isVisible()) {
                    this.owner = equipe1[i];
                }
            }
			for(int i = 0 ; i < equipe2.length;i++) {
                if(getHitBox().intersects(equipe2[i].sprite.getBoundsInParent()) && equipe2[i].isVisible()) {
                	this.owner = equipe2[i];
                }
            }
		}else {
			x = owner.getX();
			y = owner.getY();
			
		}
	}
}
