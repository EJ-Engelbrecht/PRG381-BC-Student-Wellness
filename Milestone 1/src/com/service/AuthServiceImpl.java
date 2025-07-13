class AuthServiceImpl implements AuthService  {
    private ArrayList<String> error_messages = new ArrayList<String>(); //used to store error messages

    //used for registration validation
    public boolean parseInput(String name, String surname, String phone, String email, String password){
        if (!name.isEmpty()){
            valid_name(name);
        } else {
            error_messages.add("Name must contain input");
        }

        if (!surname.isEmpty()) {
            valid_surname(surname);
        } else {
            error_messages.add("Surname must contain input");
        }

        if (!phone.isEmpty()) {
            valid_phone_nr(phone);
        } else {
            error_messages.add("Surname must contain input");
        }

        if (!email.isEmpty()) {
            valid_email_format(email);
        } else {
            error_messages.add("Email must contain input");
        }

        if (!password.isEmpty()){
            valid_password(password);
        } else {
            error_messages.add("Password must contain input");
        }

        //displays error messages if arraylist contains any items
        if (!error_messages.isEmpty()) {
            errorOutput();
            return false;
        }

        return true;
    }

    //used for login validation
    /*
        a. is email not empty? validate email
        b. is password not empty? validate password format
        c. is error message array not empty? display error message list
     */
    public boolean parseInput(String email, String password){
        if (!email.isEmpty()) {
            valid_email_format(email);
        } else {
            error_messages.add("Email must contain input");
        }

        if (!password.isEmpty()){
            valid_password(password);
        } else {
            error_messages.add("Password must contain input");
        }

        //displays error messages if arraylist contains any items
        if (!error_messages.isEmpty()) {
            errorOutput();
            return false;
        }

        return true;
    }

    //displays error arraylist
    private void errorOutput(){
        for (var item: error_messages) {
            System.out.println(item);
        }
    }

    //Checks if name has acceptable length
    public void valid_name(String name){
        if (!(name.length() > 2)){
            error_messages.add("Name must contain 2 or more characters");
        }
    }

    //Checks if surname has acceptable length
    public void valid_surname(String surname){
        if (!(surname.length() > 2)){
            error_messages.add("Surname must contain 2 or more characters");
        }
    }

    /*
        Checks if phone number has:
            1. not 10 characters long? add error message
            2. is integer?
     */
    public void valid_phone_nr(String phone) {
        if (!(phone.length() == 10)){
            error_messages.add("Phone number must contain 10 characters");
            return;
        }

        try {
            Integer.parseInt(phone);
        }
        catch (Exception e) {
            error_messages.add("Phone number may contain only numbers");
        }
    }

    //Email does not contains '@' character? add error message
    public void valid_email_format(String email) {
        if (!email.contains("@")){
            error_messages.add("Email must contain a '@' character");
        }
    }

    /*
        uses basic password standards:
            a. 10 character length
            b. at least 3 numbers
     */
    public void valid_password(String password) {
        if (!(String.length(password) == 10)) {
            error_messages.add("password must contain characters");
        }

        if (!(number_count(password) >= 3)){
            error_messages.add("password must contain at least 3 numbers");
        }
    }

    private int number_count(String password) {
        int count = 0;
        for (char c : password) {
            try {

                Integer.parseInt(c);

                count+=1;

            } catch (error e) {
                continue;
            }
        }

        return count;
    }
}