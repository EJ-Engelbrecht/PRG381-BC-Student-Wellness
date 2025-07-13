class CounselorDAOImpl {
    private Connection conn;

    public CounselorDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public List<Counselor> getCounselors() {
        String sql = "SELECT * FROM Counselors";

        List<Counselor> Counselors = new ArrayList<Appointment>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()){
                Counselor cs = new Counselor();

                cs.setName(result.getString("name"));
                cs.setSpecialization(result.getString("specialization"));
                cs.setAvailability(result.getBoolean("availability"));

                Counselors.add(st);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return Counselors;
    }
}