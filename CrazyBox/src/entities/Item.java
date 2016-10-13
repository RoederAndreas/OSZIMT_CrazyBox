package entities;

public class Item {

	private int id;
	private String designation;
	private int weight;
	private String description;
	private Case selectionCase;
	
	public Item(String designation, int weight, String description, Case selectionCase){
		this.designation = designation;
		this.weight = weight;
		this.description = description;
		this.selectionCase = selectionCase;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Case getSelectionCase() {
		return selectionCase;
	}

	public void setSelectionCase(Case selectionCase) {
		this.selectionCase = selectionCase;
	}
}
