package com.model;

public class Counselor {
    private int id;
    private String name;
    private String specialization;
    private boolean availability;

   public Integer getId(){
       return this.id;
   }
   
   public void setId(Integer id){
       this.id = id;
   }
            
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return this.specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public boolean isAvailable() {
        return availability;
    }

    public void changeAvailability(boolean availability) {
        if (this.availability) {
            this.availability = false;
        } else {
            this.availability = true;
        }
    }
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Counselor() {}
    public Counselor(String name, String specialization, boolean availability) {
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

}
