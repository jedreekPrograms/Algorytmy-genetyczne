# Genetic Algorithms for Travelling Salesman Problem (TSP)
[![Open In Colab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/gist/jedreekPrograms/c657d18a7626fbe068e)
Projekt wykonany w ramach kursu **Algorytmy Metaheurystyczne 2026**.

## Opis projektu

Celem projektu było zaimplementowanie oraz porównanie algorytmów genetycznych dla problemu komiwojażera (Travelling Salesman Problem, TSP).

Projekt obejmuje:

* klasyczny Genetic Algorithm,
* Island Genetic Algorithm,
* elementy podejścia memetycznego (Memetic Algorithm),
* lokalne przeszukiwanie 2-opt.

Testy zostały wykonane dla instancji pochodzących z biblioteki TSPLIB.

---

# Zaimplementowane algorytmy

## Genetic Algorithm (GA)

Klasyczny algorytm genetyczny wykorzystujący:

* selekcję turniejową,
* elitism,
* PMX crossover,
* mutacje typu swap oraz insert,
* lokalne ulepszanie rozwiązań,
* adaptive mutation,
* immigrants strategy.

## Island Genetic Algorithm

Rozszerzenie klasycznego GA wykorzystujące:

* wiele niezależnych populacji,
* równoległe wykonywanie wysp,
* okresową migrację osobników,
* wymianę najlepszych rozwiązań pomiędzy wyspami.

---

# Wykorzystane techniki

## PMX Crossover

Operator krzyżowania dla permutacji zachowujący poprawność tras TSP.

## Mutation Operators

* swap mutation,
* insert mutation.

## Local Search

Algorytm 2-opt wykorzystywany do lokalnego ulepszania osobników.

## Intensification

Okresowe silniejsze ulepszanie najlepszych rozwiązań.

## Migration

Migracja najlepszych osobników pomiędzy wyspami w modelu Island GA.

---

# Struktura projektu

```text
src/
 ├── main/
 │    ├── java/
 │    │     └── algorithms/
 │    │           ├── genetic/
 │    │           │      ├── crossover/
 │    │           │      ├── mutation/
 │    │           │      ├── local/
 │    │           │      ├── island/
 │    │           │      └── selection/
 │    │           │
 │    │           ├── benchmark/
 │    │           ├── parser/
 │    │           └── model/
 │    │
 │    └── resources/
 │          ├── qatar.tsp
 │          ├── djibouti.tsp
 │          ├── uruguay.tsp
 │          └── ...
```

---

# Wyniki

Algorytmy genetyczne osiągały bardzo dobre wyniki jakościowe dla wszystkich testowanych instancji.

Model wyspowy zwiększał różnorodność populacji i w części przypadków pozwalał uzyskać lepsze rozwiązania niż klasyczny Genetic Algorithm.

Najlepsze rezultaty uzyskano dzięki połączeniu:

* eksploracji populacyjnej,
* lokalnego przeszukiwania,
* operatorów mutacji,
* intensification strategy.

---

# Wizualizacja

Projekt umożliwia:

* wizualizację tras TSP,
* analizę jakości rozwiązań,
* porównanie algorytmów,
* generowanie wykresów w Google Colab.

---

# Technologie

* Java 21
* Maven
* Google Colab
* Python
* Pandas
* Matplotlib

---

# Autor

Projekt wykonany w ramach zajęć:
**Algorytmy Metaheurystyczne 2026**
