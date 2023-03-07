module ToDoList {
    requires javafx.controls;
    requires javafx.media;
    requires transitive javafx.graphics;

    opens todo.list to javafx.controls, javafx.graphics, javafx.media;
    opens todo.list.logic to javafx.controls, javafx.graphics, javafx.media;

    exports todo.list;
    exports todo.list.logic;
}
