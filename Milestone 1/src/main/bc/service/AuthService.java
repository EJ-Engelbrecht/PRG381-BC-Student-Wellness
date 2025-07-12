package main.bc.service;

interface AuthService {
    void valid_phone_nr(String phone);
    void valid_email_format(String email);
    void valid_password(String password);
    void valid_name(String name);
    void valid_surname(String surname);
}
