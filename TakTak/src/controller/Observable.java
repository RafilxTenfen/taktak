package controller;

public interface Observable {
  void addObserver(Observer o);

  void removeObserver(Observer o);
}