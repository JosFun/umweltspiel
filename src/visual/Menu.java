package visual;

import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/* A class displaying a Menu for the game.
* User is expected to either start or exit the game.*/
public class Menu {
    private static boolean answer;

    public static boolean displayMenu ( ) {
        Stage menu = new Stage ( );
        menu.setTitle ( "Environment Badge Game" );
        menu.setMinWidth( 800 );
        menu.setMinHeight ( 600 );

        Label label = new Label ( );
        label.setText ( "Environment Badge Game" );

        Button start = new Button ( "Start" );
        Button exit = new Button ( "Exit" );

        start.setOnAction ( e -> {
                Menu.answer = true;
                menu.close ( );
        });
        exit.setOnAction ( e -> {
            Menu.answer = false;
            menu.close ( );
        });

        VBox layout = new VBox ( 20 );
        layout.getChildren( ).addAll ( label, start, exit );
        layout.setAlignment(Pos.CENTER);

        Scene content = new Scene ( layout );
        menu.setScene ( content );
        menu.showAndWait();

        return ( Menu.answer );
    }
}
