package minecraft.fr.clemdefrance;

import javax.swing.JOptionPane;
import minecraft.fr.clemdefrance.Screen.*;;

public class Main {
    public static void main(String[] args) {
        if(verify()) {
            System.out.println(org.lwjgl.Version.getVersion());
            Frame frame = new Frame();
        }
    }

    public static Boolean verify() {
        try {
            Class.forName("org.lwjgl.opengl.GL");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: A library is not found or not install, please contact your administrator: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}