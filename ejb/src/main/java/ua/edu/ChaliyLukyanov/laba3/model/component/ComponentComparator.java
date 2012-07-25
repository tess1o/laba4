package ua.edu.ChaliyLukyanov.laba3.model.component;

import java.rmi.RemoteException;
import java.util.Comparator;

/**
 * Component's comparator. 
 */
public class ComponentComparator {

	/**
	 * Comparator by component price.
	 */ 
 
	public static class PriceComparator implements Comparator<Component> {

		@Override
		public int compare(Component o1, Component o2) {
			double id1;
			double id2;
			try {
				id1 = o1.getPrice();
				id2 = o2.getPrice();
			} catch (RemoteException e) {
				throw new RuntimeException("Can't compare");
			}			
			if (id1 > id2)
				return 1;
			else if (id1 < id2)
				return -1;
			else
				return 0;

		}
	}

	/**
	 * Comporator by component producer name
	 */ 

	public static class ProducerComparator implements Comparator<Component> {

		@Override
		public int compare(Component o1, Component o2) {
			String pr1;
			String pr2;
			try{
				pr1 = o1.getProducer();
				pr2 = o2.getProducer();
			} catch (RemoteException e) {
				throw new RuntimeException("Can't compare");
			}	
			
			return pr1.compareTo(pr2);
		}
	}

	/**
	 * Comporator by component title
	 */
	 
	public static class TitleComparator implements Comparator<Component> {

		@Override
		public int compare(Component o1, Component o2) {
			String pr1;
			String pr2;
			try{
				pr1 = o1.getTitle();
				pr2 = o2.getTitle();
			} catch (RemoteException e) {
				throw new RuntimeException("Can't compare");
			}	
			
			return pr1.compareTo(pr2);
		}
	}
}
