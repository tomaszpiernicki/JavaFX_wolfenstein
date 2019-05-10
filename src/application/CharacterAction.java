package application;

import utils.CharacterActionType;

public class CharacterAction {
	Character character;
	CharacterActionType type;

	public Character getCharacter() {
		return character;
	}

	public CharacterActionType getType() {
		return type;
	}

	public CharacterAction(Character ch) {
		this.character = ch;
		this.type = CharacterActionType.MOVED;
	}

	public CharacterAction(Character ch, CharacterActionType cat) {
		this.character = ch;
		this.type = cat;
	}
}
