package fr.icom.info.m1.balleauprisonnier_mvn;


import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * 
 * Classe gerant un joueur
 *
 */
public abstract class Player 
{
	  private double x;       // position horizontale du joueur
	  private final double y; 	  // position verticale du joueur
	  private double angle = 90; // rotation du joueur, devrait toujour être en 0 et 180
	  private double step;    // pas d'un joueur

	  private String playerColor;
	  
	  private boolean visible = true;
	  
	  private Image directionArrow;
	  public Sprite sprite;
	  private ImageView PlayerDirectionArrow;
	  
	  private GraphicsContext graphicsContext;
	  
	  private BoundingBox hitbox;
	  
	  /**
	   * Constructeur du Joueur
	   * 
	   * @param gc ContextGraphic dans lequel on va afficher le joueur
	   * @param color couleur du joueur
	   * @param yInit position verticale
	   */
	  Player(GraphicsContext gc, String color, int xInit, int yInit, String side,double speed)
	  {
		// Tous les joueurs commencent au centre du canvas, 
	    this.x = xInit;               
	    this.y = yInit;
	    this.graphicsContext = gc;
	    this.playerColor=color;
	    

	    
	    this.angle = 0;

	    // On charge la representation du joueur
        if(side=="top"){
        	this.directionArrow = new Image("assets/PlayerArrowDown.png");
		}
		else{
			this.directionArrow = new Image("assets/PlayerArrowUp.png");
		}
        
        this.PlayerDirectionArrow = new ImageView();
        this.PlayerDirectionArrow.setImage(directionArrow);
        this.PlayerDirectionArrow.setFitWidth(10);
        this.PlayerDirectionArrow.setPreserveRatio(true);
        this.PlayerDirectionArrow.setSmooth(true);
        this.PlayerDirectionArrow.setCache(true);

        Image tilesheetImage = new Image("assets/orc.png");
        this.sprite = new Sprite(tilesheetImage, 0,0, Duration.seconds(.2), side);
        this.sprite.setX(x);
        this.sprite.setY(y);
        //directionArrow = sprite.getClip().;

	    // Tous les joueurs ont une vitesse aleatoire entre 0.0 et 1.0
        // Random randomGenerator = new Random();
        //step = randomGenerator.nextFloat();

        // Pour commencer les joueurs ont une vitesse / un pas fixe
        this.step = speed;
	    
	  }

	  /**
	   *  Affichage du joueur
	   */
	  void display()
	  {
		  if(visible) {
			  this.graphicsContext.save(); // saves the current state on stack, including the current transform
		      rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
		      this.graphicsContext.drawImage(directionArrow, x, y);
		      spriteAnimate();
		      this.graphicsContext.restore(); // back to original state (before rotation)
		  }
	  }

	  private void rotate(GraphicsContext gc, double angle, double px, double py) {
		  Rotate r = new Rotate(angle, px, py);
		  gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	  }
	  
	  /**
	   *  Deplacement du joueur vers la gauche, on cantonne le joueur sur le plateau de jeu
	   */
	 
	  void moveLeft() 
	  {	    
	    if (this.x > 10 ) 
	    {
			this.x -= this.step;
	    }
	  }

	  /**
	   *  Deplacement du joueur vers la droite
	   */
	  void moveRight() 
	  {
	    if (this.x < 520) 
	    {
			this.x += this.step;
	    }
	  }

	  
	  /**
	   *  Rotation du joueur vers la gauche
	   */
	  void turnLeft() 
	  {
	    if (this.angle > 0 && this.angle < 180) 
	    {
	    	this.angle += 1;
	    }
	    else {
	    	this.angle += 1;
	    }

	  }

	  
	  /**
	   *  Rotation du joueur vers la droite
	   */
	  void turnRight() 
	  {
	    if (this.angle > 0 && this.angle < 180) 
	    {
	    	this.angle -=1;
	    }
	    else {
	    	this.angle -= 1;
	    }
	  }


	  void shoot(){
		this.sprite.playShoot();
	  }
	  
	  /**
	   *  Deplacement en mode boost
	   */
	  void boost() 
	  {
		  this.x += this.step*2;
		  spriteAnimate();
	  }

	  void spriteAnimate(){
	  	  //System.out.println("Animating sprite");
		  if(!this.sprite.isRunning) {
			 this.sprite.playContinuously();
		  }
		  this.sprite.setX(x);
		  this.sprite.setY(y);
	  }
		

		public double getX() {
			return x;
		}


		public double getY() {
			return y;
		}
		
		public double getAngle() {
			return angle;
		}
		
		public void setAngle(double angle) {
			this.angle = angle;
		}
		
		public boolean isVisible() {
			return this.visible;
		}
		
		public void setVisible() {
			visible = false;
		}
		
	  public boolean isEquipe1() {
		  return y > (Field.height/2);
	  }
}
