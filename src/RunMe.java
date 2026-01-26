import com.jogamp.newt.Display;
import core.DisplayWindow;

import com.formdev.flatlaf.FlatDarkLaf;

public class RunMe {
    public static void main(String[] args) {
        // --== Load an image to filter ==--

        //FlatDarkLaf.setup();
        DisplayWindow.showFor("images/8.jpg", 1280, 720, "DoNothingFilter");

        // --== Determine your input interactively with menus ==--
//        DisplayWindow.getInputInteractively(800,600);
    }
}
