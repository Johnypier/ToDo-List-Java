package todo.list.logic;

public class ToDoItem {
	private String content;
	private boolean resolved;

	public ToDoItem(String content) {
		this.content = content;
		this.resolved = false;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean value) {
		this.resolved = value;
	}
}
