package it.polito.tdp.poweroutages.model;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	List<PowerOutage>pList;
	List<PowerOutage>soluzione;
	int totCostumers;
	int totOre;
	
	public Model() {
		podao = new PowerOutageDAO();
		pList=new ArrayList<PowerOutage>();
		soluzione=new ArrayList<PowerOutage>();
		totCostumers=0;
		totOre=0;
	}
	
	public int getTotCostumers() {
		return totCostumers;
	}
	
	public int getTotOre() {
		return totOre;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage>getEventoList(int nerc,int anniMax,int oreMax){
		pList=podao.getPowerOutage(nerc,oreMax);
		List<PowerOutage>parziale=new ArrayList<PowerOutage>();
		cercaSoluzione(parziale,anniMax,oreMax,0);
		return soluzione;
		
	}

	private void cercaSoluzione(List<PowerOutage> parziale, int anniMax, int oreMax,int L) {
		// casi terminali
		
		if(sommaOre(parziale)<=oreMax) {
			if(totaleCostumers(parziale)>totCostumers) {
				soluzione=new ArrayList<PowerOutage>(parziale);
				totCostumers=totaleCostumers(parziale);
				totOre=(int)sommaOre(parziale);
				}
		}else {
			return ;
		}
		
		if(L==pList.size()) {
			return ;
		}
		
		parziale.add(pList.get(L));
		float anniTot=calcolaAnni(parziale);
		if(anniTot<=anniMax) 
		cercaSoluzione(parziale,anniMax,oreMax,L+1);
		//BACKTRACKING
		parziale.remove(pList.get(L));
		cercaSoluzione(parziale,anniMax,oreMax,L+1);
		
	}
	
	private int calcolaAnni(List<PowerOutage> parziale) {
		if(parziale.size()!=0) {
		int max=0;
		int min=3000;
		for(PowerOutage po:parziale) {
			if(max<po.getDataFine().getYear())
				max=po.getDataFine().getYear();
			if(min>po.getDataInizio().getYear())
				min=po.getDataInizio().getYear();
		}
		return max-min;
		}else {
			return 0;
		}
	}

	private float sommaOre(List<PowerOutage> parziale) {
		float tot = 0;
		for(PowerOutage po : parziale){
			long f = po.getDataFine().toEpochSecond(ZoneOffset.UTC);
			long b = po.getDataInizio().toEpochSecond(ZoneOffset.UTC);
			float diff = (int) (f - b);
			tot += (diff/3600);
		}
		
		return tot;
	}
	
	private int totaleCostumers(List<PowerOutage> parziale) {
		int tot=0;
		for(PowerOutage po:parziale) {
			tot+=po.getPersone();
		}
		return tot;
	}
}
