import com.jogamp.newt.Display;
import core.DisplayWindow;

public class RunMe {
    public static void main(String[] args) {
        // --== Load an image to filter ==--
        DisplayWindow.showFor("images/6.jpg", 1920, 1080, "DoNothingFilter");

        // --== Determine your input interactively with menus ==--
//        DisplayWindow.getInputInteractively(800,600);
    }
}
