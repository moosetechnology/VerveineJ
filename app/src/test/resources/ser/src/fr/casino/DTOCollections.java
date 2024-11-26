package fr.casino;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.ser.Entite;

public class DTOCollections extends DTOAbstrait {
	@SuppressWarnings("rawtypes")
	private Collection raw;
	private Map<String, List<Entite>> entites = new HashMap<>();
	private List<List<Integer>> imbrique = new ArrayList<>();
	private Set<Character> ensemble = new HashSet<>();
	private String[] tableau = { "a", "b" };
	private int[][] tableau2D = { { 0 }, { -1, 2 } };

	public DTOCollections() {
		List<Entite> entites = new ArrayList<>();
		this.entites.put("feuilles", entites);
		entites.add(new DTOFeuille());
		entites.add(new DTOHeritage());

		this.imbrique.add(new ArrayList<>());
		this.imbrique.add(new ArrayList<>());
		this.imbrique.get(0).add(0);
		this.imbrique.get(1).add(1);

		this.ensemble.add('a');
		this.ensemble.add('b');
		this.ensemble.add('c');
	}

	@SuppressWarnings("rawtypes")
	public Collection getRaw() {
		return raw;
	}

	@SuppressWarnings("rawtypes")
	public void setRaw(Collection raw) {
		this.raw = raw;
	}

	public Map<String, List<Entite>> getEntites() {
		return entites;
	}

	public void setEntites(Map<String, List<Entite>> entites) {
		this.entites = entites;
	}

	public List<List<Integer>> getImbrique() {
		return imbrique;
	}

	public void setImbrique(List<List<Integer>> imbrique) {
		this.imbrique = imbrique;
	}

	public Set<Character> getEnsemble() {
		return ensemble;
	}

	public void setEnsemble(Set<Character> ensemble) {
		this.ensemble = ensemble;
	}

}
