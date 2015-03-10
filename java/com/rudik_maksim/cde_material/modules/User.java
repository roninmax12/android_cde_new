package com.rudik_maksim.cde_material.modules;

/**
 * Created by maksimrudik on 26.02.15.
 */
public class User {
    private String mLogin;
    private String mPassword;
    private String mCurrentGroup;
    private static User sUser;

    private User(String login, String password) {
        mLogin = login;
        mPassword = password;
    }

    public static User init(String login, String password){
        if (sUser == null){
            sUser = new User(login, password);
        }
        return sUser;
    }

    public static User get(){
        return sUser;
    }

    public String getLogin() {
        return mLogin;
    }

    public String getPassword(){
        return mPassword;
    }

    public String getCurrentGroup() {
        return mCurrentGroup;
    }

    public void setCurrentGroup(String currentGroup) {
        mCurrentGroup = currentGroup;
    }

    /**
     * returns false if this not ifmo student
     **/
    public boolean isIhbtStudent(){
        if (mCurrentGroup.contains("и"))
            return true;
        else
            return false;
    }

    public int getCourseNumber(){
        String strcourse = "";
        int course;

        if (!isIhbtStudent()){
            strcourse = "" + mCurrentGroup.charAt(0);
        }else{
            strcourse = "" + mCurrentGroup.charAt(1);
        }

        try{
            course = Integer.parseInt(strcourse);
            return course;
        }catch (NumberFormatException ex){
            return 0;
        }

    }

    public void destruct(){
        sUser = null;
        mLogin = null;
        mPassword = null;
        mCurrentGroup = null;
    }
}
