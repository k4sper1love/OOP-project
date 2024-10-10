package universitysystem.interfaces;

import universitysystem.utills.Journal;

import java.util.Vector;

public  interface Subscription
{
	public boolean subscribeToJournal(Journal j) ;
	
	public Vector<Journal> viewJournals() ;
	
	
}

