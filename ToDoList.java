import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ToDoList {
	private List<ToDoItem> items = new LinkedList<>();

	public void add(ToDoItem item) {
		items.add(item);
	}

	public void delete(ToDoItem item) {
		items.remove(items.indexOf(item));
	}

	public void resolve(ToDoItem item) {
		item.trueResolved();
	}

	public void unresolve(ToDoItem item) {
		item.falseResolved();
	}

	public ObservableList<ToDoItem> getItems() {
		return FXCollections.observableList(items);
	}

}
