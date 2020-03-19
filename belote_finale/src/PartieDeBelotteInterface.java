import java.awt.EventQueue;
 
public class PartieDeBelotteInterface {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                BeloteInterface window = new BeloteInterface("Anna Tomie", "Guy de Michelin", "Jacques Use", "Kelly Diossy");
                window.frameJeuDeBelotte.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
 
        }});
    }
}