package nu.pich.android.starwars;

public class Item {

	private Integer id;
	private Integer drawable_small;
	private Integer drawable_large;
	private Integer sound;
	private String name;

	public Item(Integer id, Integer drawable_small, Integer drawable_large,
			Integer sound, String name) {
		this.id = id;
		this.drawable_small = drawable_small;
		this.drawable_large = drawable_large;
		this.sound = sound;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public Integer getDrawable_small() {
		return drawable_small;
	}

	public Integer getDrawable_large() {
		return drawable_large;
	}

	public Integer getSound() {
		return sound;
	}

	public String getName() {
		return name;
	}

}