import java.util.ArrayList;

public class FastArrayList extends ArrayList {
	protected ArrayList list = null;

	public FastArrayList() {
		super();
		this.list = new ArrayList();
	}
}