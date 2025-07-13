class AppointmentDAOImpl implements Appointment {
    private Connection conn;

    public AppointmentDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public List<Appointment> getAppointments() {
        String sql = "SELECT * FROM appointments";

        List<Appointment> appointments = new ArrayList<Appointment>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()){
                Student st = new Student();

                st.setStudent_no(result.getInt("student"));
                st.setName(result.getString("counselor"));
                st.setSurname(result.getString("date"));
                st.setPhone(result.getString("time"));
                st.setEmail(result.getString("status"));

                students.add(st);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return appointments;
    }
}