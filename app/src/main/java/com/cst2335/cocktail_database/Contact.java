package com.cst2335.cocktail_database;

public class Contact {
    protected String name ;
    protected long id;

    public Contact(String m, long i) {
        this.name = m;
        this.id = i;
    }

    public void update(String m)
    {
        name = m;
    }

    public Contact(String m) { this(m, 0);}

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

}
