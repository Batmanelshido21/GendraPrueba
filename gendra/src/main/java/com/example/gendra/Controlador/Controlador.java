package com.example.gendra.Controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.example.gendra.Modelo.Cities;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class Controlador {

  @GetMapping("/suggestions")
  @ResponseBody
  public ArrayList<Cities> obtenerHola(@RequestParam(required = true) String q,
      @RequestParam(required = false) String latitude,
      @RequestParam(required = false) String longitude) throws FileNotFoundException {

    String q2 = "";
    String latitude2 = "";
    String longitude2 = "";
    double resultado = 0;

    ArrayList<String> lista = new ArrayList<String>();
    lista = obtenerLista(q);
    ArrayList<Cities> ciudades = new ArrayList<Cities>();

    for (int i = 0; i < lista.size(); i++) {
      ciudades.add(obtenerQ(lista.get(i)));
    }

    for (int i = 0; i < ciudades.size(); i++) {
      q2 = ciudades.get(i).getName();
      latitude2 = ciudades.get(i).getLatitude();
      longitude2 = ciudades.get(i).getLongitud();
      double valorMaximo = q.length() + latitude.length() + longitude.length();
      resultado = calcularScore(q, q2);
      resultado = resultado + calcularScore(latitude, latitude2);
      resultado = resultado + calcularScore(longitude, longitude2);
      resultado = resultado * 100 / (valorMaximo) / (100);
      ciudades.get(i).setScore(resultado);
    }


    Collections.sort(ciudades, new Comparator<Cities>() {
      @Override
      public int compare(Cities p1, Cities p2) {
        return new Double(p2.getScore()).compareTo(new Double(p1.getScore()));
      }
    });

    return ciudades;
  }

  public Cities obtenerQ(String cadena) {
    char[] numeros = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    char[] aux = cadena.toCharArray();
    boolean bandera = true;
    int cont = 0;
    String cadena2 = "";
    String cadena3 = "";
    while (bandera == true) {
      for (int j = 0; j < numeros.length - 1; j++) {
        if (aux[cont] != numeros[j]) {

          cadena = cadena.substring(cont + 1, cadena.length());
          bandera = false;
        }
      }
      cont++;
    }
    aux = cadena.toCharArray();

    for (int i = 0; i < cadena.length() - 1; i++) {
      for (int j = 0; j < numeros.length - 1; j++) {
        if (aux[i] == numeros[j]) {
          cadena2 = cadena.substring(i, cadena.length());
          cadena2 = cadena2.trim();
          cadena = cadena.substring(0, i);
          i = cadena.length();
          j = numeros.length;
        }
      }
    }

    aux = cadena2.toCharArray();
    for (int i = 0; i < cadena2.length(); i++) {
      if (aux[i] == '-') {
        cadena3 = cadena2.substring(i, cadena2.length());
        cadena3 = cadena3.trim();
        cadena2 = cadena2.substring(0, i - 1);
        i = cadena2.length();
      }
    }
    cadena3 = cadena3.substring(0, 8);
    Cities ciudad = new Cities(cadena, cadena2, cadena3);


    return ciudad;
  }

  public ArrayList<String> obtenerLista(String q) throws FileNotFoundException {
    ArrayList<String> lista = new ArrayList<String>();
    File doc = new File("src/main/java/com/example/gendra/ArchivoLectura/cities_canada-usa.tsv");
    Scanner obj = new Scanner(doc);
    String aux = "";
    while (obj.hasNextLine()) {
      aux = obj.nextLine();
      if (aux.contains(q)) {
        lista.add(aux);
      }
    }
    obj.close();
    return lista;
  }

  public double calcularScore(String cadena1, String cadena2) {
    double contador = 0;
    double resultado = 0;
    char[] charCadena1 = cadena1.toCharArray();
    char[] charCadena2 = cadena2.toCharArray();

    for (int i = 0; i < cadena1.length(); i++) {
      if (charCadena1[i] == charCadena2[i]) {
        contador++;
        if (contador > resultado) {
          resultado = contador;
        }
      } else {
        contador = 0;
      }
    }
    return resultado;
  }

}