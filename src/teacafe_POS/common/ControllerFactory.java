package teacafe_POS.common;

import teacafe_POS.menu.MenuController;
import teacafe_POS.seat.SeatController;

public class ControllerFactory {
	
	public static CommonControllerInterface make(int business) {
		CommonControllerInterface controller = null;
		
		switch (business) {
//			case 1 -> { controller = new KioskController(); }
//			case 2 -> { controller = new PosController(); }
			case 1 -> { controller = new MenuController(); }
			case 2 -> { controller = new SeatController(); }
		}
		
		return controller;
	}
	
}