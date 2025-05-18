package eseo.cpoo.jfx.echecpointexe.helper;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * Utility class to manage the chess grid creation and retrieval of buttons.
 */
public final class GridManager {

    private GridManager() {
        // Utility class, no instantiation
    }

    /**
     * Generates an 8x8 chessboard grid with alternating colors.
     *
     * @param chessGrid the GridPane to populate with buttons
     */
    public static void generateChessGrid(GridPane chessGrid, String firstColor, String secondColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane stackPane = new StackPane();

                Button square = new Button();
                square.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                square.setStyle((row + col) % 2 == 0 ? "-fx-background-color: " + firstColor + ";" : "-fx-background-color: " + secondColor + ";");

                Circle circle = new Circle(17);
                circle.getStyleClass().add("dark-orange");
                circle.setMouseTransparent(true);
                circle.setVisible(false);

                stackPane.getChildren().addAll(square, circle);

                GridPane.setHgrow(stackPane, Priority.ALWAYS);
                GridPane.setVgrow(stackPane, Priority.ALWAYS);

                chessGrid.add(stackPane, col, row);
            }
        }
    }

    /**
     * Retrieves the button located at the specified column and row in the grid.
     *
     * @param chessGrid the GridPane containing the buttons
     * @param col the column index
     * @param row the row index
     * @return the Button at the specified location, or null if not found
     */
    public static Button getButtonAt(GridPane chessGrid, int col, int row) {
        for (Node node : chessGrid.getChildren()) {
            if (node instanceof StackPane stackPane) {
                for (Node child : stackPane.getChildren()) {
                    if (child instanceof Button btn) {
                        Integer c = GridPane.getColumnIndex(node);
                        Integer r = GridPane.getRowIndex(node);
                        if ((c == null ? 0 : c) == col && (r == null ? 0 : r) == row) {
                            return btn;
                        }
                    }

                }
            }
        }
        return null;
    }

    public static Circle getCircleAt(GridPane chessGrid, int col, int row) {
        for (Node node : chessGrid.getChildren()) {
            if (node instanceof StackPane stackPane) {
                for (Node child : stackPane.getChildren()) {
                    if (child instanceof Circle circle) {
                        Integer c = GridPane.getColumnIndex(node);
                        Integer r = GridPane.getRowIndex(node);
                        if ((c == null ? 0 : c) == col && (r == null ? 0 : r) == row) {
                            return circle;
                        }
                    }

                }
            }
        }
        return null;
    }

}
