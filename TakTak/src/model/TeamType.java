package src.model;

public enum TeamType {
  WHITE(1) {
    @Override
    public String str() {
      return "WHITE";
    }
  },
  BLACK(2) {
    @Override
    public String str() {
      return "BLACK";
    }
  };

  public int valueTeamType;

  TeamType(int value) {
    valueTeamType = value;
  }

  public abstract String str();
}