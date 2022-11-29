import Ventana.Ventana;
import Ventana.VentanaController;
import Ventana.VentanaModel;

public class Principal {
    public static void main(String args[]) {

        Ventana view = new Ventana();
        VentanaModel model = new VentanaModel();
        VentanaController controller = new VentanaController(view, model);
    }
}
