package com.pay.chikun.service;

import com.pay.chikun.bean.User;

public interface UserService {
    public void login();
    public void checkAccount(User fromUser, User toUser);
    public void createAccount();
    public void logout(User user);
    public void myAccountDetails();
    public void accountActivity();
    public void fundTransfer(int amount, int pin, User fromUser, User toUser);
    public void withdraw(User user);
    public void changePin(User user);
    public void createLog(User user, String msg);
}
