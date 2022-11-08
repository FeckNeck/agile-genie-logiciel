package fr.icom.info.m1.balleauprisonnier_mvn;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application 
{
	public static Field gameField;
	public static Scene game, menu;
	public static Group rootGame, rootMenu;
	public static Menu menuScene;
	public static Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		this.stage = stage;
		// Nom de la fenetre
        stage.setTitle("BalleAuPrisonnier");
        
        initGame();
        initMenu();
        // On ajoute la scene a la fenetre et on affiche
        stage.setScene(game);
        stage.show();
	}
	
    public static void initGame() {
        rootGame = new Group();
        game = new Scene(rootGame);
        gameField = new Field(game);
        rootGame.getChildren().add(gameField);
        initGameField();
    }
    
    private static void initMenu() {
    	rootMenu = new Group();
        menu = new Scene(rootMenu);
        menuScene = new Menu(menu);
        rootMenu.getChildren().add(menuScene);
        initMenuScene();
    }        
	
	private static void initGameField() {
		Player[] players = gameField.concatTable();
        for(int i = 0 ; i < players.length ; i++) {
        	rootGame.getChildren().add(players[i].sprite);
        }
	}
	
	private static void initMenuScene() {
        rootMenu.getChildren().add(menuScene.getScore());
        rootMenu.getChildren().add(menuScene.getTitleScore());
        rootMenu.getChildren().add(menuScene.getkeys());
	}
	
    public static void main(String[] args) 
    {
        //System.out.println( "Hello World!" );
    	Application.launch(args);
    } 
    
    public static void displayMenu() {    	
    	gameField.pause();
    	
    	stage.setScene(menu);
        stage.show();
    }
    
    public static void displayGame() {    	
    	gameField.unpause();
    	stage.setScene(game);
        stage.show();
    }
    
}
