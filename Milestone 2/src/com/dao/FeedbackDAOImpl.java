class FeedbackDAOImpl {
    private Connection conn;


    public FeedbackDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public List<Feedback> getFeedback() {
        String sql = "SELECT * FROM Feeback";

        List<Feedback> FeedbackList = new ArrayList<Appointment>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()){
                Feedback fb = new Feedback();

                fb.setStudent(result.getString("student"));
                fb.setRating(result.getInt("rating"));
                fb.setComments(result.getString("comments"));

                FeedbackList.add(st);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return appointments;
    }
}
}