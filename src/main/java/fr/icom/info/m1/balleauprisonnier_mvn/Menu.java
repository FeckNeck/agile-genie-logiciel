package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu extends Canvas{
	
	private final GraphicsContext gc;
	private final Scene scene;
	private Text score, titleScore;
	private ImageView keys;
	private int scoreEquipe1, scoreEquipe2;
	
	public Menu(Scene scene) {
		super(600, 600);
		this.scene = scene;
		this.setFocusTraversable(true);
        gc = this.getGraphicsContext2D();
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {	    	        	
	    	            String code = e.getCode().toString();
	    	            
	    	            if(code == "ESCAPE")
	    	            	App.displayGame();
	    	            
	    	            if(code == "M") {
	    	            	App.initGame();
	    	            	App.displayGame();
	    	            }
	    	        }
	    	    }
	    );
        init();
	}
	
	public void init() {
		score = new Text(220, 100, "Human 0 - 0 IA");
		Font font = new Font("Serif", 20);
		score.setFont(font);
		score.setFill(Color.BLACK);
		
		titleScore = new Text(220, 50, "Score :");
		Font fontTitle = new Font("Serif", 25);
		titleScore.setFont(fontTitle);
		titleScore.setFill(Color.BLACK);
		
		Image keysFile = new Image("assets/keys.png",150,150,true,true);
		keys = new ImageView(keysFile);
		keys.setX(200);
		keys.setY(200);
	}
	
	public void setScore(int score1, int score2) {
		scoreEquipe1 += score1;
		scoreEquipe2 += score2;
		score.setText("Human : " + scoreEquipe1 + " - " + scoreEquipe2 + " : IA ");
		
	}
	
	public Text getScore() {
        return score;
	}

	public Text getTitleScore() {
		return titleScore;
	}
	
	public ImageView getkeys() {
		return keys;
	}
	
}
