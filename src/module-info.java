module quizz.test {
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	
	exports quizz.test to javafx.graphics;
}