package edu.itba.paw.jimi.models;

public enum DishStatus {
    AVAILABLE(1),
    UNAVAILABLE(2);

    private int id;

    DishStatus(int id){
        this.id = id;
    }

    public static DishStatus getStatus(int id){
        for(DishStatus d : DishStatus.values()) {
            if (d.id == id)
                return d;
        }
        throw new IllegalArgumentException(); //TODO linkear con InvalidDishStatusException.
    }

    @Override //TODO linkear a diccionario.
    public String toString(){
        switch(getStatus(this.id)){
            case AVAILABLE:
                return "Available";

            case UNAVAILABLE:
                return "Unavailable";

                default:
                    return "No status found";
        }
    }
}
