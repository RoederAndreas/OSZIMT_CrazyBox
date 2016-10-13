package entities;

public class Case {

	private int id;
	private int payload;
	private String name;
	
	public Case(int payload, String name){
		this.payload = payload;
		this.name = name;
	}
	
	public Case(int id, int payload, String name){
		this.id = id;
		this.payload = payload;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPayload() {
		return payload;
	}

	public void setPayload(int payload) {
		this.payload = payload;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
