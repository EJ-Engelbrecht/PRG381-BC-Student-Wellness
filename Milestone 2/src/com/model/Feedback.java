class Feedback {
    private String student;
    private int rating;
    private String[] Comments;

    public String getStudent() {
        return this.student;
    }
    public void setStudent(String student) {
        this.student = student;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        if (rating > 0 && rating <=10){
            this.rating = rating;
        }
    }
}