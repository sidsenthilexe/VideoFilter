import com.jogamp.newt.Display;
import core.DisplayWindow;

public class RunMe {
    public static void main(String[] args) {
        // --== Load an image to filter ==--
        DisplayWindow.showFor("images/8.jpg", 1280, 720, "DoNothingFilter");

        // --== Determine your input interactively with menus ==--
//        DisplayWindow.getInputInteractively(800,600);
    }
}
