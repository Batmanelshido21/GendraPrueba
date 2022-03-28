package com.example.gendra.Modelo;

public class Cities {

  String name;
  String latitude;
  String longitud;
  double score;

  public Cities(String name, String latitude, String longitud) {
    this.name = name;
    this.latitude = latitude;
    this.longitud = longitud;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitud() {
    return longitud;
  }

  public void setLongitud(String longitud) {
    this.longitud = longitud;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

}