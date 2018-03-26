package jimmy.othello;

public class Player {

	private char color;
	private int score;
	

	public Player(char color) {
		this.color = color;
	}
	
	public char getColor() {
		return color;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public void incrementScore() {
		this.score++;
	}
	
	
	public String toString() {
		return "Player " + color;
	}
	
}
