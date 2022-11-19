package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class Field extends Canvas {
	
	/** Joueurs */
	private Player [] equipe1 = new Player[1];
	private Player [] equipe2 = new Player[3];
	
	private static Projectile projectile;
	
	
	/** Couleurs possibles */
	private String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	/** Tableau traçant les evenements */
	private ArrayList<String> input = new ArrayList<String>();
    

	private final GraphicsContext gc;
	public static final int width = 600;
	public static final int height = 600;
	public int cpt = 0;
	private boolean end = false;
	private static AnimationTimer handle;
	private boolean pause;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * 
     * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
	public Field(Scene scene) 
	{
		super(width, height);
		
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		
        gc = this.getGraphicsContext2D();
        
	    init();
	   	
        /** On initialise le terrain de jeu */
        
	    /** 
	     * Event Listener du clavier 
	     * quand une touche est pressee on la rajoute a la liste d'input
	     *   
	     */
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            // only add once... prevent duplicates
	    	            if ( !input.contains(code) )
	    	                input.add( code );
	    	        }
	    	    });

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est relachee on l'enleve de la liste d'input
	     *   
	     */
	    this.setOnKeyReleased(
	    	    new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            input.remove( code );
	    	        }
	    	    });
	    
	    /** 
	     * 
	     * Boucle principale du jeu
	     * 
	     * handle() est appelee a chaque rafraichissement de frame
	     * soit environ 60 fois par seconde.
	     * 
	     */
		handle = new AnimationTimer() 
	    {
			
	        public void handle(long currentNanoTime)
	        {	 
        		// On nettoie le canvas a chaque frame
	        	if(cpt == 60) {
	        		cpt = 0;
	        	}

	        	cpt++;
	        	gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, width, height);
	        	
	            playerDeplacement(equipe1);
	        	playerDeplacement2(equipe2);
	        	
	        	if(gameOver() && !end) {
	        		App.menuScene.setScore(0, 1);
	        		end = true;
	        	}
	        	if(gameWin() && !end) {
	        		App.menuScene.setScore(1, 0);
	        		end = true;
	        	}
	        	
	        	if(input.contains("ESCAPE")) {
	        		input.clear();
	        		App.displayMenu();
	        	}
	        	//Update du projectile :]
	  		    projectile.update(equipe1, equipe2);
			    gc.drawImage(projectile.getImage(), projectile.getX(), projectile.getY());
	        }
	    };
	    unpause();
	}
	
	public void pause() {
		handle.stop();
	}
	
	public void unpause() {
	    handle.start();
	}
	
	public void init() {
		double speed = Math.random()*5;
	       
	   	equipe1[0] = new Human(gc, colorMap[0], width/2, height-50, "bottom",speed);
	
	   	equipe2[0] = new IA(gc, colorMap[1], width/2, 20, "top",speed);
	   	
	   	equipe2[1] = new IA(gc, colorMap[1], width/3, 20, "top",speed);
	   	
	   	equipe2[2] = new IA(gc, colorMap[1], width/4, 20, "top",speed);
	   	
        projectile = Projectile.getInstance();
	   	
	   	projectile.setOwner(Math.random() < 0.5 ? equipe1[0] : equipe2[0]);
	   	
	}
	
	public void playerDeplacement(Player [] equipe) {
    	for (int i = 0; i < equipe.length; i++) 
	    {
    		
    		if (input.contains("Q"))
    		{
    			equipe[i].moveLeft();
    		} 
    		if (input.contains("D")) 
    		{
    			equipe[i].moveRight();	        			
    		}
    		if (input.contains("Z"))
    		{
    			equipe[i].turnLeft();
    		} 
    		if (input.contains("S")) 
    		{
    			equipe[i].turnRight();	        			
    		}
       		if (input.contains("SPACE") && projectile.getOwner() != null && projectile.getOwner().isEquipe1() && !projectile.isRunning()){
    			projectile.fire();
    			equipe[i].shoot();
			}
    		equipe[i].display();
	    }
	}
	
	public void playerDeplacement2(Player [] equipe) {
		
		//Shoot projectile
		if (projectile.getOwner() != null && !projectile.getOwner().isEquipe1() && !projectile.isRunning()){		
			
			double maxAngle = Math.toDegrees(Math.atan( (equipe1[0].getX() - projectile.getOwner().getX()) / (projectile.getOwner().getY() - equipe1[0].getY())));
			projectile.getOwner().setAngle(maxAngle);
			
			projectile.fire();
			projectile.getOwner().shoot();
			
		}
		
		//Dodge projectile
		if(projectile.isRunning() && projectile.getOwner().isEquipe1() && projectile.getY() < height/2) {
			for(int i = 0; i < equipe.length; i++) {
				int fouchette = ( (int) projectile.getHitBox().getWidth() + (int) equipe[i].sprite.getBoundsInParent().getWidth() ) / 2;
		    	if(equipe[i].getX() < (projectile.getX() + fouchette) && equipe[i].getX() > (projectile.getX() - fouchette)) {
		    		switch(projectile.getDirection()) {
			    		case "gauche":
			    			equipe[i].moveRight();
			    			break;
			    		case "droite":
			    			equipe[i].moveLeft();
			    			break;
		    		}
		    		
		    	}
			}
		}
		
		//Get projectile on floor
		for(int i = 0; i < equipe.length; i++) {
			if(projectile.getOwner() == null && !projectile.isRunning() && projectile.getY() < height/2) {
				if(equipe[i].getX() > projectile.getX()) {
					equipe[i].moveLeft();
				}else {
					equipe[i].moveRight();
				}
			}
			if(projectile.getOwner() != equipe[i] && cpt > 55) {
				double rand = Math.random();
				if(rand < 0.05) {
					for(int j = 0 ; j < Math.random() * 3;j++) {
						equipe[i].moveLeft();
					}
				}else if( rand > 0.05 && rand < 0.10) {
					for(int j = 0 ; j < Math.random() * 5;j++) {
						equipe[i].moveRight();
					}
				}
				
			}
			equipe[i].display();
		}
		
	}
	
	public void restartGame() {
		int i;
		for(i = 0 ; i < equipe1.length ; i++)
			equipe1[i] = null;
		for(i = 0 ; i < equipe2.length ; i++)
			equipe2[i] = null;
		init();
	}
	
	public Player[] getJoueurs() {
		return equipe1;
	}
	
	public Player[] getJoueurs2() {
		return equipe2;
	}
	
	public boolean gameWin() {
		for(int i = 0; i < equipe2.length;i++) {
			if(equipe2[i].isVisible() == true) {
				return false;
			}
		}
		return true;
	}
	
	public boolean gameOver() {
		for(int i = 0; i < equipe1.length;i++) {
			if(equipe1[i].isVisible() == true) {
				return false;
			}
		}
		return true;
	}
	
	public Player[] concatTable() {
		Player [] concat = new Player[equipe1.length + equipe2.length];
		
        for(int i = 0; i < equipe1.length; i++){
            concat[i] = equipe1[i];
        }

        for(int j = 0;j < equipe2.length; j++){
        	concat[equipe1.length + j] = equipe2[j];
        }

        return concat;

	}
}
