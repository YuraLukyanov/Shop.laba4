package ua.edu.ChaliyLukyanov.laba3.model;

import ua.edu.ChaliyLukyanov.laba3.model.EJB.Component;

import java.rmi.RemoteException;

public class Comparator implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	public static class PriceComparator implements java.util.Comparator<Component>{

		@Override
		public int compare(Component o1, Component o2) {
			double id1 = 0;
			double id2 = 0;
			try {
				id1 = o1.getPrice();
				id2 = o2.getPrice();
			} catch (RemoteException e) {
			
			}
			if (id1 > id2)
				return 1;
			else if (id1 < id2)
				return -1;
			else
				return 0;

		}
	}
	
	public static class ProducerComparator implements java.util.Comparator<Component>{
		
		@Override
		public int compare(Component o1, Component o2) {
			String pr1 = null;
			String pr2 = null;
			try {
				pr1 = o1.getProducer();
				pr2 = o2.getProducer();
			} catch (RemoteException e) {
			
			}
			return pr1.compareTo(pr2);
		}
	}
	
	public static class TitleComparator implements java.util.Comparator<Component>{

		@Override
		public int compare(Component o1, Component o2) {
			String pr1 = null;
			String pr2 = null;
			try {
				pr1 = o1.getTitle();
				pr2 = o2.getTitle();
			} catch (RemoteException e) {
			
			}
			return pr1.compareTo(pr2);
		}
	}
}



