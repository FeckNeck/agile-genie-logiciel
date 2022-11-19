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
	private Text score, titleScore, press, restart;
	private ImageView keys, arrowUp, arrowDown, playerRight, playerLeft, shooting ,echap, space,R;
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
	    	            
	    	            if(code == "R") {
	    	            	App.initGame();
	    	            	App.displayGame();
	    	            }
	    	        }
	    	    }
	    );
        init();
	}
	
	public void init() {
		score = new Text(230, 80, "Human 0 - 0 IA");
		Font font = new Font("Serif", 20);
		score.setFont(font);
		score.setFill(Color.BLACK);
		
		titleScore = new Text(260, 50, "Score :");
		Font fontTitle = new Font("Serif", 25);
		titleScore.setFont(fontTitle);
		titleScore.setFill(Color.BLACK);
		
		press = new Text(190, 500, "Press");
		press.setFont(font);
		press.setFill(Color.BLACK);
		
		restart = new Text(300, 500, "to restart game");
		restart.setFont(font);
		restart.setFill(Color.BLACK);
		
		Image keysFile = new Image("assets/keys.png",150,150,true,true);
		keys = new ImageView(keysFile);
		keys.setX(222);
		keys.setY(200);
		
		Image playerUpFile = new Image("assets/PlayerArrowUp.png",100,100,true,true);
		arrowUp = new ImageView(playerUpFile);
		arrowUp.setX(280);
		arrowUp.setY(165);
		
		Image playerRightFile= new Image("assets/playerRight.png",50,50,true,true);
		playerRight = new ImageView(playerRightFile);
		playerRight.setX(380);
		playerRight.setY(250);
		
		Image playerDownFile = new Image("assets/PlayerArrowDown.png",70,70,true,true);
		arrowDown = new ImageView(playerDownFile);
		arrowDown.setX(280);
		arrowDown.setY(265);
		
		Image playerLeftFile = new Image("assets/playerLeft.png",50,50,true,true);
		playerLeft = new ImageView(playerLeftFile);
		playerLeft.setX(192);
		playerLeft.setY(250);
		
		Image shootingFile = new Image("assets/shooting.png",50,50,true,true);
		shooting = new ImageView(shootingFile);
		shooting.setX(320);
		shooting.setY(345);
		
		Image echapFile = new Image("assets/echap.png",50,50,true,true);
		echap = new ImageView(echapFile);
		echap.setX(10);
		echap.setY(10);
		
		Image spaceFile = new Image("assets/space.png",70,70,true,true);
		space = new ImageView(spaceFile);
		space.setX(240);
		space.setY(350);
		
		Image Rfile = new Image("assets/R.png",50,50,true,true);
		R = new ImageView(Rfile);
		R.setX(245);
		R.setY(460);

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

	public Text getPress() {
		return press;
	}

	public Text getRestart() {
		return restart;
	}

	public ImageView getKeys() {
		return keys;
	}

	public ImageView getPlayerUp() {
		return arrowUp;
	}

	public ImageView getPlayerRight() {
		return playerRight;
	}

	public ImageView getPlayerDown() {
		return arrowDown;
	}

	public ImageView getPlayerLeft() {
		return playerLeft;
	}

	public ImageView getShooting() {
		return shooting;
	}

	public ImageView getEchap() {
		return echap;
	}

	public ImageView getSpace() {
		return space;
	}
	
	public ImageView getR() {
		return R;
	}
	
}
