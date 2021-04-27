package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PowerOutage {
	int id;
	LocalDateTime dataInizio;
	LocalDateTime dataFine;
	int persone;
	int totOre;
	
	public PowerOutage(int id, LocalDateTime dataInizio, LocalDateTime dataFine, int persone) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.persone = persone;
	}

	public int getTotOre() {
		return this.calcolaOre();
	}
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}



	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}



	public LocalDateTime getDataFine() {
		return dataFine;
	}



	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}



	public int getPersone() {
		return persone;
	}



	public void setPersone(int persone) {
		this.persone = persone;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	private int calcolaOre() {
		long f = this.dataFine.toEpochSecond(ZoneOffset.UTC);
		long b = this.dataInizio.toEpochSecond(ZoneOffset.UTC);
		int diff = (int) (f - b);
		return diff/3600;
	}
	
	
	
	
	
}
