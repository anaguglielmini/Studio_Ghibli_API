package com.example.studioghibliapi;

public class Films {
    int id;
   // int favorito;
    String title;


    String original;
    String title_romanised;
    String description;
    String director;
    String year;
    String time;
    String score;

    public Films() {
    }

    public Films(int _id, String _title, String _original, String _title_romanised, String _description, String _director, String _year, String _time, String _score/*, int _favorito*/) {
        this.id = _id;
        this.title = _title;
        this.original = _original;
        this.title_romanised = _title_romanised;
        this.description = _description;
        this.director = _director;
        this.year = _year;
        this.time = _time;
        this.score = _score;
       // this.favorito = _favorito;
    }

    public Films(String _title, String _original, String _title_romanised, String _description, String _director, String _year, String _time, String _score) {
        this.title = _title;
        this.original = _original;
        this.title_romanised = _title_romanised;
        this.description = _description;
        this.director = _director;
        this.year = _year;
        this.time = _time;
        this.score = _score;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getOriginal() {return original;}
    public void setOriginal(String original) {this.original = original;}

    public String getTitle_romanised() {return title_romanised;}
    public void setTitle_romanised(String title_romanised) {this.title_romanised = title_romanised;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getDirector() {return director;}
    public void setDirector(String director) {this.director = director;}

    public String getYear() {return year;}
    public void setYear(String year) {this.year = year;}

    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}

    public String getScore() {return score;}
    public void setScore(String score) {this.score = score;}

    /*public int getFavorito() {return favorito;}
    public void setFavorito(int favorito) {this.favorito = favorito;}*/
}


